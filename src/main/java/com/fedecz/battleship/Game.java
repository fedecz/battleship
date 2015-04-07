package com.fedecz.battleship;

import com.fedecz.battleship.Ship.Orientation;
import com.fedecz.battleship.exceptions.GameHasAlreadyStartedException;
import com.fedecz.battleship.exceptions.GameHasFinishedException;
import com.fedecz.battleship.exceptions.GameHasNotStartedException;
import com.fedecz.battleship.exceptions.ShipsLeftUnplacedException;

import java.util.HashMap;
import java.util.Map;

public class Game {
    public static int MAX_BOARD_SIZE = 10;
    private Boolean hasStarted = false;
    private Boolean hasFinished = false;
    private Integer currentTurn = 1;
    private Map<Integer, Player> players;
    private Player winner;

    public Game() {
        players = new HashMap<>(2);
        players.put(1, new Player());
        players.put(2, new Player());
    }

    public Player getPlayer(Integer playerNum){
        return players.get(playerNum);
    }

    public void placeShip(Integer player, Ship ship, String square, Orientation orientation) {
        checkGameStatus();
        getPlayer(player).placeShip(ship, new Square(square), orientation);
    }

    public Attack attack(String square) {
        checkIfHasAlreadyFinished();
        if(!hasStarted)
            throw new GameHasNotStartedException();
        Square sq = new Square(square);
        Attack attack = getOpponentsPlayer().receiveAttack(sq);
        getMyPlayer().saveAttack(attack);
        if(attack.getStillFloatingSquares() == 0 ){
            hasFinished = true;
            this.winner = getMyPlayer();
        }
        currentTurn++;
        return attack;
    }

    public Boolean hasFinished(){
        if(!hasStarted)
            throw new GameHasNotStartedException();
        return hasFinished;
    }

    private Player getMyPlayer() {
        if (currentTurn % 2 > 0)
            return players.get(1);
        return players.get(2);
    }

    private Player getOpponentsPlayer() {
        if (currentTurn % 2 > 0)
            return players.get(2);
        return players.get(1);
    }

    public void start() {
        checkGameStatus();
        checkAllShipsArePlaced(players);
        hasStarted = true;
    }

    private void checkAllShipsArePlaced(Map<Integer, Player> players) {
        for(Player p : players.values()){
            if(!p.areAllShipsPlaced())
                throw new ShipsLeftUnplacedException();
        }
    }

    private void checkGameStatus() {
        checkIfHasAlreadyFinished();
        checkIfHasAlreadyStarted();
    }

    private void checkIfHasAlreadyStarted() {
        if(hasStarted)
            throw new GameHasAlreadyStartedException();
    }

    private void checkIfHasAlreadyFinished() {
        if(hasFinished)
            throw new GameHasFinishedException();
    }

    public Player getWinner() {
        return winner;
    }

    public void generateRandomBoards() {
        RandomBoardGenerator boardGenerator = new RandomBoardGenerator();
        for(Player p : players.values()){
            p.setDefensiveBoard(boardGenerator.getNewDefensiveBoard());
        }
    }
}
