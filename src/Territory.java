import Component.Cell;
import Component.ConfigurationReader;
import Component.Player;
import Component.WeightedGraph;
import java.io.IOException;
import java.util.List;


public interface Territory {
    void buyCity(int row, int col, Player p, int deposit);
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

    @Override
    public void buyCity(int row, int col, Player p, int deposit) {
        map.addEdge(row,col,p,deposit);
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
