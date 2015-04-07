package com.fedecz.battleship;

public enum Ship {
    CARRIER     (5),
    BATTLESHIP  (4),
    SUBMARINE   (3),
    CRUISER     (2),
    PATROL      (1);

    private Integer size;

    Ship(int size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }



    public enum Orientation {VERTICAL, HORIZONTAL}
}
