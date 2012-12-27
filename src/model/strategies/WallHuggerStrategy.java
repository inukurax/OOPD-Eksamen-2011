package model.strategies;

import java.util.Collection;
import java.util.Random;

import model.Direction;
import model.Game;
import model.Player;
import model.Position;

/**
 * A strategy that attempts to move onto a position that is 
 * adjacent to a wall.
 * 
 * @author Daniel Hessellund Egeberg
 * @version 1
 */
public class WallHuggerStrategy implements IStrategy
{

	/* (non-Javadoc)
	 * @see model.strategies.IStrategy#nextMove(model.Game, model.Player)
	 */
	@Override
	public Direction nextMove(Game game, Player player) {
		Position position = player.getPosition();	
		Direction currentDirection = player.getDirection();
		Position NorthPos = player.getPosition().getNeighbour(Direction.NORTH);
		Position SouthPos = player.getPosition().getNeighbour(Direction.SOUTH);
		Position EastPos = player.getPosition().getNeighbour(Direction.EAST);
		Position WestPos = player.getPosition().getNeighbour(Direction.WEST);

		Collection<Direction> directions = Direction.shuffledValues();
   	 Random random = new Random();

			for (Direction direction : directions) {
				boolean isOccup = game.isOccupied(position.getNeighbour(direction));
				if (currentDirection == null  && !isOccup)
					return direction;
			}
			if (currentDirection != null && !game.isOccupied(position.getNeighbour(currentDirection)))
				return currentDirection;
			switch (currentDirection) {
			case NORTH : if (game.isOccupied(NorthPos) && game.isOccupied(WestPos)) 
							return Direction.EAST;
			             if (game.isOccupied(NorthPos) && game.isOccupied(EastPos)) 
			            	return Direction.WEST;
			             if (game.isOccupied(NorthPos)) {
			            	if (random.nextInt(1) == 1)
			            		return Direction.WEST;
			            	else 
			            		return Direction.EAST;
			             }
			case SOUTH : if (game.isOccupied(SouthPos) && game.isOccupied(WestPos)) 
								return Direction.EAST;
				         if (game.isOccupied(SouthPos) && game.isOccupied(EastPos)) 
				           	return Direction.WEST;
				         if (game.isOccupied(SouthPos)) {
				           	if (random.nextInt(1) == 1)
				           		return Direction.WEST;
				           	else 
				           		return Direction.EAST;
				         }
			case EAST : if (game.isOccupied(EastPos) && game.isOccupied(SouthPos)) 
									return Direction.NORTH;
					    if (game.isOccupied(EastPos) && game.isOccupied(NorthPos)) 
						           	return Direction.SOUTH;
						if (game.isOccupied(EastPos)) {
							if (random.nextInt(1) == 1)
								return Direction.SOUTH;
						  	else 
						        return Direction.NORTH;
						}
			case WEST : if (game.isOccupied(WestPos) && game.isOccupied(SouthPos)) 
									return Direction.NORTH;
					    if (game.isOccupied(WestPos) && game.isOccupied(NorthPos)) 
						           	return Direction.SOUTH;
						if (game.isOccupied(WestPos)) {
							if (random.nextInt(1) == 1)
								return Direction.SOUTH;
						  	else 
						        return Direction.NORTH;
						}
			}
	
		return currentDirection;
	}   
	
}
