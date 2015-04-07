package com.fedecz.battleship;

import com.fedecz.battleship.Ship.Orientation;

public class Player {
    private OffensiveBoard offensiveBoard;
    private DefensiveBoard defensiveBoard;

    public Player() {
        offensiveBoard = new OffensiveBoard();
        defensiveBoard = new DefensiveBoard();
    }

    public void placeShip(Ship ship, Square square, Orientation orientation) {
        defensiveBoard.placeShip(ship, square, orientation);
    }

    public Attack receiveAttack(Square sq) {
        return defensiveBoard.receiveAttack(sq);
    }

    public void saveAttack(Attack attack) {
        offensiveBoard.saveAttack(attack);
    }

    public boolean areAllShipsPlaced() {
        return defensiveBoard.areAllShipsPlaced();
    }

    public void setDefensiveBoard(DefensiveBoard defensiveBoard) {
        this.defensiveBoard = defensiveBoard;
    }

    public OffensiveBoard getOffensiveBoard() {
        return offensiveBoard;
    }

    public DefensiveBoard getDefensiveBoard() {
        return defensiveBoard;
    }
}
