package model.strategies;

import java.util.Collection;

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

	/* (non-Javadoc)
	 * @see model.strategies.IStrategy#nextMove(model.Game, model.Player)
	 */
	@Override
	public Direction nextMove(Game game, Player player) {
		Position position = player.getPosition();		
		Collection<Direction> directions = Direction.shuffledValues();
		
		for (Direction direction : directions) {
			if (!game.isOccupied(position.getNeighbour(direction)))
				return direction;
		}
		
		return player.getDirection();
	}
		 
	
}

	