package com.fedecz.battleship;

public class Square {
    private Integer column;
    private Integer row;

    public Square(Integer column, Integer row) {
        this.column = column;
        this.row = row;
    }

    public Square(String square) {
        if(square == null || square.trim().length() == 0)
            throw new IllegalArgumentException("square can't be null nor empty.");
        if(!square.matches("[a-zA-Z]\\d+")){
            throw new IllegalArgumentException("Square should be the form A5, F12, etc, depending on board size.");
        }
        this.column = square.toUpperCase().charAt(0) - 'A';
        this.row = Integer.valueOf(square.substring(1, square.length()));
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Square square = (Square) o;

        if (!column.equals(square.column)) return false;
        if (!row.equals(square.row)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = column.hashCode();
        result = 31 * result + row.hashCode();
        return result;
    }

    @Override
    public String toString(){
        return ""+((char)('A' + column))+row;
    }
}
