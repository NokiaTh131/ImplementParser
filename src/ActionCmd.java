import Component.Cell;
import Component.Player;
import Component.WeightedGraph;

import java.io.IOException;

public interface ActionCmd extends Command{
    void done();
    void relocate(Cell c, Player p,int money);
    void move(Direction d);
    int invest(int money);
    int collect(int money);
    boolean shoot(Direction d,int money);
}

class ActionCommands implements Command {
    public void done() {

    }
    public void relocate(Cell c, Player p) {
        int x = calculateMinMovingDistance(p.getCenterLoc()[0],p.getCenterLoc()[1],c.getRow(),c.getCol());
        System.out.println("min distance : " + x);
        if(p.getBudget() < 5*x+10 || c.getPlayer_Id() != p.getId()) {
            done();
            System.out.println("fail");
            return;
        }
        p.setBudget(p.getBudget() - (5*x+10));
        c.setCitycenter(true);
    }

    public static void main(String[] args) throws IOException {
        WeightedGraph w = new WeightedGraph(8,8);
        Player p1 = new Player("Go",11);
        w.addEdge(1,7,p1,122);
        p1.SetCenterLoc(1,7);

//        w.addEdge(4,1,p1,44);

        w.printGraphMatrix();
        p1.printCenterCityLoc();
        ActionCommands a = new ActionCommands();
        System.out.println(p1.getBudget());
        a.relocate(w.getCell(4,1),p1);
        p1.printCenterCityLoc();
        System.out.println(p1.getBudget());

    }
}
