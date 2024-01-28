import Component.Cell;
import Component.CityCrew;
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

    public void move(Direction direction, Player p , land w) {
        switch (direction) {
            case UP:
                p.getCityCrew().moveUp();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) p.getCityCrew().moveDown();
                break;
            case UPLEFT:
                p.getCityCrew().moveUpLeft();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) p.getCityCrew().moveDownRight();
                break;
            case UPRIGHT:
                p.getCityCrew().moveUpRight();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) p.getCityCrew().moveDownLeft();
                break;
            case DOWNRIGHT:
                p.getCityCrew().moveDownRight();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) p.getCityCrew().moveUpLeft();
                break;
            case DOWN:
                p.getCityCrew().moveDown();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) p.getCityCrew().moveUp();
                break;
            case DOWNLEFT:
                p.getCityCrew().moveDownLeft();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) p.getCityCrew().moveUpRight();
                break;
            default:
                System.out.println("Invalid direction: " + direction);
        }

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
