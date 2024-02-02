import java.util.ArrayList;
import java.util.List;

interface Parser {
    /**
     * Attempts to parse the token stream
     * given to this parser.
     * throws: SyntaxError if the token
     * stream cannot be parsed
     */
    List<Expr> parsePlan() throws SyntaxError, EvalError;

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


    public List<Expr> parsePlan() throws SyntaxError, EvalError {
        List<Expr> statements = new ArrayList<>();
        while (tkz.hasNextToken()) {
            Expr statement = parseStatement();
            System.out.println(p.getBudget());
            statements.add(statement);
        }
        return statements;
    }
    //Statement → Command | BlockStatement | IfStatement | WhileStatement
    public Expr parseStatement() throws SyntaxError, EvalError {
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

    public Expr parseBlockStatement() throws SyntaxError, EvalError {
//        System.out.println("parseBlock");
        tkz.consume("{");
        List<Expr> statements = new ArrayList<>();
        while (!tkz.peek("}")) {
            statements.add(parseStatement());
        }
        tkz.consume("}");

        return new BlockStatement(statements);
    }

    // Command → AssignmentStatement | ActionCommand
    public Expr parseCommand() throws SyntaxError, EvalError {
//        System.out.println("parseCmd");
        if (tkz.peek("done") || tkz.peek("relocate")|| tkz.peek("move")
                || tkz.peek("invest") || tkz.peek("collect") || tkz.peek("shoot")) {
            return parseActionCommand();
        } else {
            return parseAssignmentStatement();
        }
    }

    public Expr parseAssignmentStatement() throws SyntaxError, EvalError {
        String identifier = tkz.consume();
        tkz.consume("=");
        Expr expression = parseExpression();
        return new AssignmentExpr(identifier, expression);
    }

    public Expr parseActionCommand() throws SyntaxError, EvalError {
        if(tkz.peek("done")) {
            tkz.consume("done");
            return new ActionCommand("done",p,l);
        } else if(tkz.peek("relocate")) {
            tkz.consume("relocate");
            return new ActionCommand("relocate",p,l);
        } else if(tkz.peek("move")) {
            return parseMoveCommand();
        } else if(tkz.peek("invest") || tkz.peek("collect")) {
            return parseRegionCommand();
        } else if(tkz.peek("shoot")) {
            return parseAttackCommand();
        }else {throw new SyntaxError("Action command syntax error");}
    }

    public Expr parseMoveCommand() throws SyntaxError {
//        System.out.println("parseMove");
        tkz.consume("move"); // Consume the "move" keyword
        Direction direction = parseDirection(); // Parse the direction
        return new MoveCommand(direction,p ,l);
    }

    private Expr parseRegionCommand() throws SyntaxError, EvalError {
//        System.out.println("parseRegion");
        if (tkz.peek("invest")) {
            tkz.consume("invest"); // Consume the "invest" keyword
            Expr expression = parseExpression(); // Parse the investment expression
            return new RegionCommand("invest", expression,p,l);
        } else if (tkz.peek("collect")) {
            tkz.consume("collect"); // Consume the "collect" keyword
            Expr expression = parseExpression(); // Parse the collection expression
            return new RegionCommand("collect", expression,p,l);
        } else {
            throw new SyntaxError("Invalid RegionCommand");
        }
    }

    private Expr parseAttackCommand() throws SyntaxError, EvalError {
        tkz.consume("shoot"); // Consume the "shoot" keyword
        Direction direction = parseDirection(); // Parse the direction
        Expr expression = parseExpression(); // Parse the expression
        return new AttackCommand(direction, expression,p,l);
    }

    private Direction parseDirection() throws SyntaxError {
//        System.out.println("parseDirection");
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

    private Expr parseIfStatement() throws SyntaxError, EvalError {
//        System.out.println("parseIf");
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

    public Expr parseWhileStatement() throws SyntaxError, EvalError {
//        System.out.println("parseWhile");
        tkz.consume("while");
        tkz.consume("(");
        Expr condition = parseExpression();
        tkz.consume(")");
        Expr body = parseStatement();
        return new WhileStatement(condition, body);
    }

    private Expr parseExpression() throws SyntaxError, EvalError {
//        System.out.println("parseExpression");
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

    private Expr parseTerm() throws Parser.SyntaxError, EvalError {
//        System.out.println("parseTerm");
        Expr left = parseFactor();
        while (tkz.peek("/") || tkz.peek("%") || tkz.peek("*")) {
            if(tkz.peek("/")) {
                tkz.consume();
                left = new BinaryArithExpr(left, "/", parseFactor());
            }else if(tkz.peek("%")){
                tkz.consume();
                left = new BinaryArithExpr(left, "%", parseFactor());
            }else{
                tkz.consume();
                left = new BinaryArithExpr(left, "*", parseFactor());
            }
        }

        return left;
    }

    private Expr parseFactor() throws Parser.SyntaxError, EvalError {
//        System.out.println("parseFactor");
        Expr l = parsePower();
        while (tkz.peek("^")) {
            tkz.consume("^");
            l = new BinaryArithExpr(l, "^", parseFactor());
        }
        return l;
    }

    public Expr parsePower() throws Parser.SyntaxError, EvalError {
//        System.out.println("parsePower");
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
//        System.out.println("parseInfo");
        if (tkz.peek("opponent")) {
            tkz.consume("opponent");
            return new OpponentInfoExpr(p,l);
        } else if (tkz.peek("nearby")) {
            tkz.consume("nearby");
            Direction direction = parseDirection();
            return new NearbyInfoExpr(direction,p,l);
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


