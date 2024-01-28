package Component;
import java.io.IOException;
import java.util.*;

//this is direct weighted graph
public class WeightedGraph {
    private Cell[][] adjacencyMatrix;
    public long row;
    public long col;

    public WeightedGraph(long row,long col) throws IOException {
        this.row = row;
        this.col = col;
        this.adjacencyMatrix = new Cell[(int) row][(int) col];

        // Initialize the matrix with all weights set to infinity initially.
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                adjacencyMatrix[i][j] = new Cell(i,j);
            }
        }
    }

    public void addEdge(int row, int col, Player p,int deposit) {
        if(deposit <= 0) {
            System.out.println("fail to buy this Cell.");
            return;
        }
        adjacencyMatrix[row][col] = new Cell(p,deposit,row,col);

    }

    public void printOwner(Player p) {
        System.out.print(p.getName() + " own : " );
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int pId = adjacencyMatrix[i][j].getPlayer_Id();
                if (pId == p.getId()) {
                    System.out.print("[(" + i + ", " + j + "), " + adjacencyMatrix[i][j].getDeposit() + "] ");
                }
            }
        }
        System.out.println();
    }


    public void printGraphMatrix() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int deposit = adjacencyMatrix[i][j].getDeposit();
                if (deposit <= 0) {
                    System.out.print("0\t");
                } else {
                    System.out.print(adjacencyMatrix[i][j].getPlayer_Id() + "\t");
                }
            }
            System.out.println();
        }
    }

    public Cell getCell(int row,int col) {
        return adjacencyMatrix[row][col];
    }



}
