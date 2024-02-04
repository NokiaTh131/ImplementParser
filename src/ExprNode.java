import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

interface Node {
    void prettyPrint(StringBuilder s) throws EvalError;
}

interface Expr extends Node {
    ActionCommands a = new ActionCommands();
    InfoCommand info = new InfoCommand();
    int eval(Map<String, Integer> bindings) throws EvalError, IOException, ActionCmd.ParsingInterruptedException;
}

class EvalError extends Throwable {
    public EvalError(String message) {
        super(message);
    }
}

record IntLit(int val) implements Expr {
    public int eval(Map<String, Integer> bindings) {
        return val;
    }
    public void prettyPrint(StringBuilder s) {
        s.append(val);
    }
}

record Variable(String name) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError {
        if (bindings.containsKey(name)) {
            if(name.equals("random")) {
                Random rand = new Random();
                int randomValue = rand.nextInt(1000);
                bindings.put("random",randomValue);
            }
            return bindings.get(name);
        }
        throw new EvalError("undefined variable: " + name);
    }
    public void prettyPrint(StringBuilder s) {
        s.append(name);
    }
}


record BinaryArithExpr(Expr left, String op, Expr right) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError, IOException, ActionCmd.ParsingInterruptedException {
        int lv = left.eval(bindings);
        int rv = right.eval(bindings);
        if (op.equals("+")) return lv + rv;
        if (op.equals("-")) return lv - rv;
        if (op.equals("*")) return lv * rv;
        if (op.equals("/")) return lv / rv;
        if (op.equals("%")) return lv % rv;
        if (op.equals("^")) return (int) Math.pow(lv,rv);
        throw new EvalError("unknown op: " + op);
    }
    public void prettyPrint(StringBuilder s) throws EvalError {
        s.append("(");
        left.prettyPrint(s);
        s.append(op);
        right.prettyPrint(s);
        s.append(")");
    }
}

//record NegExpr(Expr expr) implements Expr {
//    public int eval(Map<String, Integer> bindings) throws EvalError {
//        return -expr.eval(bindings);
//    }
//    public void prettyPrint(StringBuilder s) {
//        s.append("(-");
//        expr.prettyPrint(s);
//        s.append(")");
//    }
//}

record OpponentInfoExpr(Player p,land l) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError, IOException {
        return info.opponent(p,l);
    }
    public void prettyPrint(StringBuilder s) {
        s.append("opponent");
    }
}

record NearbyInfoExpr(Direction direction,Player p,land l) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError, IOException {
        return info.nearby(direction,p,l);
    }
    public void prettyPrint(StringBuilder s) {
        s.append("nearby");
    }
}

record WhileStatement(Expr condition, Expr body) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError, IOException, ActionCmd.ParsingInterruptedException {
        int result = 0;
        for (int c = 0; c < 10000 && condition.eval(bindings) > 0;c++) {
            result = body.eval(bindings);
        }
        return result;
    }

    public void prettyPrint(StringBuilder s) throws EvalError {
        s.append("while (");
        condition.prettyPrint(s);
        s.append(") ");
        body.prettyPrint(s);
    }
}

record ActionCommand(String cmd,Player p,land l) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError, IOException, ActionCmd.ParsingInterruptedException {
            if (cmd.equals("done")) {
                a.done();
                return 1;
            } else if (cmd.equals("relocate")) {
                a.relocate(l.getCell(p.getCityCrew().getCurrentRow(), p.getCityCrew().getCurrentCol()), p);
                a.done();
                return 2;
            }

        return 0;
    }

    public void prettyPrint(StringBuilder s) throws EvalError {
        if (cmd.equals("done")) {
            s.append("done");
        } else if (cmd.equals("relocate")) {
            s.append("relocate");
        }else{
            throw new EvalError("Invalid region command: " + cmd);
        }
    }
}


