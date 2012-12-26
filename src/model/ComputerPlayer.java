package model;

import model.strategies.IStrategy;
import java.awt.Color;


/**
 * A computer player in the game of Tron. This computer is controlled by
 * the strategy specified. It is possible to change the strategy mid way.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @version 1
 */
public class ComputerPlayer extends Player
{    
	
	private IStrategy pcStrategy;
    /**
     * Constructs a new computer player with the specified strategy, name and color
     * 
     * @param   color       The color of the player
     * @param   name        The name of the player
     * @param   strategy    The strategy of the player
     */
    public ComputerPlayer(Color color, String name, IStrategy strategy)
    {
        super(color, name);
        this.pcStrategy = strategy;
    }

	@Override
	public Direction nextMove(Game game) {
		return pcStrategy.nextMove(game, this);
	}
}
