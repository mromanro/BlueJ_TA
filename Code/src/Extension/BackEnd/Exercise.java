package Extension.BackEnd;

import bluej.extensions.*;
import java.awt.Frame;
import java.util.List;
import javax.xml.bind.annotation.*;

import java.io.*;

/**
 * An Exercise object holds all the needed information related to the exercise.
 *
 * @author Miguel Roman-Roman
 * @author Wei Huang
 * @author Di Tran
 * @author Josh Gillham
 * @author Nathan Witt
 * @author Thomas Macari
 * @version April2015
 */
@XmlRootElement
public class Exercise 
{

    //the title of the exercise and project when laucnched
    private String title;

    //the description of the exercise depicting what is required of the user and what will be tested
    private String description;

    //a hint that could help the user if they are stuck
    private String hint;

    //a sample solution to this exercise
    private String sampleAnswer;

    //the class name of the java file the user starts with
    private String javaName;

    //the contents of the java file the user starts with
    private String javaCode;

    //the class name of the junit java file that will be used to run the test
    private String junitName;

    //the contents of the junit java file that will be used to run the test
    private String junitCode;

    //a set of examples 
    private List<String> example;

    //the JUnit test file this exercise will use to execute test with.
    private File testFile;

    //the BlueJ package this exercise is associated with.
    private BPackage exercisePkg;

    /**
     * Constructor
     */
    private Exercise() 
    {
    }

    /**
     * Returns the name of the Java file for this exercise.
     * 
     * @return  the name of the Java file
     */
    @XmlElement
    public String getJavaName() 
    {
        return javaName;
    }

    /**
     * Sets the Java file name for this exercise.
     * 
     * @param   the name of the Java file
     */
    public void setJavaName(String javaName) 
    {
        this.javaName = javaName;
    }

    /**
     * Returns the Java code for the exercise file of this exercise.
     * 
     * @return  the Java code
     */
    @XmlElement
    public String getJavaCode() 
    {
        return javaCode;
    }

    /**
     * Sets the Java code of the exercise file of this exercise.
     * 
     * @param javaCode  the Java code
     */
    public void setJavaCode(String javaCode)
    {
        this.javaCode = javaCode;
    }

    /**
     * Returns the name of the JUnit Java file for this exercise.
     * 
     * @return  the name of the JUnit Java file
     */
    @XmlElement
    public String getJunitName() 
    {
        return junitName;
    }

    /**
     * Sets the name of the JUnit Java file associated with this exercise.
     * 
     * @param   the name of the JUnit Java file
     */
    public void setJunitName(String junitName) 
    {
        this.junitName = junitName;
    }

    /**
     * Returns the Java code of the JUnit file associated with this exercise.
     * 
     * @return  the Java code for the JUnit file
     */
    @XmlElement
    public String getJunitCode() 
    {
        return junitCode;
    }

    /**
     * Sets the Java code of the JUnit file associated with this exercise.
     * 
     * @param junitCode the Java code for the JUnit file
     */
    public void setJunitCode(String junitCode) 
    {
        this.junitCode = junitCode;
    }

    /**
     * Sets the list of examples for this exercise.
     * 
     * @param exampleList   the List of examples
     */
    public void setExampleList(List<String> exampleList)
    {
        this.example = exampleList;
    }

    /**
     * Used to access the title of this exercise.
     * 
     * @return  the title of this exercise
     */
    @XmlElement
    public String getTitle()
    {
        return title;
    }

    /**
     * Used to access the description of this exercise.
     * 
     * @return  the description of this exercise
     */
    @XmlElement
    public String getDescription()
    {
        return description;
    }

    /**
     * Used to access the hint of this exercise.
     * 
     * @return  the hint of this exercise
     */
    @XmlElement
    public String getHint() 
    {
        return hint;
    }

    /**
     * Used to access the sample answer of this exercise.
     * 
     * @return  the sample answer of this exercise
     */
    @XmlElement
    public String getSampleAnswer() 
    {
        return sampleAnswer;
    }

    /**
     * Used to set the title of this exercise.
     * 
     * @param title the title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Used to set the description of this exercise.
     * 
     * @param description   the description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Used to set the hint of this exercise.
     * 
     * @param hint  the hint
     */
    public void setHint(String hint) 
    {
        this.hint = hint;
    }

    /**
     * Used to set the sample answer of this exercise.
     * 
     * @param sampleAnswer  the sample answer
     */
    public void setSampleAnswer(String sampleAnswer) 
    {
        this.sampleAnswer = sampleAnswer;
    }

