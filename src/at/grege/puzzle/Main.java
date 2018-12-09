package at.grege.puzzle;

import java.util.ArrayList;
import java.util.Collection;

public class Main {

    // A   A   A   O
    // |   |   V
    // |   V
    // V
    // <--> <-> <> O


    public static void main(String[] args) {
        int row[];
        int col[];
        int shipCatCounts[];

        BattleShip battleShip;

        Collection<ShipPart> shipParts;

        col = new int[]{1,0,1,2,3,4,1,2,3,2};
        row = new int[]{1,2,1,7,1,1,1,2,2,1,};

        shipParts = new ArrayList<ShipPart>();
        shipParts.add(new ShipPart(4, 9, 'V'));
        shipParts.add(new ShipPart(6, 8, 'A'));

        shipCatCounts = new int[]{3, 3, 2, 1};

        battleShip = new BattleShip(row, col, shipCatCounts.length);

        battleShip.initGrid0(true);

        battleShip.setStartShipParts(shipParts);

        battleShip.placeStartParts();

        printGrid(battleShip);

        int ships = 0;
        for (int cnt : shipCatCounts) ships += cnt;

        Solve solve = new Solve(shipCatCounts);
        if (solve.solve(battleShip, 0, 0, ships)) {
            printGrid(battleShip);
        } else {
            System.out.println("No solution found!");
        }
        System.out.println("Iterations: " + solve.getIteration());
    }

    public static void printGrid(BattleShip battleShip) {
        int maxRow = battleShip.getMaxRow();
        int maxCol = battleShip.getMaxCol();
        char[][] grid = battleShip.getGrid();
        int row[] = battleShip.getRow();
        int col[] = battleShip.getCol();

        System.out.print("+-");
        for (int i = 0; i < maxRow; i++) {
            System.out.print("--");
        }
        System.out.println('+');
        for (int i = 0; i < maxRow; i++) {
            System.out.print("| ");
            for (int j = 0; j < maxCol; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("| " + row[i]);
        }
        System.out.print("+-");
        for (int i = 0; i < maxRow; i++) {
            System.out.print("--");
        }
        System.out.println('+');
        System.out.print("  ");
        for (int i = 0; i < maxCol; i++) {
            System.out.print(col[i] + " ");
        }
        System.out.println();
    }
}
