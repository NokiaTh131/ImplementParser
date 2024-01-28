import Component.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

interface Parser {
    /**
     * Attempts to parse the token stream
     * given to this parser.
     * throws: SyntaxError if the token
     * stream cannot be parsed
     */
    Expr parsePlan() throws SyntaxError;

    public class SyntaxError extends Exception {
        public SyntaxError(String message) {
            super(message);
        }
    }
}

class ExprParser implements Parser {

    private ExprTokenizer tkz;
    private land l;
    private Player p;
    public ExprParser(ExprTokenizer tkz, land l, Player p) {
        this.tkz = tkz;
        this.l = l;
        this.p = p;
    }


    public Expr parsePlan() throws SyntaxError {
        System.out.println("parsePlan");
        Expr statement = null;
        while (tkz.hasNextToken()) {
            statement = parseStatement();
        }
        return statement;
    }
    //Statement → Command | BlockStatement | IfStatement | WhileStatement
private Expr parseStatement() throws SyntaxError {
        System.out.println("parseStatement");
        if (tkz.peek("{")) {
            return parseBlockStatement();
        } else if (tkz.peek("if")) {
            return parseIfStatement();
        } else if (tkz.peek("while")) {
            return parseWhileStatement();
        } else {
            return parseCommand();
        }
    }

    private Expr parseBlockStatement() throws SyntaxError {
        System.out.println("parseBlock");
        tkz.consume("{");
        List<Expr> statements = new ArrayList<>();
        while (!tkz.peek("}")) {
            statements.add(parseStatement());
        }
        tkz.consume("}");

        return new BlockStatement(statements);
    }

    // Command → AssignmentStatement | ActionCommand
    private Expr parseCommand() throws SyntaxError {
        System.out.println("parseCmd");
        if (tkz.peek("done") || tkz.peek("relocate")|| tkz.peek("move")
                || tkz.peek("invest") || tkz.peek("collect") || tkz.peek("shoot")) {
            return parseActionCommand();
        } else {
            return parseAssignmentStatement();
        }
    }

    private Expr parseAssignmentStatement() throws SyntaxError {
        System.out.println("parseAssign");
        String identifier = tkz.consume();
        tkz.consume("=");
        Expr expression = parseExpression();
        return new AssignmentExpr(identifier, expression);
    }

    private Expr parseActionCommand() throws SyntaxError {
        System.out.println("parseAction");
        if(tkz.peek("done")) {
            tkz.consume("done");
            return new ActionCommand("done");
        } else if(tkz.peek("relocate")) {
            tkz.consume("relocate");
            return new ActionCommand("relocate");
        } else if(tkz.peek("move")) {
            return parseMoveCommand();
        } else if(tkz.peek("invest") || tkz.peek("collect")) {
            return parseRegionCommand();
        } else if(tkz.peek("shoot")) {
            return parseAttackCommand();
        }else {
            throw new SyntaxError("Action command syntax error");
        }
    }

    private Expr parseMoveCommand() throws SyntaxError {
        System.out.println("parseMove");
        tkz.consume("move"); // Consume the "move" keyword
        Direction direction = parseDirection(); // Parse the direction
        return new MoveCommand(direction);
    }

    private Expr parseRegionCommand() throws SyntaxError {
        System.out.println("parseRegion");
        if (tkz.peek("invest")) {
            tkz.consume("invest"); // Consume the "invest" keyword
            Expr expression = parseExpression(); // Parse the investment expression
            return new RegionCommand("invest", expression);
        } else if (tkz.peek("collect")) {
            tkz.consume("collect"); // Consume the "collect" keyword
            Expr expression = parseExpression(); // Parse the collection expression
            return new RegionCommand("collect", expression);
        } else {
            throw new SyntaxError("Invalid RegionCommand");
        }
    }

    private Expr parseAttackCommand() throws SyntaxError {
        System.out.println("parseAttack");
        tkz.consume("shoot"); // Consume the "shoot" keyword
        Direction direction = parseDirection(); // Parse the direction
        Expr expression = parseExpression(); // Parse the expression
        return new AttackCommand(direction, expression);
    }