record IfStatement(Expr condition, Expr thenBranch,Expr elseBranch) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError, IOException, ActionCmd.ParsingInterruptedException {
        int conditionValue = condition.eval(bindings);
        if (conditionValue > 0) {
            return thenBranch.eval(bindings);
        } else {
            return elseBranch.eval(bindings);
        }
    }
    public void prettyPrint(StringBuilder s) throws EvalError {
        s.append("if (");
        condition.prettyPrint(s);
        s.append(") ");
        thenBranch.prettyPrint(s);
        s.append(" else ");
        elseBranch.prettyPrint(s);
    }
}

record AttackCommand(Direction direction,Expr expression,Player p,land l) implements Expr {

    public int eval(Map<String, Integer> bindings) throws EvalError, IOException, ActionCmd.ParsingInterruptedException {
        int cost = expression.eval(bindings);
        a.shoot(direction, cost, p, l);
        return 1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append("shoot").append(direction);
    }
}

record RegionCommand(String cmd,Expr expression,Player p, land l) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError, IOException, ActionCmd.ParsingInterruptedException {
        if(cmd.equals("invest")) {
            a.invest(expression.eval(bindings), p, l.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()));
            bindings.put("deposit",(int)l.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getDeposit());
            return 1;
        } else if (cmd.equals("collect")) {
            a.collect(expression.eval(bindings), p, l.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()));
            bindings.put("deposit",(int) l.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getDeposit());
            return 2;
        }else{
            throw new EvalError("Invalid region command: " + cmd);
        }
    }
    public void prettyPrint(StringBuilder s) {
        s.append(cmd);

    }
}

record MoveCommand(Direction direction, Player p, land l) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError, ActionCmd.ParsingInterruptedException {
        a.move(direction,p,l);
        bindings.put("deposit",(int) l.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getDeposit());
        return 1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append("move");
        s.append(direction);
    }
}


record AssignmentExpr(String identifier,Expr expression,Player p ,land l) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError, IOException, ActionCmd.ParsingInterruptedException {
        if (expression != null) {
            int value = expression.eval(bindings);
            bindings.put(identifier, value);
            if(identifier.equals("t")) {
                //make new deposit for all cell belong to this player and increased by int
                double r = 0;
                double interestRate = 0;
                for (int i = 0; i < bindings.get("rows"); i++) {
                    for (int j = 0; j < bindings.get("cols"); j++) {
                        int pId = l.getCell(i,j).getPlayer_Id();
                        if (pId == p.getId()) {
                            r = bindings.get("interest_pct") * Math.log10(l.getCell(i,j).getDeposit()) * Math.log(value);
                            interestRate = l.getCell(i,j).getDeposit() * r/100;
//                            System.out.println("interest rate " + value + " turn " + interestRate);
                            bindings.put("int",(int) l.getCell(i,j).getDeposit() * (int)r/100);
                            l.getCell(i,j).setDeposit(Math.min(l.getCell(i,j).getDeposit() + interestRate,bindings.get("max_dep")));
//                            System.out.println("player deposit " + l.getCell(i,j).getDeposit());
                        }
                    }
                }
            }
            return value;
        } else {
            throw new EvalError("Missing expression in assignment");
        }
    }
    public void prettyPrint(StringBuilder s) throws EvalError {
        s.append(identifier);
        if (expression != null) {
            s.append(" = ");
            expression.prettyPrint(s);
        }
    }
}

class BlockStatement implements Expr {
    private final List<Expr> statements;

    public BlockStatement(List<Expr> statements) {
        this.statements = statements;
    }

    @Override
    public int eval(Map<String, Integer> bindings) throws EvalError, IOException, ActionCmd.ParsingInterruptedException {
        int result = 0; // Default value for block statement
        for (Expr statement : statements) {
            result = statement.eval(bindings);
        }
        return result;
    }

    @Override
    public void prettyPrint(StringBuilder s) throws EvalError {
        s.append("{\n");
        for (Expr statement : statements) {
            s.append("  ");
            statement.prettyPrint(s);
            s.append("\n");
        }
        s.append("}");
    }
}



