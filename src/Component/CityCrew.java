package Component;

public class CityCrew {
    private int currentRow;
    private int currentCol;
    private Player p;

    public CityCrew(int row, int col, Player p) {
        this.currentRow = row;
        this.currentCol = col;
        this.p = p;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public int getPlayer_Id() {
        return p.getId();
    }

    public void moveUp() {
        currentRow--;
        if (currentCol % 2 == 1) {
            currentCol--;
        }
    }

    public void moveUpLeft() {
        currentRow--;
        if (currentCol % 2 == 0) {
            currentCol++;
        }
    }

    public void moveUpRight() {
        currentCol++;
    }

    public void moveDownRight() {
        currentRow++;
        if (currentCol % 2 == 0) {
            currentCol++;
        }
    }

    public void moveDown() {
        currentRow++;
        if (currentCol % 2 == 1) {
            currentCol--;
        }
    }

    public void moveDownLeft() {
        currentCol--;
    }

}
