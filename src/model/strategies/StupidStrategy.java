package model.strategies;

import model.Direction;
import model.Game;
import model.Player;
import model.Position;

/**
 * @author Hjalte S. Jorgensen
 * @version 1.00
 */
public class StupidStrategy implements IStrategy {

	/* (non-Javadoc)
	 * @see model.strategies.IStrategy#nextMove(model.Game, model.Player)
	 */
	@Override
	public Direction nextMove(Game game, Player player) {
		Position NorthPos = player.getPosition().getNeighbour(Direction.NORTH);
		Position SouthPos = player.getPosition().getNeighbour(Direction.SOUTH);
		Position EastPos = player.getPosition().getNeighbour(Direction.EAST);
		Position WestPos = player.getPosition().getNeighbour(Direction.WEST);
		
		if (!game.isOccupied(WestPos)) 
			return Direction.WEST;		
		if (!game.isOccupied(EastPos)) 
				return Direction.EAST;
		if (!game.isOccupied(NorthPos)) 
			return Direction.NORTH;

		if (!game.isOccupied(SouthPos)) 
			return Direction.SOUTH;

		return Direction.EAST;
	}

}
