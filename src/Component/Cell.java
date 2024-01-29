package Component;

import java.io.IOException;

public class Cell{
    private Player p;
    private int deposit;
    private boolean isCitycenter = false;
    private long row;
    private long col;

    public Cell(Player p ,int deposit,long row,long col) {
        this.p = p;
        this.deposit = deposit;
        this.row = row;
        this.col = col;
    }

    public Cell(long row,long col) throws IOException {
        this.p = new Player("N/A",0);
        this.deposit = 0;
        this.row = row;
        this.col = col;

    }

    public int getDeposit() {
        return deposit;
    }

    public int getPlayer_Id() {
        return p.getId();
    }

    public void setCitycenter(boolean citycenter) {
        this.isCitycenter = citycenter;
        p.SetCenterLoc(row,col);

    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public boolean isCitycenter() {
        return isCitycenter;
    }

    public int getRow() {
        return (int)row;
    }
    public int getCol() {
        return (int)col;
    }

    public void setP(Player p) {
        this.p = p;
    }

    public Player getP() {
        return p;
    }

    public static void main(String[] args) throws IOException {
        WeightedGraph w = new WeightedGraph(6,5);
        Player p1 = new Player("Go",11);
        Player p2 = new Player("Jo",22);
        p1.SetCenterLoc(2,3);
        p2.SetCenterLoc(0,1);

        w.addEdge(2,3,p1,122);
        w.addEdge(3,4,p1,44);

        w.addEdge(0,1,p2,1322);
        w.addEdge(4,0,p2,1233);

        w.printGraphMatrix();
        p1.printCenterCityLoc();
        p2.printCenterCityLoc();


    }

}
