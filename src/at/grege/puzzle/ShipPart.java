package at.grege.puzzle;

/**
 * Created by Gerhard on 06.01.2017.
 */
public class ShipPart {

    private int row;
    private int col;
    private char chr;

    public ShipPart(int row, int col, char chr) {
        setRow(row);
        setCol(col);
        setChr(chr);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public char getChr() {
        return chr;
    }

    public void setChr(char chr) {
        this.chr = chr;
    }
}
