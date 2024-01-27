import java.util.List;
import java.util.Map;

interface Node {
    void prettyPrint(StringBuilder s);
}

interface Expr extends Node {
    int eval(Map<String, Integer> bindings) throws EvalError;
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
        if (bindings.containsKey(name)) return bindings.get(name);
        throw new EvalError("undefined variable: " + name);
    }
    public void prettyPrint(StringBuilder s) {
        s.append(name);
    }
}


record BinaryArithExpr(Expr left, String op, Expr right) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError {
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
    public void prettyPrint(StringBuilder s) {
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

class OpponentInfoExpr implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError {
        return 1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append("opponent");
    }
}

record NearbyInfoExpr(Direction direction) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError {
        return 1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append("nearby");
    }
}

class WhileStatement implements Expr {
    private final Expr condition;
    private final Expr body;

    public WhileStatement(Expr condition, Expr body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public int eval(Map<String, Integer> bindings) throws EvalError {
//        int counter = 0;
//        while (condition.eval(bindings) > 0 && counter < 10000) {
//            body.eval(bindings);
//            counter++;
//        }
        return 0; // Placeholder value
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append("while (");
        condition.prettyPrint(s);
        s.append(") ");
        body.prettyPrint(s);
    }
}


record IfStatement(Expr condition, Expr thenBranch,Expr elseBranch) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError {
        return 1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append("if");
        s.append("(");
        s.append(condition);
        s.append(")");
        s.append(thenBranch);
    }
}

record AttackCommand(Direction direction,Expr expression) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError {
        return 1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append("shoot");

    }
}

record RegionCommand(String cmd,Expr expression) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError {
        return 1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append(cmd);

    }
}

record MoveCommand(Direction direction) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError {
        return 1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append(direction);

    }
}

record ActionCommand(String cmd) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError {
        return 1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append(cmd);

    }
}

record AssignmentExpr(String identifier,Expr expression) implements Expr {
    public int eval(Map<String, Integer> bindings) throws EvalError {
        return 1;
    }
    public void prettyPrint(StringBuilder s) {
        s.append(identifier);

    }
}

class BlockStatement implements Expr {
    private final List<Expr> statements;

    public BlockStatement(List<Expr> statements) {
        this.statements = statements;
    }

    @Override
    public int eval(Map<String, Integer> bindings) throws EvalError {
        int result = 0; // Default value for block statement
//        for (Expr statement : statements) {
//            result = statement.eval(bindings);
//        }
        return result;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append("{\n");
        for (Expr statement : statements) {
            s.append("  ");
            statement.prettyPrint(s);
            s.append("\n");
        }
        s.append("}");
    }
}



