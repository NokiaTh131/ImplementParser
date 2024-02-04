import java.io.IOException;

public class Cell{
    private Player p;
    private double deposit;
    private boolean isCitycenter = false;
    private long row;
    private long col;

    public Cell(Player p , int deposit, long row, long col) {
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

    public double getDeposit() {
        if(p.getId() == 0) return -deposit;
        return deposit;
    }

    public int getPlayer_Id() {
        return p.getId();
    }

    public void setCitycenter(boolean citycenter) {
        this.isCitycenter = citycenter;
        p.SetCenterLoc(row,col);

    }

    public void setDeposit(double deposit) {
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

}
