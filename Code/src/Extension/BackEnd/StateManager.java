package Extension.BackEnd;

import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.io.File;
import org.junit.runner.Result;
import java.io.*;
import javax.swing.*;
import java.nio.file.Path;
import bluej.extensions.BlueJ;
import bluej.extensions.BProject;
import bluej.extensions.BPackage;
import bluej.extensions.BClass;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

import Extension.Main;
import Extension.GUI.*;

/**
 * The StateManager class controls the current state of the extension.
 * It holds all necessary information such as the GUI's and the currently chosen exercise.
 *
 * @author Miguel Roman-Roman
 * @author Wei Huang
 * @author Di Tran
 * @author Josh Gillham
 * @author Nathan Witt
 * @author Thomas Macari
 * @version April2015
 */
public class StateManager {
    private static BlueJ blueJ;
    
    private static String exercisesLocation;
    private static ArrayList<Exercise> exercises;
    private static Exercise currentExercise;
    
    private static TestResultsGUI testResultsGUI;
    private static DescriptionGUI descriptionGUI;
    private static ExerciseListGUI exerciseListGUI;
    
    private static File tempDir, extDir;
    
    /**
     * Private constructor
     */
    private StateManager()
    {
    }
    
    /**
     * Used to set up this State Manager and extension.
     * Creates proper folders in the BlueJ config directory.
     * Parses the exercises from known location.
     * 
     * @param bluej the BlueJ instance
     */
    public static void setup(BlueJ bluej)
    {
        blueJ = bluej;
        tempDir = new File(blueJ.getUserConfigDir(), "TA_Temp");
        FileUtil.deleteDir(tempDir);
        tempDir.mkdir();
        
        StateManager.parseExercises();
    }
    
    /**
     * Returns the BlueJ instance held 
     * 
     * @return  the BlueJ instance
     */
    public static BlueJ getBlueJ()
    {
        return blueJ;
    }
    
    /**
     * Returns a File for the location of the Temporary Directory created by this extension.
     * 
     * @return  the location of the Temporary Directory
     */
    public static File getTempDir()
    {
        return tempDir;
    }
    
    /**
     * Returns the current exercise chosen by the user.
     * 
     * @return  the currently selected exercise
     */
    public static Exercise getCurrentExercise()
    {
        return currentExercise;
    }
    
    /**
     * Sets the current exercise chosen by the user.
     * 
     * @param ex    the exercise
     */
    public static void setCurrentExercise(Exercise ex)
    {
        currentExercise = ex;
    }
    
    /**
     * Returns the String location of the exercises.
     * 
     * @return  the path of the exercises
     */
    public static String getExercisesLocation()
    {
        return exercisesLocation;
    }
    
    /**
     * Sets the location of the exercises.
     * 
     * @param location  the location of the exercises
     */
    public static void setExercisesLocation(String location)
    {
        exercisesLocation = location;
    }
    
    /**
     * Returns the held instance of the TestResultGUI
     * 
     * @return  the TestResultsGUI
     */
    public static TestResultsGUI getTestResultsGUI()
    {
        return testResultsGUI;
    }
    
    /**
     * Returns the held instance of the DescriptionGUI
     * 
     * @return  the DescriptionGUI
     */
    public static DescriptionGUI getDescriptionGUI()
    {
        return descriptionGUI;
    }
    
    /**
     * Returns the held instance of the ExerciseListGUI
     * 
     * @return the ExerciseListGUI
     */
    public static ExerciseListGUI getExerciseListGUI()
    {
        return exerciseListGUI;
    }
    
    /**
     * Sets the instance of TestResultsGUI
     * 
     * @param gui   the TestResultsGUI
     */
    public static void setTestResultsGUI(TestResultsGUI gui)
    {
        testResultsGUI = gui;
    }
    
    /**
     * Sets the intance of DescriptionGUI
     * 
     * @param gui   the DescriptionGUI
     */
    public static void setDescriptionGUI(DescriptionGUI gui)
    {
        descriptionGUI = gui;
    }
    
