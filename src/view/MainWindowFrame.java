package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Direction;
import model.HumanPlayer;
import model.config.ParserException;


/**
 * Main window for the application.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @version 1
 */
public class MainWindowFrame extends JFrame
{
    private GameInput gameInput;

    
    /**
     * Creates a new main window with an image panel.
     * 
     * @param   tronDisplayPanel  the game display panel
     */
    public MainWindowFrame(TronDisplayPanel tronDisplayPanel)
    {
    	setTitle("Tron game");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	add(tronDisplayPanel);
    	gameInput = new GameInput();
    	addKeyListener(gameInput);
    	
    	pack();
    	setVisible(true);
    }
    
    public void setHumanObserver(HumanPlayer humanPlayer) {
    	if (humanPlayer != null)
    		gameInput.addObserver(humanPlayer);
    }
    /**
     * A class for listening to the keyboard for arrow key strokes.
     * Notifies observers with a direction corresponding to the key pressed.
     */
    private class GameInput extends Observable implements KeyListener
    {
        /**
         * Handles the key typed event.
         * 
         * Nothing happens.
         * 
         * @param e the key event.
         */
        @Override
        public void keyTyped(KeyEvent e)
        {
            //No intended behaviour.
        }
        
        /**
         * Handles the key released event.
         * 
         * Nothing happens.
         * 
         * @param e the key event.
         */
        @Override
        public void keyReleased(KeyEvent e)
        {
            //No intended behaviour.
        }
    
        /**
         * Handles the key pressed event.
         * 
         * When game keys are used, the observers are notified.
         * 
         * @param e the key event.
         */
        @Override
        public void keyPressed(KeyEvent e)
        {
            Direction direction = null;
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_DOWN:
                    direction = Direction.SOUTH;
                    break;
                case KeyEvent.VK_LEFT:
                    direction = Direction.WEST;
                    break;
                case KeyEvent.VK_UP:
                    direction = Direction.NORTH;
                    break;
                case KeyEvent.VK_RIGHT:
                    direction = Direction.EAST;
                    break;
            }
        
            if (direction != null)
            {
                setChanged();
                notifyObservers(direction);
            }
        }
    }
}
