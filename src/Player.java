import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Player {
    private String name;
    private int id;
    private int citycenRow = -1;
    private int citycenCol = -1;
    private Cell c;
    private CityCrew cityCrew;
    public Map<String, Integer> bindings = new HashMap<>();
    private Variable t = new Variable("t");
    private Variable d = new Variable("deposit");
    private Variable b = new Variable("budget");
    private Variable m = new Variable("m");
    private Variable loc = new Variable("opponentLoc");
    private Variable cost = new Variable("cost");
    private Variable dir = new Variable("dir");
    private Variable random = new Variable("random");
    private Variable currow = new Variable("currow");
    private Variable curcol = new Variable("curcol");
    private Variable rows = new Variable("rows");
    private Variable cols = new Variable("cols");
    private Variable interest = new Variable("int");
    private Variable interest_base = new Variable("interest_pct");
    private Variable maxdp = new Variable("max_dep");


    public Player(String name, int id) throws IOException {
        ConfigurationReader r = new ConfigurationReader();
        bindings.put("rows", (int)r.m());
        bindings.put("cols", (int)r.n());
        this.name = name;
        this.id = id;
        bindings.put("budget",(int) r.initBudget());
        this.cityCrew = new CityCrew(this);
        bindings.put("t", 0);
        bindings.put("deposit",1000);
        bindings.put("m", 0);
        bindings.put("opponentLoc", 0);
        bindings.put("cost", 0);
        bindings.put("dir", 0);
        bindings.put("random",0 );
        bindings.put("int",10000);
        bindings.put("interest_pct",(int)r.interestPct());
        bindings.put("max_dep",(int)r.maxDep());
    }
    public Player() throws IOException {
        this("N/A",0);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void SetCenterLoc(long row, long col) {
        this.citycenRow = (int)row;
        this.citycenCol = (int)col;
    }

    public int[] getCenterLoc() {
        int[] Loc = new int[2];
        Loc[0] = this.citycenRow;
        Loc[1] = this.citycenCol;
        return Loc;
    }

    public void printCenterCityLoc() {
        System.out.println(name + " city center is " + "(" + getCenterLoc()[0] + ", " + getCenterLoc()[1] + ")");
    }

    public int getBudget() {
        return bindings.get("budget");
    }

    public void setBudget(int budget) {
        bindings.put("budget",budget);
    }

    public CityCrew getCityCrew() {
        return cityCrew;
    }
    public void setCityCenter(Cell c) {
        c.setCitycenter(true);
    }

    public void setCityCrewLoc(int row,int col) {
        bindings.put("currow", row);
        bindings.put("curcol", col);
    }

}

