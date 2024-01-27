public interface Command {
    default public int calculateMinMovingDistance(int startRow, int startCol, int destRow, int destCol) {
        // Convert cube coordinates to axial coordinates
        int startQ = startCol;
        int startR = startRow - (startCol + (startCol & 1)) / 2;

        int destQ = destCol;
        int destR = destRow - (destCol + (destCol & 1)) / 2;

        // Calculate axial coordinates distance
        int dQ = Math.abs(destQ - startQ);
        int dR = Math.abs(destR - startR);
        int dS = Math.abs(-destQ - destR + startQ + startR);

        // Return the maximum coordinate difference as the minimum moving distance
        return Math.max(dQ, Math.max(dR, dS));
    }
}
