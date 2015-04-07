package com.fedecz.battleship;

public abstract class Attack {
    private Square sq;
    private Integer stillFloatingSquares;

    public Attack(Square sq, Integer stillFloatingSquares) {
        this.sq = sq;
        this.stillFloatingSquares = stillFloatingSquares;
    }

    public Square getSq() {
        return sq;
    }

    public Integer getStillFloatingSquares() {
        return stillFloatingSquares;
    }

    public abstract void updateBoard(Board board);
}