    /**
     * Used to set the example list of this exercise.
     * 
     * @param example   the example List
     */
    public void setExample(List<String> example) 
    {
        this.example = example;
    }

    /**
     * Used to access the examples list of this exercise.
     * 
     * @return  the List of examples
     */
    @XmlElementWrapper(name = "examples")
    @XmlElement(name = "example")
    public List<String> getExample() 
    {
        return example;
    }

    /**
     * Launches this exercise by setting up the JUnit test and user project.
     */
    public void launch() 
    {
        BlueJ bluej = StateManager.getBlueJ();

        //close any open projects with this exercise name
        FileUtil.closeProj(bluej, title);

        //sets up a temp directory and project for this exercise
        File exerciseTempDir = new File(StateManager.getTempDir(), title + "_Files");
        FileUtil.deleteDir(exerciseTempDir);
        exerciseTempDir.mkdir();
        File projDir = new File(exerciseTempDir, title);
        BProject bproj = bluej.newProject(projDir);

        Frame frame = null;
        try 
        {
            BPackage bpkg = bproj.getPackage("");

            //locks the ui from repainting, to hide the junit class while it is in the UI view.
            frame = bpkg.getFrame();
            frame.getComponent(0).setVisible(false);

            //saves the users starting java files and the JUnit test files to the project
            FileUtil.save(new File(projDir, javaName + ".java"), javaCode);
            FileUtil.save(new File(projDir, junitName + ".java"), junitCode);

            //create new BClasses from the files
            bpkg.newClass(javaName);
            BClass bTestClass = bpkg.newClass(junitName);

            //compile the files and wait till compilation finishes
            bpkg.compileAll(true);

            //copies the JUnit test class file out of the users project and remembers the file for execution later
            testFile = new File(exerciseTempDir, junitName + ".class");
            FileUtil.copy(bTestClass.getClassFile(), testFile);

            //remembers this package to be used later in the execution of the junit test
            exercisePkg = bpkg;

            //removes the JUnit test file from the project
            bTestClass.remove();
        } catch (Exception ex) {
            System.err.println("Error: " + ex);
        }
        if (frame != null) 
        {
            //unlocks the ui to be repainted again
            frame.getComponent(0).setVisible(true);
            frame.toFront();
        }
    }

    /**
     * Runs this exercises JUnit test with the code within the users exercise
     * project. This will do this using BlueJ's user VM feature.
     *
     * @return the results of this exercise.
     */
    public String execute()
    {
        try 
        {
            //save the needed files into the users project
            File userProjDir = exercisePkg.getDir();
            File projTestFile = new File(userProjDir, testFile.getName());
            FileUtil.copy(testFile, projTestFile);
            FileUtil.extractResource("runner/", "JRunner.class", userProjDir);

            //locks the ui from repainting, to hide the added classes while they are in the UI view.
            Frame frame = exercisePkg.getFrame();
            frame.getComponent(0).setVisible(false);

            //reload package to allow the added classes to be obtained
            exercisePkg.reload();
            BClass testClass = exercisePkg.getBClass(junitName);
            BClass runClass = exercisePkg.getBClass("JRunner");

            //reset the static runner flag in the JRunner class, so the last test dosen't interfear with this one.
            runClass.getMethod("resetRunning", null).invoke(null, null);

            //get the method to execute
            BMethod runMethod = runClass.getMethod("run", new Class<?>[]{String.class});

            //start up a remove thread that will remove the added classes and unlock the ui when the test is underway.
            new RemoveThread(testClass, runClass, frame).start();

            //execute the junit test
            Object results = runMethod.invoke(null, new Object[]{junitName});

            return results.toString();
        } catch (InvocationErrorException e) 
        {
            return "Test Canceled by user.";
        } catch (Exception e) 
        {
            e.printStackTrace();
            return e.toString();
        }
    }

    /**
     * Prints the various data that composes this exercise
     */
    public void printExercise() 
    {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Hint: " + hint);
        System.out.println("Sample Answer: " + sampleAnswer);
        for (int x = 0; x < example.size(); x++) {
            System.out.println("Example " + x + ": " + example.get(x));
        }
        System.out.println("Java Name: " + javaName);
        System.out.println("Java Code: " + javaCode);
        System.out.println("Junit Name: " + junitName);
        System.out.println("Junit Code: " + junitCode);
    }

    /**
     * Used primarily to populate the Observable list in the Exercise List GUI with the titles of the
     * exercises
     * 
     * @return  this exercise's title
     */
    @Override
    public String toString()
    {
        return this.title;
    }
}
