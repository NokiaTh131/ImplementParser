package Component;

import java.io.IOException;

public class Player {
    private String name;
    private int id;
    private int citycenRow = -1;
    private int citycenCol = -1;
    private Cell c;
    private int budget;
    private CityCrew cityCrew;

    public Player(String name, int id) throws IOException {
        ConfigurationReader r = new ConfigurationReader();
        this.name = name;
        this.id = id;
        this.budget = (int) r.initBudget();
        this.cityCrew = new CityCrew(-1,-1,this);
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
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}

