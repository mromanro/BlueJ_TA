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
 * Used by the FXML document to control the ExerciseList GUI.
 *
 * @author Miguel Roman-Roman
 * @author Wei Huang
 * @author Di Tran
 * @author Josh Gillham
 * @author Nathan Witt
 * @author Thomas Macari
 * @version April2015
 */
public class FXMLExerciseDocumentController implements Initializable 
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
    private Button submitButton;
    
    private Exercise exerciseSelected;

    /**
     * Called whenever a button or menu item is pressed, and calls a function
     * depending on the button/item pressed.
     *
     * @param event the calling event
     */
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        if(event.getSource().equals(submitButton))
        {
            StateManager.setCurrentExercise(exerciseSelected);
            StateManager.getExerciseListGUI().setVisible(false);
            StateManager.disposeDescriptionGUI();
            StateManager.disposeTestResultsGUI();
            new Thread()
            {
                public void run()
                {
                    StateManager.getCurrentExercise().launch();
                }
            }.start(); 
        }
    }

    /**
     * Used to initialize the GUI
     *
     * @param url unused
     * @param rb unused
     */
    @SuppressWarnings("restriction")
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ObservableList<Exercise> items = FXCollections.observableArrayList();
        items.setAll(StateManager.getExerciseList());
        list.setItems(items);   
        list.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Exercise>() 
            {
                public void changed(ObservableValue<? extends Exercise> ov, Exercise old_val, 
                                Exercise new_val) 
                {
                    exerciseSelected = new_val;
                    titleLabel.setText(new_val.getTitle());
                    descriptionLabel.setText(new_val.getDescription());
                    String examples = "";
                    List<String> examp = new_val.getExample();
                    for(int x=0; x<examp.size(); x++)
                        examples += "   " + examp.get(x) + "\n";
                    examplesLabel.setText(examples);
                }    
            });
      
    }  
}
