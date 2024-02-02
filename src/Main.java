import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, Parser.SyntaxError, EvalError, ActionCmd.ParsingInterruptedException {



        List<Player> players = new ArrayList<>();
        Player p = new Player("John",11);
        Player p2 = new Player("Jesse",12);
        players.add(p);players.add(p2);
        land l = new land(players);
        l.buyCity(3,2,p,1000);
        l.buyCity(4,2,p2,900);
        ///display
        System.out.println("-----before parse variable value -----");
        System.out.println("t = " + p.bindings.get("t"));
        System.out.println("m = " + p.bindings.get("m"));
        System.out.println("deposit = " + p.bindings.get("deposit"));
        System.out.println("opponentLoc = " + p.bindings.get("opponentLoc"));
        System.out.println("budget = " + p.bindings.get("budget"));
        System.out.println("cost = " + p.bindings.get("cost"));
        System.out.println("dir = " + p.bindings.get("dir"));



        ConstructionPlanReader constructionPlanReader = new ConstructionPlanReader();
        String constructionPlan = constructionPlanReader.read("src/constructionplan2.txt");

        ExprTokenizer tokenizer = new ExprTokenizer(constructionPlan);
        ExprParser parser = new ExprParser(tokenizer,l,p);
        List<Expr> result = parser.parsePlan();

        try{
            for (Expr statement : result) {
                statement.eval(p.bindings);
            }
        }catch (ActionCmd.ParsingInterruptedException e) {
            System.out.println("turn end.");
        }

        System.out.println("!!parse complete!...");
        System.out.println("-----after parse variable value -----");
        System.out.println("t = " + p.bindings.get("t"));
        System.out.println("m = " + p.bindings.get("m"));
        System.out.println("deposit = " + p.bindings.get("deposit"));
        System.out.println("opponentLoc = " + p.bindings.get("opponentLoc"));
        System.out.println("budget = " + p.bindings.get("budget"));
        System.out.println("cost = " + p.bindings.get("cost"));
        System.out.println("dir = " + p.bindings.get("dir"));
        l.printMatrix();
        l.printOwner(p);
        l.printOwner(p2);










    }
}
