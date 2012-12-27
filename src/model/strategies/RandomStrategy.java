package model.strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import model.Direction;
import model.Game;
import model.Player;
import model.Position;

/**
 * A simple strategy that choses a random, valid direction.
 * 
 * @author Søren Dahlgaard
 * @author Daniel Hessellund Egeberg
 * @version 1
 */
public class RandomStrategy implements IStrategy
{

	@Override
	public Direction nextMove(Game game, Player player) {

		Position position = player.getPosition();		
		ArrayList<Direction> directions = (ArrayList<Direction>) 
				Direction.shuffledValues();
		
		Direction currentDirection = player.getDirection();
		
		for (Direction direction : directions) {
			boolean isOccup = game.isOccupied(position.getNeighbour(direction));
			if (currentDirection == null  && !isOccup)
				return direction;
			if (!isOccup) 
				return direction;
		}
		return currentDirection;
	}
		 
	
}

	