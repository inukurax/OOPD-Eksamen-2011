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
        MainWindowFrame mainWindow = null;

        NewGameDialog gameDialog = new NewGameDialog(mainWindow, true);
        
        Configuration config = gameDialog.getConfig();
        
        Collection<Player> players = gameDialog.getPlayers();
        
        GameManager game = new GameManager(config, players);
        
        TronDisplayPanel tronDisplay = new TronDisplayPanel(config.getHeight(), 
        		config.getWidth(), config.getScaleFactor());
        
        GUIController guiController = new GUIController(game);
        mainWindow = new MainWindowFrame(tronDisplay);
        
        
        tronDisplay.init(game.getGame(), game.getPlayers());
        
        mainWindow.setHumanObserver(gameDialog.getHuman());
        game.addObserver(tronDisplay);
     
        guiController.run(); 
        
    }
}
