package at.grege.puzzle;

import java.util.Collection;

/**
 * Created by Gerhard on 05.01.2017.
 */
public class Bimaru {

    Collection<ShipPart> startShipParts;
    private int maxRow;
    private int maxCol;
    private int maxShipSize;
    private char[][] grid;
    private int row[];
    private int col[];
    private boolean start = false;

    public Bimaru(int[] row, int[] col, int maxShipSize) {
        setRow(row);
        setMaxRow(row.length);
        setCol(col);
        setMaxCol(col.length);
        setGrid(new char[getMaxRow()][getMaxCol()]);
        setMaxShipSize(maxShipSize);
    }

    public int getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }

    public int getMaxCol() {
        return maxCol;
    }

    public void setMaxCol(int maxCol) {
        this.maxCol = maxCol;
    }

    public int getMaxShipSize() {
        return maxShipSize;
    }

    public void setMaxShipSize(int maxShipSize) {
        this.maxShipSize = maxShipSize;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void setGrid(char[][] grid) {
        this.grid = grid;
    }

    public int[] getRow() {
        return row;
    }

    public void setRow(int[] row) {
        this.row = row;
    }

    public int[] getCol() {
        return col;
    }

    public void setCol(int[] col) {
        this.col = col;
    }

    public Collection<ShipPart> getStartShipParts() {
        return startShipParts;
    }

    public void setStartShipParts(Collection<ShipPart> startShipParts) {
        this.startShipParts = startShipParts;
    }

    private boolean isShip(int row, int col) {
        return "A|V<->#O".contains(String.valueOf(grid[row][col]));
    }

    public void placeStartParts() {
        Collection<ShipPart> shipParts = getStartShipParts();

        start = true;
        for (ShipPart shipPart : shipParts) {
            try {
                setShipAndWater(shipPart.getRow(), shipPart.getCol(), shipPart.getChr(), false);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                //e.printStackTrace();
            }
        }
        start = false;
    }

    public void initGrid0(boolean first) {
        if (first) {
            for (int i = 0; i < getMaxRow(); i++) {
                for (int j = 0; j < getMaxCol(); j++) {
                    try {
                        fillField(i, j, ' ', false);
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    public void initGrid1(boolean first) {
        initGrid0(first);
        for (int i = 0; i < getMaxCol(); i++) {
            if (getCol()[i] == 0) {
                for (int j = 0; j < getMaxRow(); j++) {
                    try {
                        fillField(j, i, '~', false);
                    } catch (Exception e) {

                    }
                }
            }
        }
        for (int i = 0; i < getMaxRow(); i++) {
            if (getRow()[i] == 0) {
                for (int j = 0; j < getMaxCol(); j++) {
                    try {
                        fillField(i, j, '~', false);
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    public boolean placeShip(int row, int col, int size, boolean horizontal) {
        if (horizontal && ((col + size) > grid.length) || !horizontal && ((row + size) > grid.length)) return false;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j <= size; j++) {
                if (horizontal) {
                    if (i == 0 && j != -1 && j != size) {
                        if (isSet(row + i, col + j)) return false;
                    } else {
                        if (!isFree(row + i, col + j)) return false;
                    }
                } else {
                    if (i == 0 && j != -1 && j != size) {
                        if (isSet(row + j, col + i)) return false;
                    } else {
                        if (!isFree(row + j, col + i)) return false;
                    }
                }
            }
        }

        int count = size;
        for (int i = 0; i < size; i++) {
            if ((horizontal ? grid[row][col + i] : grid[row + i][col]) == '*') {
                count--;
            }
        }
        for (int i = 0; i < grid.length; i++) {
//            if ((horizontal ? grid[row][i] : grid[i][col]) == '#' || ((horizontal ? grid[row][i] : grid[i][col]) == '*'))
            if ((horizontal ? isShip(row, i) : isShip(i, col)) || ((horizontal ? grid[row][i] : grid[i][col]) == '*'))
                count++;
        }
        if (count > (horizontal ? this.row[row] : this.col[col])) return false;

        for (int i = 0; i < size; i++) {
            if ((horizontal ? grid[row][col + i] : grid[row + i][col]) == '*') count = 0;
            else count = 1;
            for (int j = 0; j < grid.length; j++) {
//                if ((horizontal ? grid[j][col + i] : grid[row + i][j]) == '#' || (horizontal ? grid[j][col + i] : grid[row + i][j]) == '*')
                if ((horizontal ? isShip(j, col + i) : isShip(row + i, j)) || (horizontal ? grid[j][col + i] : grid[row + i][j]) == '*')
                    count++;
            }
            if (count > (horizontal ? this.col[col + i] : this.row[row + i])) return false;
        }

//        for (int i = 0; i < size; i++) {
//            if (horizontal) {
//                this.grid[row][col + i] = '#';
//            } else {
//                this.grid[row + i][col] = '#';
//            }
//        }

//        for (int i = 0; i < size; i++) {
            if (size == 1) this.grid[row][col] = 'O';
            else if (horizontal) {
                this.grid[row][col] = '<';
                for (int j = 1; j < size - 1; j++) {
                    this.grid[row][col + j] = '-';
                }
                this.grid[row][col + size - 1] = '>';
            } else {
                this.grid[row][col] = 'A';
                for (int j = 1; j < size - 1; j++) {
                    this.grid[row + j][col] = '|';
                }
                this.grid[row + size - 1][col] = 'V';
            }
//        }
        //System.out.println("placeShip: " + (horizontal ? "hor," : "ver,") + row + "," + col + "," + size);
        //Main.printGrid(this);

        return true;
    }

    private boolean isFree(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) return true;

        if (grid[row][col] == ' ' || grid[row][col] == '~') return true;

        return false;
    }

    private boolean isSet(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) return false;

//        if (grid[row][col] == '#' || grid[row][col] == '~') return true;
        if (isShip(row, col) || grid[row][col] == '~') return true;

        return false;
    }

    public void removeShip(int row, int col, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                this.grid[row][col + i] = ' ';
            } else {
                this.grid[row + i][col] = ' ';
            }
        }
        //System.out.println("removeShip: " + (horizontal ? "hor," : "ver,") + row + "," + col + "," + size);
        placeStartParts();
    }

    public void setShipAndWater(int row, int col, char chr, boolean reset) throws Exception {

        if (row < 0) throw new Exception("row must not lower than 0, but is " + row);
        if (row >= getMaxRow())
            throw new Exception("row must not greater than " + (getMaxRow() - 1) + ", but is " + row);
        if (col < 0) throw new Exception("col must not lower than 0, but is " + col);
        if (col >= getMaxCol())
            throw new Exception("col must not greater than " + (getMaxCol() - 1) + ", but is " + col);

        switch (chr) {
            case 'A':
                fillField(row - 1, col - 1, '~', reset);
                fillField(row - 1, col + 0, '~', reset);
                fillField(row - 1, col + 1, '~', reset);
                fillField(row + 0, col - 1, '~', reset);
                fillField(row + 0, col + 0, chr, reset);
                fillField(row + 0, col + 1, '~', reset);
                fillField(row + 1, col - 1, '~', reset);
                fillField(row + 1, col + 0, '*', reset);
                fillField(row + 1, col + 1, '~', reset);
                fillField(row + 2, col - 1, '~', reset);
                fillField(row + 2, col + 1, '~', reset);
                break;
            case '|':
                fillField(row - 2, col - 1, '~', reset);
                fillField(row - 2, col + 1, '~', reset);
                fillField(row - 1, col - 1, '~', reset);
                fillField(row - 1, col + 0, '*', reset);
                fillField(row - 1, col + 1, '~', reset);
                fillField(row + 0, col - 1, '~', reset);
                fillField(row + 0, col + 0, chr, reset);
                fillField(row + 0, col + 1, '~', reset);
                fillField(row + 1, col - 1, '~', reset);
                fillField(row + 1, col + 0, '*', reset);
                fillField(row + 1, col + 1, '~', reset);
                fillField(row + 2, col - 1, '~', reset);
                fillField(row + 2, col + 1, '~', reset);
                break;
            case 'V':
                fillField(row - 2, col - 1, '~', reset);
                fillField(row - 2, col + 1, '~', reset);
                fillField(row - 1, col - 1, '~', reset);
                fillField(row - 1, col + 0, '*', reset);
                fillField(row - 1, col + 1, '~', reset);
                fillField(row + 0, col - 1, '~', reset);
                fillField(row + 0, col + 0, chr, reset);
                fillField(row + 0, col + 1, '~', reset);
                fillField(row + 1, col - 1, '~', reset);
                fillField(row + 1, col + 0, '~', reset);
                fillField(row + 1, col + 1, '~', reset);
                break;
            case 'O':
                fillField(row - 1, col - 1, '~', reset);
                fillField(row - 1, col + 0, '~', reset);
                fillField(row - 1, col + 1, '~', reset);
                fillField(row + 0, col - 1, '~', reset);
                fillField(row + 0, col + 0, chr, reset);
                fillField(row + 0, col + 1, '~', reset);
                fillField(row + 1, col - 1, '~', reset);
                fillField(row + 1, col + 0, '~', reset);
                fillField(row + 1, col + 1, '~', reset);
                break;
            case '#':
                fillField(row - 1, col - 1, '~', reset);
                fillField(row - 1, col + 1, '~', reset);
                fillField(row + 0, col + 0, chr, reset);
                fillField(row + 1, col - 1, '~', reset);
                fillField(row + 1, col + 1, '~', reset);
                break;
            case '<':
                fillField(row - 1, col - 1, '~', reset);
                fillField(row - 1, col + 0, '~', reset);
                fillField(row - 1, col + 1, '~', reset);
                fillField(row - 1, col + 2, '~', reset);
                fillField(row + 0, col - 1, '~', reset);
                fillField(row + 0, col + 0, chr, reset);
                fillField(row + 0, col + 1, '*', reset);
                fillField(row + 1, col - 1, '~', reset);
                fillField(row + 1, col + 0, '~', reset);
                fillField(row + 1, col + 1, '~', reset);
                fillField(row + 1, col + 2, '~', reset);
                break;
            case '-':
                fillField(row - 1, col - 2, '~', reset);
                fillField(row - 1, col - 1, '~', reset);
                fillField(row - 1, col + 0, '~', reset);
                fillField(row - 1, col + 1, '~', reset);
                fillField(row - 1, col + 2, '~', reset);
                fillField(row + 0, col - 1, '*', reset);
                fillField(row + 0, col + 0, chr, reset);
                fillField(row + 0, col + 1, '*', reset);
                fillField(row + 1, col - 2, '~', reset);
                fillField(row + 1, col - 1, '~', reset);
                fillField(row + 1, col + 0, '~', reset);
                fillField(row + 1, col + 1, '~', reset);
                fillField(row + 1, col + 2, '~', reset);
                break;
            case '>':
                fillField(row - 1, col - 2, '~', reset);
                fillField(row - 1, col - 1, '~', reset);
                fillField(row - 1, col + 0, '~', reset);
                fillField(row - 1, col + 1, '~', reset);
                fillField(row + 0, col - 1, '*', reset);
                fillField(row + 0, col + 0, chr, reset);
                fillField(row + 0, col + 1, '~', reset);
                fillField(row + 1, col - 2, '~', reset);
                fillField(row + 1, col - 1, '~', reset);
                fillField(row + 1, col + 0, '~', reset);
                fillField(row + 1, col + 1, '~', reset);
                break;
            default:
                fillField(row, col, chr, reset);
        }
    }

    private void fillField(int row, int col, char chr, boolean reset) throws Exception {
        if (reset) chr = ' ';
        if (start && chr != '~' && chr != ' ') chr = '*';
        if (row < 0 || row >= getMaxRow() || col < 0 || col >= getMaxCol()) {
            return;
        }
        if (getGrid()[row][col] == chr) return;
        if (getGrid()[row][col] != '\u0000' && getGrid()[row][col] != ' ' && !reset) {
            if (getGrid()[row][col] == '~')
                throw new Exception("field (" + row + ", " + col + ") is already water and not " + chr);
            if (getGrid()[row][col] == '*' && chr == '~')
                throw new Exception("field (" + row + ", " + col + ") is already boat and not water");
            if (chr == '*')
                return;
            if (getGrid()[row][col] != '*')
                throw new Exception("field (" + row + ", " + col + ") is already " + getGrid()[row][col] + " and not " + chr);
        }
        getGrid()[row][col] = chr;
    }
}
