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

        Bimaru bimaru;

        row = new int[]{6, 1, 3, 2, 2, 2, 3, 1};
        col = new int[]{5, 0, 2, 4, 2, 3, 0, 4};

        row = new int[]{1, 3, 4, 2, 2, 0, 0, 4, 2, 2};
        col = new int[]{3, 0, 4, 1, 2, 1, 4, 0, 4, 1};

        row = new int[]{4, 0, 5, 1, 4, 0, 2, 4};
        col = new int[]{3, 4, 2, 4, 0, 2, 4, 1};

        row = new int[]{1, 3, 2, 3, 2, 2, 0, 6, 0, 1};
        col = new int[]{1, 1, 4, 0, 1, 5, 0, 3, 2, 3};

        row = new int[]{3, 2, 1, 4, 2, 1, 1, 0, 3, 3};
        col = new int[]{1, 5, 1, 2, 0, 4, 1, 3, 2, 1};

        shipCatCounts = new int[]{4, 3, 2, 1};

        Collection<ShipPart> shipParts;

        shipParts = new ArrayList<ShipPart>();
        shipParts.add(new ShipPart(2, 4, '<'));
//        shipParts.add(new ShipPart(2, 5, '>'));
        shipParts.add(new ShipPart(6, 0, 'A'));
//        shipParts.add(new ShipPart(7, 0, 'V'));

//        shipParts.add(new ShipPart(6, 0, '*'));
//        shipParts.add(new ShipPart(7, 0, '*'));
//        shipParts.add(new ShipPart(2, 0, '~'));
//        shipParts.add(new ShipPart(5, 0, '~'));
//        shipParts.add(new ShipPart(5, 5, '~'));

//        shipParts.add(new ShipPart(0, 0, 'A'));
//        shipParts.add(new ShipPart(1, 0, 'V'));
//
//        shipParts.add(new ShipPart(0, 2, '<'));
//        shipParts.add(new ShipPart(0, 3, '-'));
//        shipParts.add(new ShipPart(0, 4, '-'));
//        shipParts.add(new ShipPart(0, 5, '>'));
//
//        shipParts.add(new ShipPart(0, 7, 'O'));
//
//        shipParts.add(new ShipPart(2, 2, 'O'));
//
//        shipParts.add(new ShipPart(3, 0, 'O'));
//
//        shipParts.add(new ShipPart(3, 7, 'A'));
//        shipParts.add(new ShipPart(4, 7, '|'));
//        shipParts.add(new ShipPart(5, 7, 'V'));
//
//        shipParts.add(new ShipPart(4, 3, 'A'));
//        shipParts.add(new ShipPart(5, 3, '|'));
//        shipParts.add(new ShipPart(6, 3, 'V'));
//
//        shipParts.add(new ShipPart(6, 5, 'O'));

        shipParts = new ArrayList<ShipPart>();
        shipParts.add(new ShipPart(0, 2, '~'));
        shipParts.add(new ShipPart(2, 0, 'A'));
        shipParts.add(new ShipPart(2, 8, 'A'));
        shipParts.add(new ShipPart(7, 2, '~'));

        shipParts = new ArrayList<ShipPart>();
        shipParts.add(new ShipPart(4, 1, '~'));

        shipParts = new ArrayList<ShipPart>();
        shipParts.add(new ShipPart(3, 5, '~'));
        shipParts.add(new ShipPart(4, 9, 'A'));

        shipParts = new ArrayList<ShipPart>();
        shipParts.add(new ShipPart(0, 6, '<'));
        shipParts.add(new ShipPart(0, 9, '~'));
        shipParts.add(new ShipPart(3, 1, '#'));
        shipParts.add(new ShipPart(8, 2, '#'));
        shipParts.add(new ShipPart(0, 1, '~'));
//        shipParts.add(new ShipPart(0, 8, '~'));
//        shipParts.add(new ShipPart(1, 3, '~'));
        shipParts.add(new ShipPart(8, 0, '~'));
        shipParts.add(new ShipPart(9, 0, '~'));

        bimaru = new Bimaru(row, col, shipCatCounts.length);

        bimaru.initGrid0(true);

        bimaru.setStartShipParts(shipParts);

        bimaru.placeStartParts();

        printGrid(bimaru);

        int ships = 0;
        for (int cnt : shipCatCounts) ships += cnt;

        Solve solve = new Solve(shipCatCounts);
        if (solve.solve(bimaru, 0, 0, ships)) {
            printGrid(bimaru);
        } else {
            System.out.println("No solution found!");
        }
        System.out.println("Iterations: " + solve.getIteration());
    }

    public static void printGrid(Bimaru bimaru) {
        int maxRow = bimaru.getMaxRow();
        int maxCol = bimaru.getMaxCol();
        char[][] grid = bimaru.getGrid();
        int row[] = bimaru.getRow();
        int col[] = bimaru.getCol();

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
