package model;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class representing a position or position on the Tron board.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Lars Christian Thoudahl
 * @author Søren Dahlgaard
 * @version 1
 */
public class Position
{
	
	private int x, y;
	
	public Position(final int x, final int y)
    {

       this.x = x;
       this.y = y;
		//if (x < 0 || y < 0)
		//	throw new IllegalArgumentException();
    }
	
	/**
	 * Overwrites equals.
	 * @param other an Position
	 * @return true if x and y value for other is equal to the positions,
	 * otherwise false.
	 */
	@Override 
	public boolean equals(Object other) {
		if (other == null)
			return false;
		return this.getX() == ((Position) other).getX() 
				&& this.getY() == ((Position) other).getY();
	}
    // Assumes non null position //
    public Position getNeighbour(Direction direction)
    {
    	if (direction == null) {
    		return this;
    	}
		switch (direction) {
		case NORTH : return new Position(this.getX(), this.getY()-1);
		case SOUTH : return new Position(this.getX(), this.getY()+1);
		case EAST  : return new Position(this.getX()+1, this.getY());
		case WEST  : return new Position(this.getX()-1, this.getY());
		}
		return null;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
       return y;
    }
    
    /**
     * A collection of the positions neighbouring this position. Note that only the positions
     * to the west, east, north and south are neighbours. Positions diagonal from this
     * are not.
     * 
     * @return  A collection of the neighbouring positions
     */
    public Collection<Position> getNeighbours()
    {
        ArrayList<Position> neighbours = new ArrayList<Position>();
        for (Direction dir : Direction.values())
            neighbours.add(getNeighbour(dir));
        
        return neighbours;
    }
}
