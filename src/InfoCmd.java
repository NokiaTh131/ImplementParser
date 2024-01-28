import Component.Player;
import Component.WeightedGraph;

import java.io.IOException;

public interface InfoCmd extends Command{

}

class InfoCommand implements InfoCmd {


    public static void main(String[] args) throws IOException {
        // Assuming you have a WeightedGraph instance named 'graph'
        WeightedGraph graph = new WeightedGraph(5, 5);  // Adjust the size as needed

        // Assuming the current position is at (2, 2)
        int currentRow = 2;
        int currentCol = 2;

        // Create an instance of InfoCommand
        InfoCommand infoCommand = new InfoCommand();

        // Set some opponents in different directions
        graph.addEdge(0, 2, new Player("p1", 1), 100);  // Opponent to the north
        graph.addEdge(2, 4, new Player("p2", 2), 100);  // Opponent to the west
        graph.addEdge(3, 4, new Player("p3", 3), 100);  // Opponent to the south
        graph.addEdge(1, 1, new Player("p4", 4), 100);  // Opponent to the east

    }
}
