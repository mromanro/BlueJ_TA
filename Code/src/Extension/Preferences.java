package Extension;

import bluej.extensions.*;
import bluej.extensions.event.*;
import bluej.extensions.editor.*;

import java.net.URL;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.GroupLayout.*;
import java.awt.FlowLayout;
import java.io.File;

import Extension.BackEnd.*;

/**
 * This class is used to generate a Panel within BlueJ Preferences->Extension
 * 
 * @author Miguel Roman-Roman
 * @author Wei Huang
 * @author Di Tran
 * @author Josh Gillham
 * @author Nathan Witt
 * @author Thomas Macari
 * @version April2015
 */
public class Preferences implements PreferenceGenerator
{
    private JPanel myPanel, localPanel, uriPanel;
    private JLabel localDir, uriDir;
    private JTextField uriTextfield, localTextfield;
    private JButton localButton, uriButton;
    private BlueJ bluej;
    public static final String PROFILE_LABEL="Exercise-Location-BlueJTA";

    /**
     * Creates the Panel within BlueJ's Preferences
     * 
     * @param bluej the BlueJ instance
     */
    public Preferences(BlueJ bluej)
    {
        this.bluej = bluej;
        myPanel = new JPanel();
        localPanel = new JPanel();
        uriPanel = new JPanel();
        
        localDir = new JLabel("Local Exercise Directory");
        uriDir = new JLabel("URI Exercise Location");
        uriTextfield = new JTextField (40);
        localTextfield =new JTextField (40);

        localButton = new JButton("Load");
        localButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String location = openFileChooser();
                localTextfield.setText(location);
                
                StateManager.setExercisesLocation(location);
                StateManager.parseExercises();
                StateManager.setExerciseListGUI(null);
            }
        });
        
        uriButton = new JButton("Connect");
        uriButton.addActionListener(new ActionListener() 
        {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               //to be implemented
           }
        });
        
        BoxLayout layout = new BoxLayout(myPanel, BoxLayout.Y_AXIS);
        myPanel.setLayout(layout);
        
        localPanel.add (new JLabel ("Local Exercise Directory"));
        localPanel.add(localTextfield);
        localPanel.add(localButton);
        
        myPanel.add(localPanel);
        
        uriPanel.add (new JLabel ("URI Exercise Location"));
        uriPanel.add (uriTextfield);
        uriPanel.add(uriButton);
        
        myPanel.add(uriPanel);
       
        
        uriTextfield.setText("Currently not implemented");
        uriTextfield.setEditable(false);
       
        loadValues();
    }
    
    /**
     * Opens a JFileChooser for the user to pick the location of the exercises
     * 
     * @return  the path of the exercises specified by the user.
     */
    private String openFileChooser() 
    {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setDialogTitle("Choose Exercise Location");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
         
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        { 
            return fc.getSelectedFile().getAbsolutePath();
        } else 
        {
            return "";
        }
    }

    /**
     * Returns the JPanel created by this object
     * 
     * @return  the populated JPanel
     */
    @Override
    public JPanel getPanel () 
    { 
        return myPanel; 
    }

    /**
     * Used to save the path given by the user into the BlueJ properties file
     */
    @Override
    public void saveValues ()
    {
        bluej.setExtensionPropertyString(PROFILE_LABEL, localTextfield.getText());
    }

    /**
     * Used to load the previously saved path given by the user from the 
     * BlueJ properties file
     */
    @Override
    public void loadValues ()
    {
        String exLocation = bluej.getExtensionPropertyString(PROFILE_LABEL, "");
        localTextfield.setText(exLocation);
        if(exLocation != null && !exLocation.isEmpty()) 
        {
            StateManager.setExercisesLocation(exLocation);
            StateManager.parseExercises();
        }
    }
}
