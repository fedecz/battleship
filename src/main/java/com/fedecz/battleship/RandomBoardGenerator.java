package com.fedecz.battleship;

import java.util.Random;

public class RandomBoardGenerator {

    private static final int MAX_RETRIES = 100;

    public DefensiveBoard getNewDefensiveBoard() {
        DefensiveBoard b = new DefensiveBoard();
        Random r = new Random();
        for(Ship s : Ship.values()){
            if(!placeShip(b, r, s))
                throw new RuntimeException("Could not place ship");
        }
        return b;
    }

    private boolean placeShip(DefensiveBoard b, Random r, Ship s) {
        for (int i = 0 ;i < MAX_RETRIES; i++) {
            Ship.Orientation orientation = getRandomOrientation(r);
            Square sq = getRandomSquare(r);
            try {
                b.placeShip(s,sq,orientation);
                return true;
            } catch (Exception e) {}
        }
        System.out.println("Couldnt place ship " + s);
        return false;
    }

    private Square getRandomSquare(Random r) {
        int col = r.nextInt(Game.MAX_BOARD_SIZE);
        int row = r.nextInt(Game.MAX_BOARD_SIZE);
        return new Square(col, row);
    }

    private Ship.Orientation getRandomOrientation(Random r) {
        return r.nextBoolean()? Ship.Orientation.HORIZONTAL: Ship.Orientation.VERTICAL;
    }
}
