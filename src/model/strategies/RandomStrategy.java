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
	private Random random = new Random();

	/* (non-Javadoc)
	 * @see model.strategies.IStrategy#nextMove(model.Game, model.Player)
	 */
	@Override
	public Direction nextMove(Game game, Player player) {

		Position position = player.getPosition();		
		ArrayList<Direction> directions = (ArrayList<Direction>) Direction.shuffledValues();
		Direction currentDirection = player.getDirection();
		if (currentDirection == null) {
			Direction randomDirection = directions.get(random .nextInt(3));
			if (!game.isOccupied(position.getNeighbour(randomDirection)))
				return randomDirection;
		}
		
		for (Direction direction : directions) {
			if (!game.isOccupied(position.getNeighbour(direction)))
				return direction;
		}
		
		return player.getDirection();
	}
		 
	
}

	