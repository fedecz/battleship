package com.fedecz.battleship;

public class Hit extends Attack {
    public Hit(Square sq, Integer floatingSq) {
        super(sq,floatingSq);
    }

    @Override
    public void updateBoard(Board board) {
        board.getHits().add(this.getSq());
    }

    @Override
    public String toString() {
        return "X";
    }
}
