import java.io.IOException;
import java.util.List;


public interface Territory {
    void buyCity(int row, int col, Player p) throws IOException;
    Cell getCell(int row, int col);
    int getIndexOfPlayer(Player p, List<Player> playerList);
}

class land implements Territory {
    ConfigurationReader c;
    WeightedGraph map;
    List<Player> players;
    public land(List<Player> players) throws IOException {
        c = new ConfigurationReader();
        map = new WeightedGraph(c.m(),c.n());
        this.players = players;
    }

    //spec : buycity is invoked only first time game is started for set player initial random cell
    @Override
    public void buyCity(int row, int col, Player p) throws IOException {
        map.addEdge(row,col,p,(int)c.initCenterDep());
        p.setCityCenter(this.getCell(row,col));
        p.setCityCrewLoc(row,col);
    }

    public void printMatrix() {
        map.printGraphMatrix();
    }

    public Cell getCell(int row, int col) {
        return map.getCell(row,col);
    }

    public void printOwner(Player p) {
        map.printOwner(p);
    }

    public int getIndexOfPlayer(Player p, List<Player> playerList) {
        return playerList.indexOf(p);
    }

}
