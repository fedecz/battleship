package com.fedecz.battleship;

public class Miss extends Attack {
    public Miss(Square sq, Integer floatingSq) {
        super(sq, floatingSq);
    }

    @Override
    public void updateBoard(Board board) {
        board.getMisses().add(this.getSq());
    }

    @Override
    public String toString() {
        return ".";
    }
}
