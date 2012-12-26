package view;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.ComputerPlayer;
import model.HumanPlayer;
import model.Player;
import model.config.Configuration;
import model.config.ParserException;
import model.config.TextParser;
import model.strategies.StrategyFactory;


/**
 * Sets up a new game configuration.
 * 
 * @author Christian Thoudahl
 * @version 1
 */
public class NewGameDialog extends JDialog implements ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Configuration config = null;
    private HumanPlayer human;
    private ArrayList<Player> players;
    public Color[] farve;
    private boolean humanPlayer = false;
	private JCheckBox checkButton;
	private JButton okButton;
	private JButton closeButton;
	private JLabel setupLabel;
	
	 
	public PlayerPanel player1, pc1, pc2, pc3;
    
    /**
     * Constructor for objects of class NewGameDialog
     * 
     * @param   owner   The owner of the dialog
     * @param   modal   Specifies whether dialog blocks user input to other top-level windows when shown.
     *                  Also blocks the current thread until the dialog is disposed.
     * @throws ParserException 
     * @throws FileNotFoundException 
     */
    public NewGameDialog(JFrame owner, boolean modal) throws FileNotFoundException, ParserException    {
        super(owner, modal);
        setConfig(); 
        if (config != null)
        	openDialog();

     }
    
    private void setConfig() throws FileNotFoundException, ParserException {
 	   JFileChooser chooser = new JFileChooser();
	   FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Tron Config-files", "tron");
	    chooser.setFileFilter(filter);
	    Component parent = null;
		int returnVal = chooser.showOpenDialog(parent);
	    	if(returnVal == JFileChooser.APPROVE_OPTION) {
	    		TextParser configPars = new TextParser(chooser.getSelectedFile(
	    				).getAbsolutePath());
	    		config = configPars.getConfiguration();
	    	}
	    else 
		       System.out.println("No config file selected!");
    }
    
    public void openDialog() {
        FlowLayout experimentLayout = new FlowLayout();
        setPreferredSize( new Dimension(300,280));  
		setTitle("Game Setup");
        setLayout(experimentLayout);
        checkButton = new JCheckBox("Human player");
        setupLabel = new JLabel("Game Setup ");
        add(setupLabel);
        add(checkButton);
        
        farve = TronColors.getColors(4);
        
    	player1 = new PlayerPanel(farve[0], "Player 1");
    	add(player1);
        pc1 = new PlayerPanel(farve[1], "Player 2");
        pc2 = new PlayerPanel(farve[2], "Player 3");
        pc3 = new PlayerPanel(farve[3], "Player 4");

        add(pc1);
        add(pc2);
        add(pc3);

        
        okButton = new JButton("Ok");
        closeButton = new JButton("Close");
        add(okButton);
        add(closeButton);
        
        checkButton.addActionListener(new ActionListener()
        {
			public void actionPerformed(ActionEvent e)
            {
            	System.out.println("" +checkButton.isSelected());
            	if (checkButton.isSelected()) {
            		player1.setComboBoxEnabled(false);
            		humanPlayer = true;
            	}
            	else {
            		player1.setComboBoxEnabled(true);
            		humanPlayer = false;
            	}
            }
        });
        
        closeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	setVisible(false);
            }
        });

        okButton.addActionListener(this);

        pack();
        setVisible(true);
    }
    
    /**
     * Returns the configuration loaded through this dialog
     * 
     * @return  The configuration
     */
    public Configuration getConfig()
    {
        return this.config;
    }
    
    /**
     * Returns a collection of the players created with this dialog
     * 
     * @retun   The collection of players
     */
    public Collection<Player> getPlayers()
    {
        return this.players;
    }
    
    /**
     * Returns the human player if one has been selected by the user. If the user wishes a pure AI game
     * this returns null
     * 
     * @return  The HumanPlayer object associated with this game or null.
     */
    public HumanPlayer getHuman()
    {
        return this.human;
    }
    
    public void actionPerformed(ActionEvent e)
    {
    		System.out.println("Clicked Ok");
        // To be implemented by the student
			players = new ArrayList<Player>();
			try {
			if (humanPlayer) {
				human = new HumanPlayer(player1.getColor(), player1.getName());
				players.add(human);

			}
			else if (player1.getStrategy() != null)
					players.add(new ComputerPlayer(player1.getColor(), player1.getName(),
							StrategyFactory.getStrategy(player1.getStrategy())));
					
			
			if (pc1.getStrategy() != null)
					players.add(new ComputerPlayer(pc1.getColor(), pc1.getName(),
							StrategyFactory.getStrategy(pc1.getStrategy())));
			if (pc2.getStrategy() != null)
				players.add(new ComputerPlayer(pc2.getColor(), pc2.getName(),
						StrategyFactory.getStrategy(pc2.getStrategy())));
			if (pc3.getStrategy() != null)
				players.add(new ComputerPlayer(pc3.getColor(), pc3.getName(),
						StrategyFactory.getStrategy(pc3.getStrategy())));
				} catch (InstantiationException | IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if (players.size() >= 2)
    			dispose();
			else
				System.err.println("Need two players, only got " + players.size());
    }
    
    
    /**
     * A panel for selecting a strategy for a player
     * 
     * @author Christian Thoudahl
     * @version 1
     */
    private class PlayerPanel extends JPanel
    {
        private JLabel    nameLabel;
        private String    name;
        private Color     color;
        private JComboBox strategies;
    
        /**
          * Constructor for objects of class PlayerPanel
          * 
          * @oaram  color   The color of the player
          * @param  name    The name of the player
          */
        public PlayerPanel(Color color, String name)
        {
             this.setLayout(new FlowLayout());
             this.name = name;
             
             // Retrieve the strategy names from the factory and put them in a combo box
             this.strategies = new JComboBox(StrategyFactory.getStrategyTypes().keySet().toArray());
             this.strategies.insertItemAt("", 0);
             this.strategies.setSelectedIndex(0);
        
             // Create a name label and set its color to this player's color
             this.nameLabel = new JLabel(name);
             this.add(nameLabel);
             this.add(new JLabel("Strategy: "));
             this.add(strategies);
             this.nameLabel.setForeground(color);
             
             this.color = color;
        }

        /**
          * Gets the name of the panel
          * 
          * @return the name of the panel
          */
        public String getName()
        {
            return this.name;
        }
          
        /**
         * Gets the color of the player
         * 
         * @return the color
         */
        public Color getColor()
        {
          return this.color;
        }
    
        /**
          * Gets the player strategy that corresponds to the chosen strategy in the panel.
          * Returns null if no strategy has been selected.
          * 
          * @return the corresponding player strategy
          */
        public StrategyFactory.Type getStrategy()
        {
            // Get an instance of the strategy and return it
            String item = (String)this.strategies.getSelectedItem();
            return StrategyFactory.getStrategyTypes().get(item);
        }
    
        /**
          * Enables/disables the combo box
          * 
          * @param enabled   whether or not the combo box should be enabled
          */
        public void setComboBoxEnabled(boolean enabled)
        {
             this.strategies.setEnabled(enabled);
        }
    }
}