    private Direction parseDirection() throws SyntaxError {
        System.out.println("parseDirection");
        if (tkz.peek("up")) {
            tkz.consume("up");
            return Direction.UP;
        } else if (tkz.peek("down")) {
            tkz.consume("down");
            return Direction.DOWN;
        }else if (tkz.peek("upleft")) {
            tkz.consume("upleft");
            return Direction.UPLEFT;
        } else if (tkz.peek("upright")) {
            tkz.consume("upright");
            return Direction.UPRIGHT;
        } else if (tkz.peek("downleft")) {
            tkz.consume("downleft");
            return Direction.DOWNLEFT;
        } else if (tkz.peek("downright")) {
            tkz.consume("downright");
            return Direction.DOWNRIGHT;
        } else {
            throw new SyntaxError("Expected a valid direction, but found: " + tkz.peek());
        }
    }

    private Expr parseIfStatement() throws SyntaxError {
        System.out.println("parseIf");
        tkz.consume("if");
        tkz.consume("(");
        Expr condition = parseExpression();
        tkz.consume(")");
        tkz.consume("then");
        Expr thenBranch = parseStatement();
        tkz.consume("else");
        Expr elseBranch = parseStatement();
        return new IfStatement(condition, thenBranch, elseBranch);
    }

    private Expr parseWhileStatement() throws SyntaxError {
        System.out.println("parseWhile");
        tkz.consume("while");
        tkz.consume("(");
        Expr condition = parseExpression();
        tkz.consume(")");
        Expr body = parseStatement();
        return new WhileStatement(condition, body);
    }

    private Expr parseExpression() throws SyntaxError {
        System.out.println("parseExpression");
        Expr left = parseTerm();
        while (tkz.peek("+")) {
            tkz.consume();
            left = new BinaryArithExpr(left, "+", parseTerm());
        }
        while (tkz.peek("-")) {
            tkz.consume();
            left = new BinaryArithExpr(left, "-", parseTerm());
        }
        return left;
    }

    private Expr parseTerm() throws Parser.SyntaxError {
        System.out.println("parseTerm");
        Expr left = parseFactor();
        while (tkz.peek("*")) {
            tkz.consume();
            left = new BinaryArithExpr(left, "*", parseFactor());
        }
        while (tkz.peek("/")) {
            tkz.consume();
            left = new BinaryArithExpr(left, "/", parseFactor());
        }
        while (tkz.peek("%")) {
            tkz.consume();
            left = new BinaryArithExpr(left, "%", parseFactor());
        }

        return left;
    }

    private Expr parseFactor() throws Parser.SyntaxError {
        System.out.println("parseFactor");
        Expr l = parsePower();
        while (tkz.peek("^")) {
            tkz.consume("^");
            l = new BinaryArithExpr(l, "^", parseFactor());
        }
        return l;
    }

    private Expr parsePower() throws Parser.SyntaxError {
        System.out.println("parsePower");
        String[] reservedWords = {"collect", "done", "down", "downleft", "downright",
                "else", "if", "invest", "move", "nearby", "opponent",
                "relocate", "shoot", "then", "up", "upleft", "upright", "while"};
        if (isNumber(tkz.peek())) {
            return new IntLit(Integer.parseInt(tkz.consume()));
        } else if (isChar(tkz.peek()) && !isReservedWord(tkz.peek(), reservedWords)) {
            return new Variable(tkz.consume());
        } else if (tkz.peek("opponent") || tkz.peek("nearby")) {
            return parseInfoExpression();
        }else {
            tkz.consume("(");
            Expr v = parseExpression();
            tkz.consume(")");
            return v;
        }
    }
    private Expr parseInfoExpression() throws SyntaxError {
        System.out.println("parseInfo");
        if (tkz.peek("opponent")) {
            tkz.consume("opponent");
            return new OpponentInfoExpr();
        } else if (tkz.peek("nearby")) {
            tkz.consume("nearby");
            Direction direction = parseDirection();
            return new NearbyInfoExpr(direction);
        } else {
            throw new SyntaxError("Unexpected token in InfoExpression: " + tkz.peek());
        }
    }












    private boolean isNumber(String str) {
        return str != null && str.matches("\\d+");
    }
    private boolean isChar(String str) {
        return str != null && str.matches("\\w+");
    }

    private static boolean isReservedWord(String word, String[] reservedWords) {
        // Check if the given word is in the list of reserved words
        for (String reserved : reservedWords) {
            if (reserved.equals(word)) {
                return true;
            }
        }
        return false;
    }

}


