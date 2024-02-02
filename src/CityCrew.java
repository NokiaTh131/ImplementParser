public class CityCrew {
    private int currentRow;
    private int currentCol;
    private Player p;
    private Cell curCell;

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
    }

    public void moveUpLeft() {
        if (currentCol % 2 == 0) {
            currentRow--;
            currentCol--;
            return;
        }
        if(currentCol % 2 == 1){
            currentCol--;
        }
    }

    public void moveUpRight() {
        if (currentCol % 2 == 0) {
            currentRow--;
            currentCol++;
            return;
        }
        if(currentCol % 2 == 1){
            currentCol++;
        }
    }

    public void moveDownRight() {
        if (currentCol % 2 == 0) {
            currentCol++;
            return;
        }
        if(currentCol % 2 == 1){
            currentCol++;
            currentRow++;
        }
    }

    public void moveDown() {
        currentRow++;

    }

    public void moveDownLeft() {
        if (currentCol % 2 == 0) {
            currentCol--;
            return;
        }
        if(currentCol % 2 == 1){
            currentCol--;
            currentRow++;
        }
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

}
