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

    public boolean solve(BattleShip battleShip, int row, int col, int ships) {
        int length = battleShip.getGrid().length;

        if (ships == 0) return true;

        boolean tryHor = true;
        boolean tryVer = true;

        for (int i = shipCatCounts.length - 1; i >= 0 && (tryHor || tryVer); i--) {
            if (shipCatCounts[i] > 0) {
                int shipSize = i + 1;
                if (tryHor) {
                    iteration++;

                    if (battleShip.placeShip(row, col, shipSize, true)) {
                        shipCatCounts[i]--;

                        if (solve(battleShip, 0, 0, ships - 1)) return true;
                        else {
                            battleShip.removeShip(row, col, shipSize, true);
                            shipCatCounts[i]++;
                        }
                    } else {
                        tryHor = false;
                        if (shipSize == 1) tryVer = false;
                    }
                }

                if (tryVer && shipSize > 1) {
                    iteration++;

                    if (battleShip.placeShip(row, col, shipSize, false)) {
                        shipCatCounts[i]--;

                        if (solve(battleShip, 0, 0, ships - 1)) return true;
                        else {
                            battleShip.removeShip(row, col, shipSize, false);
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

        return solve(battleShip, newRow, newCol, ships);
    }
}
