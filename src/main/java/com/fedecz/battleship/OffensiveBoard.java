package com.fedecz.battleship;

public class OffensiveBoard extends Board{

    public OffensiveBoard() {
        super();
    }

    public void saveAttack(Attack attack) {
        getAttacks().add(attack);
        attack.updateBoard(this);
    }
}
