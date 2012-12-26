package model.strategies;

import model.Direction;
import model.Game;
import model.Player;

/**
 * @author Hjalte S. Jorgensen
 * @version 1.00
 */
public interface IStrategy {
	
	public Direction nextMove(Game game, Player player);

}
