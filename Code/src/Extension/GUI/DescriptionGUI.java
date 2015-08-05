package Extension.GUI;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import Extension.BackEnd.*;

/**
 * The Graphical User Interface used to show the description of the exercises the user is working on.
 *
 * @author Miguel Roman-Roman
 * @author Wei Huang
 * @author Di Tran
 * @author Josh Gillham
 * @author Nathan Witt
 * @author Thomas Macari
 * @version April2015
 */
public class DescriptionGUI extends JFrame implements WindowListener
{
    private JFXPanel panel;
    
    /**
     * Creates the GUI
     */
    public DescriptionGUI() 
    {
        this.setTitle("BlueJ TA - Exercise Description");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(457, 471);
        this.addWindowListener(this);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLocation(200, 100);
        this.createGUI();
    }
    
    /**
     * Used to create a JavaFX panel
     */
    private void createGUI() 
    {
        
        panel = new JFXPanel();
        
        Platform.runLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                panel.setScene(createScene());
            }
        });
    }
    
    /**
     * Creates a JavaFX scene
     * 
     * @return the JavaFX scene
     */
    private Scene createScene()
    {
        Parent root = null;
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDescriptionDocument.fxml"));
            FXMLDescriptionDocumentController controller = new FXMLDescriptionDocumentController();
            loader.setController(controller);
            loader.setClassLoader(getClass().getClassLoader());
            root =  (Parent)loader.load();
            System.out.println(loader.getController().toString());
            System.out.println(loader.getLocation());
            return new Scene(root);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Scene(root);
    }
    
    /**
     * Packs the GUI and sets it visible
     */
    public void packGUI() 
    {
        this.getContentPane().add(panel);
        this.add(panel);
        this.setVisible(true);
    }
    
    public void windowActivated(WindowEvent event) 
    {
    }
    
    public void windowClosed(WindowEvent event) {
        StateManager.setDescriptionGUI(null);
    }

    public void windowClosing(WindowEvent event) 
    {
    }
    
    public void windowDeactivated(WindowEvent event) 
    {
    }
    
    public void windowOpened(WindowEvent event) 
    {
    }
    
    public void windowDeiconified(WindowEvent event) 
    {
    }
    
    public void windowIconified(WindowEvent event) 
    { 
    }
}
