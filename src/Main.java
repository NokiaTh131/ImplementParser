import java.io.IOException;
import java.util.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, Parser.SyntaxError, EvalError, ActionCmd.ParsingInterruptedException {



        List<Player> players = new ArrayList<>();
        Player p = new Player("John",11);
        Player p2 = new Player("Jesse",12);
        players.add(p);players.add(p2);
        land l = new land(players);
        l.buyCity(0,0,p);
        l.buyCity(6,5,p2);
        ///display
        System.out.println("-----before parse variable value -----");
        l.printMatrix();
        for (Player player: players) {
            l.printOwner(player);
        }
        System.out.println("rows = " + p.bindings.get("rows"));
        System.out.println("cols = " + p.bindings.get("cols"));
        System.out.println("citycrewLoc = " + p.bindings.get("currow") + ", " + p.bindings.get("curcol"));
        System.out.println("t = " + p.bindings.get("t"));
        System.out.println("m = " + p.bindings.get("m"));
        System.out.println("deposit = " + p.bindings.get("deposit"));
        System.out.println("opponentLoc = " + p.bindings.get("opponentLoc"));
        System.out.println("budget = " + p.bindings.get("budget"));
        System.out.println("cost = " + p.bindings.get("cost"));
        System.out.println("dir = " + p.bindings.get("dir"));
        System.out.println("int = " + p.bindings.get("int"));
        System.out.println("intbase = " + p.bindings.get("interest_pct"));



        ConstructionPlanReader constructionPlanReader = new ConstructionPlanReader();
        String constructionPlan = constructionPlanReader.read("src/constructionplan.txt");

        ExprTokenizer tokenizer = new ExprTokenizer(constructionPlan);
        ExprParser parser = new ExprParser(tokenizer,l,p);
        List<Expr> result = parser.parsePlan();

        ConstructionPlanReader constructionPlanReader2 = new ConstructionPlanReader();
        String constructionPlan2 = constructionPlanReader2.read("src/constructionplan2.txt");

        ExprTokenizer tokenizer2 = new ExprTokenizer(constructionPlan2);
        ExprParser parser2 = new ExprParser(tokenizer2,l,p2);
        List<Expr> result2 = parser2.parsePlan();

        //first turn

        while (players.size() > 1) {
            try{
                if(players.contains(p)) {
                    for (Expr statement : result) {
                        statement.eval(p.bindings);
                    }
                    throw new ActionCmd.ParsingInterruptedException("");
                }
            }catch (ActionCmd.ParsingInterruptedException e) {
                System.out.println("p1 budget " + p.getBudget());
                System.out.println("p1 " + p.bindings.get("t") + " turn end.");
            }
            l.printMatrix();
            try{
                if(players.contains(p2)) {
                    for (Expr statement : result2) {
                        statement.eval(p2.bindings);
                    }
                    throw new ActionCmd.ParsingInterruptedException("");
                }
            }catch (ActionCmd.ParsingInterruptedException e) {
                System.out.println("p2 budget " + p2.getBudget());
                l.printOwner(p2);
                System.out.println("p2 " + p2.bindings.get("t") + " turn end.");
            }
            l.printMatrix();
        }

        System.out.println("!!parse complete!...");
        System.out.println("-----after parse variable value -----");
        System.out.println("rows = " + p.bindings.get("rows"));
        System.out.println("cols = " + p.bindings.get("cols"));
        System.out.println("citycrewLoc = " + p.bindings.get("currow") + ", " + p.bindings.get("curcol"));
        System.out.println("t = " + p.bindings.get("t"));
        System.out.println("m = " + p.bindings.get("m"));
        System.out.println("deposit = " + p.bindings.get("deposit"));
        System.out.println("opponentLoc = " + p.bindings.get("opponentLoc"));
        System.out.println("budget = " + p.bindings.get("budget"));
        System.out.println("cost = " + p.bindings.get("cost"));
        System.out.println("dir = " + p.bindings.get("dir"));
        System.out.println("int = " + p.bindings.get("int"));
        System.out.println("intbase = " + p.bindings.get("interest_pct"));
        l.printMatrix();

        for (Player player: players) {
            l.printOwner(player);
        }

    }
}
