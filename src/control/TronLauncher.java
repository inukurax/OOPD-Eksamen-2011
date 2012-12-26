package control;

import java.io.FileNotFoundException;
import java.util.Collection;

import javax.swing.UIManager;

import model.GameManager;
import model.Player;
import model.config.Configuration;
import model.config.ParserException;

import view.MainWindowFrame;
import view.NewGameDialog;
import view.TronDisplayPanel;

/**
 * Creates the GUI elements, the controller and launches the game.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @version 1
 */
public class TronLauncher
{
    /**
     * Main entry point for the application.
     * @throws ParserException 
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException, ParserException
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception _) { } // Just ignore this.
        
        // To be implemented by the student
        NewGameDialog gameDialog = new NewGameDialog(null, true);
        Configuration config = gameDialog.getConfig();
        
        Collection<Player> players = gameDialog.getPlayers();
        
        GameManager game = new GameManager(config, players);
        
        TronDisplayPanel tronDisplay = new TronDisplayPanel(config.getHeight(), 
        		config.getWidth(), config.getScaleFactor());
        
        
        GUIController guiController = new GUIController(game);
        
        new MainWindowFrame(tronDisplay);
        game.addObserver(tronDisplay);

        tronDisplay.init(game.getGame(), players);
        tronDisplay.update(game, players);
       
        guiController.run();
        

        	
        	
    }
}
