package com.fedecz.battleship;

import com.fedecz.battleship.exceptions.OverlappingException;
import com.fedecz.battleship.exceptions.ShipAlreadyPlacedException;
import com.fedecz.battleship.exceptions.ShipOutOfBoundsException;
import com.fedecz.battleship.exceptions.ShipsLeftUnplacedException;

import java.util.*;

public class DefensiveBoard extends Board{
    private Set<Ship> placedShips;
    private Set<Square> squaresUsedByMyShips;

    public DefensiveBoard() {
        super();
        placedShips = new HashSet<>(Ship.values().length);
        squaresUsedByMyShips = new HashSet<>(15);
    }

    public boolean areAllShipsPlaced() {
        return placedShips.size() == Ship.values().length;
    }

    public void placeShip(Ship ship, Square square, Ship.Orientation orientation) {
        checkPlaceArguments(ship, square, orientation);
        placedShips.add(ship);
        squaresUsedByMyShips.addAll(getSquaresUsedByShip(ship.getSize(), square, orientation));
    }

    private void checkPlaceArguments(Ship ship, Square square, Ship.Orientation orientation) {
        if(ship == null || square == null || orientation == null)
            throw new IllegalArgumentException("Can't pass null arguments");
        if(hasAlreadyPlaced(ship))
            throw new ShipAlreadyPlacedException();
        if(isOutOfBounds(ship, square, orientation))
            throw new ShipOutOfBoundsException();
        if(overlapsOtherShip(ship, square, orientation))
            throw new OverlappingException();
    }

    private boolean overlapsOtherShip(Ship ship, Square square, Ship.Orientation orientation) {
        Collection<? extends Square> squaresUsedByShip = getSquaresUsedByShip(ship.getSize(), square, orientation);
        for(Square s: squaresUsedByShip){
            if(squaresUsedByMyShips.contains(s))
                return true;
        }
        return false;
    }


    private boolean isOutOfBounds(Ship ship, Square startingSquare, Ship.Orientation orientation) {
        return !(isInRange(startingSquare) && isInRange(getEndingSquare(ship, startingSquare, orientation)));
    }

    private Square getEndingSquare(Ship ship, Square startingSquare, Ship.Orientation orientation) {
        return orientation == Ship.Orientation.HORIZONTAL?
                new Square(startingSquare.getColumn() + ship.getSize(), startingSquare.getRow()):
                new Square(startingSquare.getColumn(), startingSquare.getRow() + ship.getSize());
    }

    private boolean isInRange(Square square) {
        return square.getRow() < Game.MAX_BOARD_SIZE && square.getColumn() <  Game.MAX_BOARD_SIZE;
    }

    private boolean hasAlreadyPlaced(Ship ship) {
        return placedShips.contains(ship);
    }

    private Collection<? extends Square> getSquaresUsedByShip(Integer size, Square square, Ship.Orientation orientation) {
        if(size==1) return Arrays.asList(square);
        ArrayList<Square> a = new ArrayList<>();
        a.add(square);
        if(orientation == Ship.Orientation.HORIZONTAL){
            a.addAll(getSquaresUsedByShip(size -1, new Square(square.getColumn() + 1, square.getRow()), orientation));
        }else{
            a.addAll(getSquaresUsedByShip(size -1, new Square(square.getColumn(), square.getRow() + 1), orientation));
        }
        return a;
    }

    public Attack receiveAttack(Square sq) {
        Attack result;
        if (squaresUsedByMyShips.contains(sq)){
            getHits().add(sq);
            result = new Hit(sq, squaresUsedByMyShips.size() - getHits().size());
        }else {
            getMisses().add(sq);
            result = new Miss(sq,squaresUsedByMyShips.size() - getHits().size());
        }
        getAttacks().add(result);
        return result;
    }

    public Set<Square> getSquaresUsedByMyShips() {
        return squaresUsedByMyShips;
    }
}
