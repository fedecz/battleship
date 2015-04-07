package com.fedecz.battleship;

import com.fedecz.battleship.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class GameTest {
    Game g;

    @Before
    public void setup(){
        g = new Game();
    }

    @Test
    public void shouldBeAbleToCreateANewGameWithDefauls(){
        assertNotNull(g);
    }

    @Test
    public void shouldBeAbleToPlaceAShipInABoard(){
        g.placeShip(1, Ship.PATROL, "E4", Ship.Orientation.VERTICAL);

        Set<Square> myShips = g.getPlayer(1).getDefensiveBoard().getSquaresUsedByMyShips();
        assertEquals(1, myShips.size());
        assertTrue(myShips.contains(new Square("E4")));
    }

    @Test(expected = ShipAlreadyPlacedException.class)
    public void shouldNotBeAbleToPlaceTheSameShipMoreThanOnce(){
        g.placeShip(1, Ship.BATTLESHIP, "E4", Ship.Orientation.VERTICAL);
        g.placeShip(1, Ship.BATTLESHIP, "A1", Ship.Orientation.HORIZONTAL);
        g.placeShip(1, Ship.BATTLESHIP, "A1", Ship.Orientation.HORIZONTAL);
    }

    @Test(expected = ShipOutOfBoundsException.class)
    public void shouldNotBeAbleToPlaceAShipOutOfBounds(){
        g.placeShip(1, Ship.CARRIER, "Z40", Ship.Orientation.VERTICAL);
    }


    @Test(expected = GameHasNotStartedException.class)
    public void shouldNotAttackWithoutStarting(){
        g.attack("A1");
    }

    @Test(expected = ShipsLeftUnplacedException.class)
    public void shouldThrowAnExceptionIfStartedWithOutAllShipsPlaced(){
        g.start();
    }

    @Test(expected = GameHasAlreadyStartedException.class)
    public void shouldThrowAnExceptionIfTryingToPlaceAShipWhenGameStarted(){
        g.placeShip(1, Ship.BATTLESHIP,    "A1", Ship.Orientation.HORIZONTAL);
        g.placeShip(1, Ship.CARRIER,       "A2", Ship.Orientation.HORIZONTAL);
        g.placeShip(1, Ship.CRUISER,       "A3", Ship.Orientation.HORIZONTAL);
        g.placeShip(1, Ship.PATROL,        "A4", Ship.Orientation.HORIZONTAL);
        g.placeShip(1, Ship.SUBMARINE,     "A5", Ship.Orientation.HORIZONTAL);

        g.placeShip(2, Ship.BATTLESHIP,    "A1", Ship.Orientation.HORIZONTAL);
        g.placeShip(2, Ship.CARRIER,       "A2", Ship.Orientation.HORIZONTAL);
        g.placeShip(2, Ship.CRUISER,       "A3", Ship.Orientation.HORIZONTAL);
        g.placeShip(2, Ship.PATROL,        "A4", Ship.Orientation.HORIZONTAL);
        g.placeShip(2, Ship.SUBMARINE,     "A5", Ship.Orientation.HORIZONTAL);
        g.start();

        g.placeShip(1, Ship.BATTLESHIP, "E4", Ship.Orientation.VERTICAL);
    }

    @Test
    public void shouldReturnAGameFinishedResult(){
        g.placeShip(1, Ship.CARRIER,       "A1", Ship.Orientation.HORIZONTAL);
        g.placeShip(1, Ship.BATTLESHIP,    "A2", Ship.Orientation.HORIZONTAL);
        g.placeShip(1, Ship.SUBMARINE,     "A3", Ship.Orientation.HORIZONTAL);
        g.placeShip(1, Ship.CRUISER,       "A4", Ship.Orientation.HORIZONTAL);
        g.placeShip(1, Ship.PATROL,        "A5", Ship.Orientation.HORIZONTAL);

        g.placeShip(2, Ship.CARRIER,       "A1", Ship.Orientation.HORIZONTAL);
        g.placeShip(2, Ship.BATTLESHIP,    "A2", Ship.Orientation.HORIZONTAL);
        g.placeShip(2, Ship.SUBMARINE,     "A3", Ship.Orientation.HORIZONTAL);
        g.placeShip(2, Ship.CRUISER,       "A4", Ship.Orientation.HORIZONTAL);
        g.placeShip(2, Ship.PATROL,        "A5", Ship.Orientation.HORIZONTAL);
        g.start();

        g.attack("A1");//player one
        g.attack("A1");//player two
        g.attack("B1");//player one
        g.attack("A1");//player two
        g.attack("C1");//player one
        g.attack("A1");//player two
        g.attack("D1");//player one
        g.attack("A1");//player two
        g.attack("E1");//player one //goneCarrier
        g.attack("A1");//player two
        g.attack("A2");//player one
        g.attack("A1");//player two
        g.attack("B2");//player one
        g.attack("A1");//player two
        g.attack("C2");//player one
        g.attack("A1");//player two
        g.attack("D2");//player one //gone Battleship
        g.attack("A1");//player two
        g.attack("A3");//player one
        g.attack("A1");//player two
        g.attack("B3");//player one
        g.attack("A1");//player two
        g.attack("C3");//player one //goneSubmarine
        g.attack("A1");//player two
        g.attack("A4");//player one
        g.attack("A1");//player two
        g.attack("B4");//player one // gone cruiser
        g.attack("A1");//player two
        g.attack("A5");//player one // gone patrol

        assertTrue(g.hasFinished());
        assertEquals(g.getPlayer(1), g.getWinner());
    }

    @Test
    public void shouldBeAbleToGenerateRandomBoards(){
        g.generateRandomBoards();

        assertTrue(g.getPlayer(1).areAllShipsPlaced());
        assertTrue(g.getPlayer(2).areAllShipsPlaced());
    }

}
