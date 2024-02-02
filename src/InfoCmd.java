import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface InfoCmd {
    int opponent(Player p,land w) throws IOException;
    int nearby(Direction d, Player p, land w) throws IOException;
}

class InfoCommand implements InfoCmd {
    ActionCommands a = new ActionCommands();
    ConfigurationReader c = new ConfigurationReader();

    public int opponent(Player p,land w) throws IOException {//find the closest opponent and return number that digit is direction and left is distance +10
        int maxOpponentNumber = Integer.MAX_VALUE;
        for (Direction direction : Direction.values()) {
            int[] opponentNumber = findOpponent(direction, p,w);
            if (opponentNumber[0] > 0 && opponentNumber[0] < maxOpponentNumber) {
                maxOpponentNumber = opponentNumber[0];
            }
        }
        if(maxOpponentNumber == Integer.MAX_VALUE) return 0;
        return maxOpponentNumber;
    }

    public int nearby(Direction d,Player p,land w) throws IOException {
        int[] packOfD_d = findOpponent(d,p,w);//change y right here.
        String yS = Integer.toString(packOfD_d[1]);
        int y = yS.length();
        int distance = packOfD_d[0];
        System.out.println(y);
        if(distance == 0) return 0;
        String loc = Integer.toString(distance);//get string
        String x = loc.substring(0, loc.length() - 1);//get substring except last char
        return (100 * Integer.parseInt(x)) + y;
    }



    /*@param d = direction that will find through
      @param p = Player that use this operation
      @param w = land that we mention
      @param y is deposit that we want to change
      @return : number of location in form xy where x is distance and y is direction such as 41 distance is 4,direction is UP
      @SideEffect : @param y change to be deposited of Cell that findOpponent stop
     */
    public int[] findOpponent(Direction d,Player p,land w) throws IOException {
        int culrow = p.getCityCrew().getCurrentRow();
        int culcol = p.getCityCrew().getCurrentCol();
        int[] loc = new int[2];// loc ->(row,col)
        loc[0] = culrow;
        loc[1] = culcol;
        int y;
        int[] packOfD_and_d = new int[2];
        switch (d) {
            case UP:
                int initial_up_number = 1;
                while (loc[0] > 0) {
                    loc = a.directPeek(d,loc[0],loc[1]);
                    initial_up_number += 10;
                    int id = w.getCell(loc[0],loc[1]).getPlayer_Id();
                    if(id != p.getId() && id != 0) {
                        y = w.getCell(loc[0],loc[1]).getDeposit();
                        packOfD_and_d[0] = initial_up_number;
                        packOfD_and_d[1] = y;
                        return packOfD_and_d;
                    }
                }
                break;
            case UPLEFT:
                int initial_upleft_number = 6;
                while (loc[0] > 0 && loc[1] > 0) {
                    loc = a.directPeek(d, loc[0], loc[1]);
                    initial_upleft_number += 10;
                    int id = w.getCell(loc[0], loc[1]).getPlayer_Id();
                    if (id != p.getId() && id != 0) {
                        y = w.getCell(loc[0],loc[1]).getDeposit();
                        packOfD_and_d[0] = initial_upleft_number;
                        packOfD_and_d[1] = y;
                        return packOfD_and_d;
                    }
                }
                break;
            case UPRIGHT:
                int initial_upright_number = 2;
                while (loc[0] > 0 && loc[1] < c.n()-1) {
                    loc = a.directPeek(d, loc[0], loc[1]);
                    initial_upright_number += 10;
                    int id = w.getCell(loc[0], loc[1]).getPlayer_Id();
                    if (id != p.getId() && id != 0) {
                        y = w.getCell(loc[0],loc[1]).getDeposit();
                        packOfD_and_d[0] = initial_upright_number;
                        packOfD_and_d[1] = y;
                        return packOfD_and_d;
                    }
                }
                break;
            case DOWNRIGHT:
                int initial_downright_number = 3;
                while (loc[0] < c.m()-1 && loc[1] < c.n()-1) {
                    loc = a.directPeek(d, loc[0], loc[1]);
                    initial_downright_number += 10;
                    int id = w.getCell(loc[0], loc[1]).getPlayer_Id();
                    if (id != p.getId() && id != 0) {
                        y = w.getCell(loc[0],loc[1]).getDeposit();
                        packOfD_and_d[0] = initial_downright_number;
                        packOfD_and_d[1] = y;
                        return packOfD_and_d;
                    }
                }
                break;
            case DOWN:
                int initial_down_number = 4;
                while (loc[0] < c.m()-1) {
                    loc = a.directPeek(d,loc[0],loc[1]);
                    initial_down_number += 10;
                    int id = w.getCell(loc[0],loc[1]).getPlayer_Id();
                    if(id != p.getId() && id != 0) {
                        y = w.getCell(loc[0],loc[1]).getDeposit();
                        packOfD_and_d[0] = initial_down_number;
                        packOfD_and_d[1] = y;
                        return packOfD_and_d;
                    }
                }
                break;
            case DOWNLEFT:
                int initial_downleft_number = 5;
                while (loc[0] < c.m()-1 && loc[1] > 0) {
                    loc = a.directPeek(d, loc[0], loc[1]);
                    initial_downleft_number += 10;
                    int id = w.getCell(loc[0], loc[1]).getPlayer_Id();
                    if (id != p.getId() && id != 0) {
                        y = w.getCell(loc[0],loc[1]).getDeposit();
                        packOfD_and_d[0] = initial_downleft_number;
                        packOfD_and_d[1] = y;
                        return packOfD_and_d;
                    }
                }
                break;
            default:
                System.out.println("Invalid direction: " + d);
        }
        packOfD_and_d[0] = 0;
        packOfD_and_d[1] = 0;
        return packOfD_and_d;

    }
    public static void main(String[] args) throws IOException {
        List<Player> players = new ArrayList<>();
        land l = new land(players);
        InfoCommand i = new InfoCommand();
        Player p = new Player("John",10);
        Player p2 = new Player("Ksdf",88);
        l.buyCity(3,4,p,1000);
        p.setCityCrewLoc(3,4);
        l.buyCity(1,4,p2,100000);
        l.buyCity(2,2,p2,2000);
        l.buyCity(4,2,p2,2000);
        l.buyCity(5,4,p2,2000);
        l.buyCity(4,6,p2,2000);
        l.buyCity(2,6,p2,2000);
        int y = i.opponent(p,l);
        l.printMatrix();
        System.out.println("closest direction number is " + y);

    }
}