package Extension.BackEnd;

import java.awt.Frame;
import bluej.extensions.BClass;
import bluej.extensions.BField;
import bluej.extensions.BObject;

/**
 * A thread used to clean up execution artifacts within a users project and unlock
 * its ui once they are loaded into memory.
 *
 * @author Team BirdFeedr
 */
public class RemoveThread extends Thread
 {

    //the frame to unlock after removal of the classes
    private final Frame frame;

    //the classes to remove
    private final BClass testClass, runClass;

    /**
     * Constructor
     *
     * @param testClass the JUnit test class to remove.
     * @param runClass the JRunner class to remove.
     * @param frame the frame to unlock after removal.
     */
    RemoveThread(BClass testClass, BClass runClass, Frame frame) 
    {
        this.testClass = testClass;
        this.runClass = runClass;
        this.frame = frame;
    }

    /**
     * Remove the BClasses from the project and unlock the UI after the JRunners
     * running flag has been set to true.
     */
    @Override
    public void run() 
    {
        try 
        {
            BField runFlag = runClass.getField("running");
            BObject runRef = runClass.getConstructor(null).newInstance(null);
            while (!(Boolean) runFlag.getValue(runRef)) 
            {
                Thread.sleep(100);
            }
        } catch (Exception e) 
        {
            System.out.println("Err: " + e);
        }
        try
        {
            testClass.remove();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
        try
        {
            runClass.remove();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
        frame.getComponent(0).setVisible(true);
    }
}
