package model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;

import model.config.Configuration;
import model.config.LineSegment;

/**
 * A class for managing a game of Tron.
 * 
 * @author  Søren Dahlgaard
 * @author  Daniel Hessellund Egeberg
 * @version 1
 */
public class GameManager extends Observable
{
    private Board board;
    private Collection<Player> players;
    private Game  game;
    
    /**
     * Constructs a new game manager from a configuration and a collection of players
     * 
     * A new GameManager object creates a Board object for the game and assigns the
     * players to their respective start locations.
     * 
     * @param   config  The configuration of the new game
     * @param   players The players that will be competing in the game
     */
    public GameManager(Configuration config, Collection<Player> players)
    {        
        this.players = players;
        this.createBoard(config);
        this.game = new Game(board, players);
    }
    
    public void takeTurn()
    {
    	for (Player player : this.players) {
    		if (player.isAlive()) {
        		board.occupyField(player.getPosition());
    			Direction currentMove = player.getDirection();
    			Direction nextMove = player.nextMove(game);
				player.performMove(nextMove);

    			if (nextMove == null || currentMove == null) {
    				System.out.println("wow null");
    				return;
    			}
				if (currentMove.getOpposite() == (nextMove)) {
					player.performMove(player.getDirection());
				}
				else
					player.performMove(nextMove);
    		}
    		
    	}
    }
    
    /**
     * Retrieves the winner of the game or null if the game was a tie.
     * 
     * @return The winner or null in case of a tie
     */
    public Player getWinner()
    {
        for (Player player : this.players)
            if (player.isAlive())
                return player;
                
        return null;
    }
    
    /**
     * Retrieves the immutable game object representing the status of the game.
     * 
     * @return The game object
     */
    public Game getGame()
    {
        return this.game;
    }
    
    /**
     * Returns the player list
     * 
     * @return the player list
     */
    public Collection<Player> getPlayers()
    {
        return this.players;
    }
    
    private void createBoard(Configuration config)
    {	
    	board = new Board(config.getWidth(), config.getHeight());
    	//setup players starting position.
    	Iterator<Position> pos = config.getStartPositions().iterator();
    	for (Player player : players) {
    		if (pos.hasNext())
    			player.setPosition(pos.next());
    	}
    	
    	//setup borders of board
    	int width = config.getWidth() - 1;
    	int height = config.getHeight() -1;
    	LineSegment topBorder = new LineSegment(0,0,width,0);
    	LineSegment buttomBorder =  new LineSegment(0, height, width, height);
    	LineSegment leftBorder =  new LineSegment(0, 0, 0, height);
    	LineSegment rightBorder =  new LineSegment(width, 0, width, height);
    	
    	Collection<Position> borderList = topBorder.getPositions();
    	borderList.addAll(buttomBorder.getPositions());
    	borderList.addAll(leftBorder.getPositions());
    	borderList.addAll(rightBorder.getPositions());

    	for (LineSegment segment : config.getLineSegments()) {
    		borderList.addAll(segment.getPositions());
    	}
    	
    	for (Position pos1 : borderList) {
    		board.occupyField(pos1);
    	}
    	
    }
}
