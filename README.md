Battleship
========
This is my version of the common board game Battleship. It's only intended as educational purpose only, but all pull requests are always welcome.

#### What should you expect?

So far there is no CLI, or no presentation layer of the game. It is only a few classes that represent the game itself, and it's playable (as long you implement some interface to use it :) ).

#### How should I use it?

You should first instantiate a Game class. By default it creates 2 players with empty boards for you to start placing the ships.

```
Game game = new Game();

game.placeShip(1, Ship.BATTLESHIP, "A3", Orientation.HORIZONTAL);
game.placeShip(2, Ship.CRUISER, "E8", Orientation.HORIZONTAL);
```
once players 1 and 2 finish placing their ships...
```
game.start();
```
Now is the time to attack();
```
Attack myAttack = game.attack("F3");
```
If you noticed, attack() doesn't accept a player number, that's because the object game controls the turns and it starts by default with player 1, then 2 and so on. I wanted to model it like this in order to let Game control how the game is played, and not let the interface do it. Notice that you can call *placeShip()* in the order you want (first player 1, then 2 or whatever), but once all players have placed their ships and game started, Game takes control.

The *Attack* class is an abstract class that has two child implementations *Hit* and *Miss*, depending on the attacks outcome.

Every attack is recorded in the offensive and defensive boards from each player. However, there's no implementation to print the actual status of the boards (keep it simple).

After every turn you should call *game.hasFinished()* which will return true in the case there was a winner. For that case you can get the winner by calling game.getWinner(); I would've liked to return if there was a winner or not in the Attack implementation so that the client doesn't have to call game.hasFinished() every time, but I ran out of time. Could be next iteration.

#### Some considerations:

A ship is nothing else than a Set of squares in a board that should not be repeated among different ships at all. A player then wins when he/she hits all the squares that belong to a ship in a board. It doesn't really matter (in this implementation) if the square belongs to a BattleSHip, a Patrol, or a Cruiser. I just wanted to keep things simple. In the future I might add the ship information so that the game can actually answer questions like: "what ships are sill afloat?", "how many ships have I sunk?", etc. So far now is more like "How many squares have I hit?"
