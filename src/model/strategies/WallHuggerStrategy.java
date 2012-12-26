package model.strategies;

import java.util.Collection;

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
		Collection<Direction> directions = Direction.shuffledValues();
		
		for (Direction direction : directions) {
			Position newPos = position.getNeighbour(direction);
			if (game.isOccupied(newPos)) {
				
				if (newPos.getY() > player.getPosition().getY() ||
						newPos.getY() < player.getPosition().getY()) {
					// wall north or south , walk east if possible else west
					Position posEast = new Position(
										player.getPosition().getX() + 1,
									player.getPosition().getY());
					Position posWest = new Position(
										player.getPosition().getX() - 1,
									player.getPosition().getY());
					
					if (!game.isOccupied(posEast)) 
						return Direction.EAST;
					if (!game.isOccupied(posWest))
						return Direction.WEST;
					
					return player.getDirection().getOpposite();
					
				}
				else {
					// wall east or west, walk north or south
					Position posNorth = new Position(player.getPosition().getX(),
							player.getPosition().getY() + 1);
					Position posSouth = new Position(player.getPosition().getX(),
							player.getPosition().getY() - 1);
					if (!game.isOccupied(posNorth)) 
						return Direction.NORTH;
					if (!game.isOccupied(posSouth))
						return Direction.SOUTH;
					
					return player.getDirection().getOpposite();
				}
			}
				
		}
		return player.getDirection();
	}   
	
}
