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

    public Player(String name, int id) throws IOException {
        ConfigurationReader r = new ConfigurationReader();
        this.name = name;
        this.id = id;
        bindings.put("budget",(int) r.initBudget());
        this.cityCrew = new CityCrew(-1,-1,this);
        bindings.put("t", 0);
        bindings.put("deposit",1000);
        bindings.put("m", 0);
        bindings.put("opponentLoc", 0);
        bindings.put("cost", 0);
        bindings.put("dir", 0);

        Random rand = new Random();
        int randomValue = rand.nextInt(1000);
        bindings.put("random",randomValue );
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
        this.cityCrew.setCurrentRow(row);
        this.cityCrew.setCurrentCol(col);
    }
}

