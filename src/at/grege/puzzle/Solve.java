package at.grege.puzzle;

/**
 * Created by Gerhard on 06.01.2017.
 */
public class Solve {

    private int[] shipCatCounts;
    private int iteration;

    public Solve(int[] shipCatCounts) {
        setShipCatCounts(shipCatCounts);
        iteration = 0;
    }

    public int[] getShipCatCounts() {
        return shipCatCounts;
    }

    public void setShipCatCounts(int[] shipCatCounts) {
        this.shipCatCounts = shipCatCounts;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public boolean solve(Bimaru bimaru, int row, int col, int ships) {
        int length = bimaru.getGrid().length;

        if (ships == 0) return true;

        boolean tryHor = true;
        boolean tryVer = true;

        for (int i = shipCatCounts.length - 1; i >= 0 && (tryHor || tryVer); i--) {
            if (shipCatCounts[i] > 0) {
                int shipSize = i + 1;
                if (tryHor) {
                    iteration++;

                    if (bimaru.placeShip(row, col, shipSize, true)) {
                        shipCatCounts[i]--;

                        if (solve(bimaru, 0, 0, ships - 1)) return true;
                        else {
                            bimaru.removeShip(row, col, shipSize, true);
                            shipCatCounts[i]++;
                        }
                    } else {
                        tryHor = false;
                        if (shipSize == 1) tryVer = false;
                    }
                }

                if (tryVer && shipSize > 1) {
                    iteration++;

                    if (bimaru.placeShip(row, col, shipSize, false)) {
                        shipCatCounts[i]--;

                        if (solve(bimaru, 0, 0, ships - 1)) return true;
                        else {
                            bimaru.removeShip(row, col, shipSize, false);
                            shipCatCounts[i]++;
                        }
                    } else {
                        tryVer = false;
                    }
                }
            }
        }

        int newRow = row;
        int newCol = col + 1;
        if (newCol >= length) {
            newCol = 0;
            newRow = row + 1;
        }
        if (newRow >= length) return false;

        return solve(bimaru, newRow, newCol, ships);
    }
}