    /**
     * Sets the instance of ExerciseListGUI
     * 
     * @param gui   the ExerciseListGUI
     */
    public static void setExerciseListGUI(ExerciseListGUI gui)
    {
        exerciseListGUI = gui;
    }
    
    /**
     * Initializes the exercise list.
     * 
     * @return exercises  the list of exercises.
     */
    public static ArrayList<Exercise> getExerciseList() 
    {
        return exercises;
    }
    
    /**
     * Used to show an error message.
     * Displays a JOptionPane with given message.
     * 
     * @param msg   the message to display
     */
    public static void showErrorMessage(String msg) 
    {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Parses the exercises in the location specified and populates the exercise list
     */
    public static void parseExercises() 
    {
        exercises = new ArrayList<Exercise>();
        System.out.println( exercisesLocation );
        if (exercisesLocation == null || exercisesLocation.isEmpty())
        {
            String listDir = "/Extension/exercises/exerciseList.lst"; 
            Scanner in = new Scanner(Main.class.getResourceAsStream(listDir));
            XMLReader reader = new XMLReader();
            while (in.hasNextLine())
            {
                String ex = in.nextLine();
                System.out.println("ex: " + ex);
                exercises.add(reader.readExercise(Main.class.getResourceAsStream(ex)));
            }
        }else
        {
            File directory = new File(exercisesLocation);
            System.out.println(directory.toPath());
            File[] filesInDir = directory.listFiles();
            if(filesInDir != null){
                XMLReader reader = new XMLReader();
                for(int i = 0; i < filesInDir.length; i++) 
                {    
                    if(filesInDir[i].isFile() && !filesInDir[i].isHidden())
                    {
                        System.out.println("File: " + filesInDir[i].toPath().toString());
                        exercises.add(reader.readExercise(filesInDir[i].toPath().toString()));
                    }
                }
            }
        }
        System.out.println("Size: " + exercises.size());
    }
    
    /**
     * Creates the GUI showing the JUnit tests for the exercise.
     */
    public static void createTestResultsGUI()
    {
        if (testResultsGUI == null)
        {
            SwingUtilities.invokeLater(new Runnable() 
            {
                @Override
                public void run() 
                {
                    testResultsGUI = new TestResultsGUI();
                    testResultsGUI.packGUI();
                }
            });
        } else if(!testResultsGUI.isVisible()) 
        {
            testResultsGUI.setVisible(true);
        }
    }
    
    /**
     * Creates the GUI for the description of the exercise.
     */
    public static void createDescriptionGUI() 
    {
        if (descriptionGUI == null)
        {
            SwingUtilities.invokeLater(new Runnable() 
            {
                @Override
                public void run() 
                {
                    descriptionGUI = new DescriptionGUI();
                    descriptionGUI.packGUI();
                }
            });
        } else if(!descriptionGUI.isVisible()) 
        {
            descriptionGUI.setVisible(true);
        }
    }
    
    /**
     * Creates the GUI for picking an exercise.
     */
    public static void createExerciseListGUI() 
    {
        if (exerciseListGUI == null)
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run() {
                    exerciseListGUI = new ExerciseListGUI();
                    exerciseListGUI.packGUI();
                }
            });
        } else if(!exerciseListGUI.isVisible()) 
        {
            exerciseListGUI.setVisible(true);
        }
    }
    
    /**
     * Disposes the ExerciseListGUI
     */
    public static void disposeExerciseGUI()
    {
        if( exerciseListGUI != null) 
        {
            exerciseListGUI.setVisible(false);
            exerciseListGUI.dispose();
            exerciseListGUI = null;
        }
    }
    
    /**
     * Disposes the DescriptionGUI
     */
    public static void disposeDescriptionGUI() 
    {
        if( descriptionGUI != null )
        {
            descriptionGUI.setVisible(false);
            descriptionGUI.dispose();
            descriptionGUI = null;
        }
    }
    
    /**
     * Disposes the TestResultsGUI 
     */
    public static void disposeTestResultsGUI()
    {
        if( testResultsGUI != null )
        {
            testResultsGUI.setVisible(false);
            testResultsGUI.dispose();
            testResultsGUI = null;
        }
    }
}
