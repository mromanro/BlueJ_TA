package Extension.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import org.junit.runner.Result;
import javax.swing.JOptionPane;
import javafx.scene.shape.Circle;
import java.io.File;

import Extension.BackEnd.*;

import javafx.application.Platform;

/**
 * Used by the FXML document to control the test results GUI
 *
 * @author Miguel Roman-Roman
 * @author Wei Huang
 * @author Di Tran
 * @author Josh Gillham
 * @author Nathan Witt
 * @author Thomas Macari
 * @version April2015
 */
public class FXMLTestResultsDocumentController implements Initializable
{
    @FXML
    private Button runButton;
    @FXML
    private TextArea textArea;
    @FXML
    private Label titleLabel;
    
    /**
     * Called whenever a button or menu item is pressed, and calls a function
     * depending on the button/item pressed.
     *
     * @param event the calling event
     */
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        if(event.getSource().equals(runButton))
        {
            textArea.clear();
            runTest();
        }
    }
    
    /**
     * Gets the results from running the JUnit tests
     * then formats the failures and prints it to the text area.
     */
    public void runTest() 
    {
        if( StateManager.getCurrentExercise() != null)
        {
            new Thread()
            {
                public void run()
                {
                    final String resultText = StateManager.getCurrentExercise().execute();
                    Platform.runLater(new Runnable() 
                    {
                        public void run() 
                        {
                            textArea.setText(resultText);
                        }
                    });
                }
            }.start();
        }else
        {
            textArea.setText("Exercise not loaded");
        }
    }
    
    /**
     * Used to initialize this GUI
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        textArea.setEditable(false);
        if(StateManager.getCurrentExercise() != null)
        {
            titleLabel.setText(StateManager.getCurrentExercise().getTitle());
            runTest();
        }else
        {
            textArea.setText("Exercise not loaded");
        }
    }
}
