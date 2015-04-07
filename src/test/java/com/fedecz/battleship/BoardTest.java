package com.fedecz.battleship;

import com.fedecz.battleship.exceptions.OverlappingException;
import com.fedecz.battleship.exceptions.ShipAlreadyPlacedException;
import com.fedecz.battleship.exceptions.ShipOutOfBoundsException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    DefensiveBoard defB;
    OffensiveBoard offB;

    @Before
    public void setup(){
        defB = new DefensiveBoard();
        offB = new OffensiveBoard();
    }

    @Test(expected = ShipAlreadyPlacedException.class)
    public void shouldNotBeAbleToPlaceTheSameShipMoreThanOnce(){
        defB.placeShip(Ship.BATTLESHIP, new Square("E4"), Ship.Orientation.VERTICAL);
        defB.placeShip(Ship.BATTLESHIP, new Square("A1"), Ship.Orientation.HORIZONTAL);
        defB.placeShip(Ship.BATTLESHIP, new Square("A1"), Ship.Orientation.HORIZONTAL);
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipInAnInvalidSquare(){
        try {
            defB.placeShip(Ship.CARRIER, new Square(""), Ship.Orientation.VERTICAL);
            fail();
        } catch (IllegalArgumentException e) {}
        try {
            defB.placeShip(Ship.CARRIER, null, Ship.Orientation.VERTICAL);
            fail();
        } catch (IllegalArgumentException e) {}
        try {
            defB.placeShip(Ship.CARRIER, new Square("40"), Ship.Orientation.VERTICAL);
            fail();
        } catch (IllegalArgumentException e) {}
        try {
            defB.placeShip(Ship.CARRIER, new Square("4A"), Ship.Orientation.VERTICAL);
            fail();
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipWithNullArguments(){
        try {
            defB.placeShip(null, new Square(""), Ship.Orientation.VERTICAL);
            fail();
        } catch (IllegalArgumentException e) {}
        try {
            defB.placeShip(Ship.CARRIER, null, Ship.Orientation.VERTICAL);
            fail();
        } catch (IllegalArgumentException e) {}
        try {
            defB.placeShip(Ship.CARRIER, new Square(""), null);
            fail();
        } catch (IllegalArgumentException e) {}
        try {
            defB.placeShip(null, null, null);
            fail();
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void shouldNotBeAbleToPlaceAShipOutOfBounds(){
        try {
            defB.placeShip(Ship.CARRIER, new Square("Z40"), Ship.Orientation.VERTICAL);
            fail();
        } catch (ShipOutOfBoundsException e) {}
        try {
            defB.placeShip(Ship.CARRIER, new Square("A40"), Ship.Orientation.VERTICAL);
            fail();
        } catch (ShipOutOfBoundsException e) {}
        try {
            defB.placeShip(Ship.CARRIER, new Square("A8"), Ship.Orientation.VERTICAL);
            fail();
        } catch (ShipOutOfBoundsException e) {}
    }

    @Test(expected = OverlappingException.class)
    public void shouldNotBeAbleToPlaceAShipOverlappingOther(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);
        defB.placeShip(Ship.CRUISER, new Square("A4"), Ship.Orientation.HORIZONTAL);
    }


    @Test
    public void shouldThrowAnExceptionIfItChecksNotAllShipsWerePlaced(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);

        assertFalse(defB.areAllShipsPlaced());
    }

    @Test
    public void shouldNotThrowAnExceptionIfItChecksNotAllShipsWerePlaced(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);
        defB.placeShip(Ship.BATTLESHIP, new Square("B1"), Ship.Orientation.VERTICAL);
        defB.placeShip(Ship.CRUISER, new Square("C1"), Ship.Orientation.VERTICAL);
        defB.placeShip(Ship.PATROL, new Square("D1"), Ship.Orientation.VERTICAL);
        defB.placeShip(Ship.SUBMARINE, new Square("E1"), Ship.Orientation.VERTICAL);

        assertTrue(defB.areAllShipsPlaced());
    }


    @Test
    public void shouldReturnACorrectHitWhenAttacked(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);

        Attack attack = defB.receiveAttack(new Square("A2"));

        assertEquals(new Square("A2"), attack.getSq());
        assertTrue(attack instanceof Hit);
        assertEquals(4,attack.getStillFloatingSquares().intValue());
    }

    @Test
    public void shouldReturnACorrectMissWhenAttacked(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);

        Attack attack = defB.receiveAttack(new Square("B2"));

        assertEquals(new Square("B2"), attack.getSq());
        assertTrue(attack instanceof Miss);
        assertEquals(5,attack.getStillFloatingSquares().intValue());
    }

    @Test
    public void shouldAddAttacksToAttacksList(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);

        Attack attack = defB.receiveAttack(new Square("B2"));

        assertTrue(defB.getAttacks().contains(attack));
        assertTrue(defB.getAttacks().size() == 1);
    }

    @Test
    public void shouldAddHitsToHitsSet(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);
        Square sq = new Square("A2");

        defB.receiveAttack(sq);

        assertTrue(defB.getHits().contains(sq));
        assertTrue(defB.getHits().size()==1);
    }

    @Test
    public void shouldAddMissesToMissesSet(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);
        Square sq = new Square("B2");

        defB.receiveAttack(sq);

        assertTrue(defB.getMisses().contains(sq));
        assertTrue(defB.getMisses().size()==1);
    }

    @Test
    public void offensiveBoardShouldRecordAttacks(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);
        Square sq = new Square("B2");
        Attack attack = defB.receiveAttack(sq);

        offB.saveAttack(attack);

        assertTrue(offB.getAttacks().contains(attack));
        assertTrue(offB.getAttacks().size() == 1);
    }

    @Test
    public void offensiveBoardShouldRecordHits(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);
        Square sq = new Square("A2");
        Attack attack = defB.receiveAttack(sq);

        offB.saveAttack(attack);

        assertTrue(offB.getHits().contains(sq));
        assertTrue(offB.getHits().size()==1);
    }

    @Test
    public void offensiveBoardShouldRecordMisses(){
        defB.placeShip(Ship.CARRIER, new Square("A1"), Ship.Orientation.VERTICAL);
        Square sq = new Square("B2");
        Attack attack = defB.receiveAttack(sq);

        offB.saveAttack(attack);

        assertTrue(offB.getMisses().contains(sq));
        assertTrue(offB.getMisses().size()==1);
    }
}
