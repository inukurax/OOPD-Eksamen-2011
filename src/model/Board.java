package model;

import java.util.ArrayList;

/**
 * A class for modeling the board of a Tron game.
 * The board is of size getWidth() x getHeight() and has some obstacles on it.
 * You can check if there's an obstacle at position x,y by calling isOccupied(x,y).
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @version 1
 */
public class Board
{
	
	private int width, height;
	private ArrayList<Position> occupiedPositions;
    /**
     * Constructs a new board with the specified width and height.
     * 
     * @param   width   the width of the board
     * @param   height  the height of the board
     */
    public Board(int width, int height)
    {
        this.height = height;
        this.width = width;
        // should add player start positions
        occupiedPositions = new ArrayList<Position>();
      
    }
    
    public int getWidth()
    {
      return this.width;
    }
    
    public int getHeight()
    {
       return this.height;
    }
    
    public void occupyField(Position position)
    {
    	occupiedPositions.add(position);
    }
    
    public boolean isOccupied(Position position)
    {
    	return occupiedPositions.contains(position);
    }
}
