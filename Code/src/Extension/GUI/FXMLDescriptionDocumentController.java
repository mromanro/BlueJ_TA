package Extension.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Window;

import Extension.BackEnd.*;

/**
 * Used by the FXML document to control the description GUI
 *
 * @author Miguel Roman-Roman
 * @author Wei Huang
 * @author Di Tran
 * @author Josh Gillham
 * @author Nathan Witt
 * @author Thomas Macari
 * @version April2015
 */
public class FXMLDescriptionDocumentController implements Initializable
{

    @FXML
    private Stage stage;
    @FXML
    private ListView<Exercise> list;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label examplesLabel;
    @FXML
    private Button okButton;
    private Exercise exercise;
    
     /**
     * Called whenever a button or menu item is pressed, and calls a function
     * depending on the button/item pressed.
     *
     * @param event the calling event
     */
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        if(event.getSource().equals(okButton))
        {
            StateManager.disposeDescriptionGUI();
        }
    }
    
    /**
     * Initializes this GUI with the proper data
     * 
     * @param url   not used
     * @param rb    not used
     */
    public void initialize(URL url, ResourceBundle rb) 
    {
        if(StateManager.getCurrentExercise() != null)
        {
            exercise = StateManager.getCurrentExercise();
            titleLabel.setText(exercise.getTitle());
            descriptionLabel.setText(exercise.getDescription());
            String examples = "";
            List<String> examp = exercise.getExample();
            
            for(int x=0; x<examp.size(); x++)
                examples += "   " + examp.get(x) + "\n";
                
            examplesLabel.setText(examples);
        }
    }
}
