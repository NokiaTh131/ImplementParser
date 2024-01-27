import Component.Player;
import Component.WeightedGraph;

import java.io.IOException;

public interface InfoCmd extends Command{

}

class InfoCommand implements InfoCmd {
    private int calculateOpponentLocation(int currentRow, int currentCol, WeightedGraph graph) {
        int minDistance = Integer.MAX_VALUE;
        int opponentLocation = 0;  // 0 represents no visible opponent

        // Iterate over all directions
        for (int direction = 1; direction <= 6; direction++) {
            int destRow = currentRow;
            int destCol = currentCol;

            // Adjust destination coordinates based on direction
            switch (direction) {
                case 1: destRow--; break;//
                case 2: destCol--; break;
                case 3: destRow++; destCol++; break;//
                case 4: destRow++; break;//
                case 5: destCol++; break;//
                case 6: destRow++; destCol--; break;//
            }

            // Calculate minimum moving distance to opponent in this direction
            int distance = calculateMinMovingDistance(currentRow, currentCol, destRow, destCol);

            // Check if opponent is visible and closer than before
            if (distance < minDistance && graph.getCell(destRow, destCol).getPlayer_Id() != 0) {
                minDistance = distance;
                opponentLocation = direction * 10 + distance;  // Combine direction and distance
            }
        }

        return opponentLocation;
    }

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

        // Calculate opponent location
        int opponentLocation = infoCommand.calculateOpponentLocation(currentRow, currentCol, graph);

        // Display the result
        graph.printGraphMatrix();
        System.out.println("Opponent Location: " + opponentLocation);
    }
}
