package org.vut.ija_project.Common;

/**
 * Class Represents Position in Environment
 */
public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Position other = (Position) o;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        //multiply column by 31 here, so we could differ
        //(2, 1) from (1, 2)
        return Integer.hashCode(col) * 31 + Integer.hashCode(col);
    }

}
