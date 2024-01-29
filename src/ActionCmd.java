import Component.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ActionCmd extends Command{
    void done();
    void relocate(Cell c, Player p);
    boolean move(Direction direction, Player p , land w);
    void invest(int money, Player p, Cell c) throws IOException;
    void collect(int amount, Player player, Cell c) throws IOException;
    void shoot(Direction d, int money,Player p1,land l) throws IOException;
}

class ActionCommands implements ActionCmd {

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
        done();
    }

    public boolean move(Direction direction, Player p , land w) {//don't handle move to edge yet. move should return loc
        p.setBudget(p.getBudget() - 1);
        if(p.getBudget() < 1) done();
        switch (direction) {
            case UP:
                p.getCityCrew().moveUp();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) {
                    p.getCityCrew().moveDown();
                    System.out.println("found opponent!");
                    return true;}
                break;
            case UPLEFT:
                p.getCityCrew().moveUpLeft();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) {
                    p.getCityCrew().moveDownRight();
                    System.out.println("found opponent!");
                    return true;}
                break;
            case UPRIGHT:
                p.getCityCrew().moveUpRight();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) {
                    p.getCityCrew().moveDownLeft();
                    System.out.println("found opponent!");
                    return true;}
                break;
            case DOWNRIGHT:
                p.getCityCrew().moveDownRight();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) {
                    p.getCityCrew().moveUpLeft();
                    System.out.println("found opponent!");
                    return true;}
                break;
            case DOWN:
                p.getCityCrew().moveDown();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) {
                    p.getCityCrew().moveUp();
                    System.out.println("found opponent!");
                    return true;}
                break;
            case DOWNLEFT:
                p.getCityCrew().moveDownLeft();
                if(p.getCityCrew().getPlayer_Id() != w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id()
                        && w.getCell(p.getCityCrew().getCurrentRow(),p.getCityCrew().getCurrentCol()).getPlayer_Id() != 0) {
                    p.getCityCrew().moveUpRight();
                    System.out.println("found opponent!");
                    return true;}
                break;
            default:
                System.out.println("Invalid direction: " + direction);
        }
        return false;

    }

    public void invest(int money, Player p, Cell c) throws IOException {
        // The total cost of an investment is i + 1, where i is the investment amount.
        int Cost = 1;
        int investmentCost = money + 1;
        //whatever it will decrease player budget by 1
        p.setBudget(p.getBudget() - Cost);
        if (p.getBudget() < investmentCost) {
            return;
        }
        ConfigurationReader r = new ConfigurationReader();

        int newDeposit = Math.min(c.getDeposit() + money, (int) r.maxDep());
        c.setDeposit(newDeposit);
        //set new player budget
        p.setBudget(p.getBudget() - money);
    }

    public void collect(int amount, Player player, Cell c) throws IOException {
        // The cost of the collect command is 1 unit
        int Cost = 1;
        player.setBudget(Math.max(player.getBudget() - Cost, 0));
        if (player.getBudget() < Cost) {
            done();
            return;
        }
        if (amount > c.getDeposit()) {
            // The command acts like a no-op, but the player still pays for the unit cost of the command
            return;
        }

        c.setDeposit(c.getDeposit() - amount);
        player.setBudget(player.getBudget() + amount);

        if (c.getDeposit() == 0) {
            c.setCitycenter(false);
            c.setP(new Player());
        }
    }

    @Override
    public void shoot(Direction d, int money,Player p1,land l) throws IOException {
        int shootCost = money + 1;
        if (p1.getBudget() < shootCost) {
            return;//no-op
        }
        p1.setBudget(p1.getBudget() - shootCost);
        int[] loc = directPeek(d,p1);
        System.out.println(p1.getName() + " aim at " + l.getCell(loc[0],loc[1]).getP().getName());

        // Calculate the new deposit after the attack

        int newDeposit = Math.max(0, l.getCell(loc[0],loc[1]).getDeposit() - money);
        l.getCell(loc[0],loc[1]).setDeposit(newDeposit);
        if (newDeposit < 1) {
            if(l.getCell(loc[0],loc[1]).isCitycenter()) {
                handlePlayerLoss(l.getCell(loc[0],loc[1]), l);
            }
            l.getCell(loc[0],loc[1]).setP(new Player());
        }

    }

    private int[] directPeek(Direction d,Player p) {

        int[] loc = new int[2];//0 is row and 1 is col
        loc[0] = p.getCityCrew().getCurrentRow();
        loc[1] = p.getCityCrew().getCurrentCol();
        switch (d) {//peekUp
            case UP:
                loc[0]--;
                break;
            case UPLEFT:
                if (loc[1] % 2 == 0) {
                    loc[0]--;
                    loc[1]--;
                    break;
                }
                if(loc[1] % 2 == 1){
                    loc[1]--;
                }
                break;
            case UPRIGHT:
                if (loc[1] % 2 == 0) {
                    loc[0]--;
                    loc[1]++;
                    break;
                }
                if(loc[1] % 2 == 1){
                    loc[1]++;
                }
                break;
            case DOWNRIGHT:
                if (loc[1] % 2 == 0) {
                    loc[1]++;
                    break;
                }
                if(loc[1] % 2 == 1){
                    loc[0]++;
                    loc[1]++;
                }
                break;
            case DOWN:
                loc[0]++;
                break;
            case DOWNLEFT:
                if (loc[1] % 2 == 0) {
                    loc[1]--;
                    break;
                }
                if(loc[1] % 2 == 1){
                    loc[0]++;
                    loc[1]--;
                }
                break;
        }
        return loc;
    }

    private void handlePlayerLoss(Cell target, land l) throws IOException {
        System.out.println(target.getP().getName() + " has lost the game!");
        Player lp = target.getP();
        l.players.remove(target.getP());
        for(int i = 0;i < l.map.row;i++) {
            for(int j = 0;j < l.map.col;j++) {
                if(lp.getId() == (l.getCell(i, j).getP().getId())) {
                    int oldDeposit = l.getCell(i,j).getDeposit();
                    l.getCell(i,j).setP(new Player());
                    l.getCell(i,j).setDeposit(oldDeposit);
                    System.out.println(l.getCell(i,j).getDeposit());
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        // Assuming you have a land instance 'w' and a player instance 'p1'
        List<Player> players = new ArrayList<>();
        land w = new land(players);

        Player p1 = new Player("Gojo", 99);
        w.buyCity(3, 2, p1, 1202);
        p1.setCityCrewLoc(3,2);

        Player p2 = new Player("Jogo", 11);
        w.buyCity(2, 2, p2, 1200);
        p2.setCityCenter(w.getCell(2,2));
        w.buyCity(4, 2, p2, 1000);
        w.buyCity(4, 0, p2, 999);

        players.add(p1);  players.add(p2);

        System.out.println("player(s) : ");
        for (Player p: players) {
            System.out.println(p.getName());
        }
        w.printMatrix();
        w.printOwner(p2);
        ActionCommands a = new ActionCommands();
        System.out.println(p1.getName() + " : " +p1.getBudget());
        System.out.println(w.getCell(2,2).getP().getName() + " deposit is " + w.getCell(2,2).getDeposit());

        a.shoot(Direction.UP,1300,p1,w);

        System.out.println(p1.getName() + " : " +p1.getBudget());
        System.out.println(w.getCell(2,2).getP().getName() + " deposit is " + w.getCell(2,2).getDeposit());

        System.out.println("player(s) : ");
        for (Player p: players) {
            System.out.println(p.getName());
        }
        w.printMatrix();
        System.out.println(w.getCell(3,2).getDeposit());

        a.invest(1001,p1,w.getCell(3,2));
        System.out.println(w.getCell(3,2).getDeposit());
        System.out.println(p1.getName() + " : " +p1.getBudget());
        a.collect(2203,p1,w.getCell(3,2));
        System.out.println(w.getCell(3,2).getDeposit());
        System.out.println(p1.getName() + " : " +p1.getBudget());
        w.printMatrix();
    }
}
