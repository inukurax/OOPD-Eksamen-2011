package model.strategies;

import java.util.Collection;
import java.util.Random;

import model.Direction;
import model.Game;
import model.Player;
import model.Position;
import model.config.LineSegment;

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
		LineSegment lineRight = new LineSegment (position, 
				new Position(game.getWidth(), position.getY()));
		LineSegment lineLeft = new LineSegment(position,
				new Position(0, position.getY()));
		LineSegment lineUp = new LineSegment(position, 
				new Position(position.getX(), 0));
		LineSegment lineDown = new LineSegment(
				 position,new Position(position.getX(), game.getHeight()));
		Direction currentDirection = player.getDirection();
		Position NorthPos = player.getPosition().getNeighbour(Direction.NORTH);
		Position SouthPos = player.getPosition().getNeighbour(Direction.SOUTH);
		Position EastPos = player.getPosition().getNeighbour(Direction.EAST);
		Position WestPos = player.getPosition().getNeighbour(Direction.WEST);

		Collection<Direction> directions = Direction.shuffledValues();
		if (currentDirection == null) {
			for (Direction direction : directions) {
				boolean isOccup = game.isOccupied(position.getNeighbour(direction));
				if (currentDirection == null  && !isOccup)
					return direction;
			}
		}
			if (currentDirection != null && !game.isOccupied(position.getNeighbour(currentDirection)))
				return currentDirection;
			switch (currentDirection) {
			case NORTH : if (game.isOccupied(NorthPos) && game.isOccupied(WestPos)) 
							return Direction.EAST;
			             if (game.isOccupied(NorthPos) && game.isOccupied(EastPos)) 
			            	return Direction.WEST;
				         if (game.isOccupied(NorthPos)) {
			             if (this.getFreePositions(lineLeft, game) < this.getFreePositions(lineRight, game))
			            		return Direction.WEST;
			            	else 
			            		return Direction.EAST;
				         }
			case SOUTH : if (game.isOccupied(SouthPos) && game.isOccupied(WestPos)) 
							return Direction.EAST;
				         if (game.isOccupied(SouthPos) && game.isOccupied(EastPos)) 
				           	return Direction.WEST;
				         if (game.isOccupied(SouthPos)) {
				             if (this.getFreePositions(lineLeft, game) > this.getFreePositions(lineRight, game))
				           		return Direction.WEST;
				           	else 
				           		return Direction.EAST;
				         }
			case EAST : if (game.isOccupied(EastPos) && game.isOccupied(SouthPos)) 
									return Direction.NORTH;
					    if (game.isOccupied(EastPos) && game.isOccupied(NorthPos)) 
						           	return Direction.SOUTH;
						if (game.isOccupied(EastPos)) {
				             if (this.getFreePositions(lineDown, game) < this.getFreePositions(lineUp, game))
								return Direction.SOUTH;
						  	else 
						        return Direction.NORTH;
						}
			case WEST : if (game.isOccupied(WestPos) && game.isOccupied(SouthPos)) 
									return Direction.NORTH;
					    if (game.isOccupied(WestPos) && game.isOccupied(NorthPos)) 
						           	return Direction.SOUTH;
						if (game.isOccupied(WestPos)) {
				             if (this.getFreePositions(lineDown, game) > this.getFreePositions(lineUp, game))
								return Direction.SOUTH;
						  	else 
						        return Direction.NORTH;
						}
			}
	
		return currentDirection;
	}   
	
	public int getFreePositions (LineSegment line, Game game) {
		Collection<Position> pos = line.getPositions();
		int i = 0;
		for (Position position : pos) {
			if (!line.getP1().equals(position)) {
			if (game.isOccupied(position))
				return i;
			}
			i++;	
		}
		return i;
	}
	
}
