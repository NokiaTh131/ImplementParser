
import Component.ConfigurationReader;
import Component.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            ConfigurationReader configReader = new ConfigurationReader();

            Map<String, Integer> bindings = new HashMap<>();
            bindings.put("m", (int) configReader.m());
            bindings.put("n", (int) configReader.n());
            bindings.put("init_plan_min", (int) configReader.initPlanMin());
            bindings.put("init_plan_sec", (int) configReader.initPlanSec());
            bindings.put("init_budget", (int) configReader.initBudget());
            bindings.put("init_center_dep", (int) configReader.initCenterDep());
            bindings.put("plan_rev_min", (int) configReader.planRevMin());
            bindings.put("plan_rev_sec", (int) configReader.planRevSec());
            bindings.put("rev_cost", (int) configReader.revCost());
            bindings.put("max_dep", (int) configReader.maxDep());
            bindings.put("interest_pct", (int) configReader.interestPct());

            ConstructionPlanReader constructionPlanReader = new ConstructionPlanReader();
            String constructionPlan = constructionPlanReader.code();

            ExprTokenizer tokenizer = new ExprTokenizer(constructionPlan);
            ExprParser parser = new ExprParser(tokenizer,new land(),new Player());
            Expr result = parser.parsePlan();

            // Evaluate the parsed expression with the given bindings
            int evaluationResult = result.eval(bindings);

            // Print the result
            System.out.println("Evaluation Result: " + evaluationResult);

        } catch (IOException | Parser.SyntaxError | EvalError e) {
            e.printStackTrace();
            // Handle the exceptions appropriately
        }
    }
}
