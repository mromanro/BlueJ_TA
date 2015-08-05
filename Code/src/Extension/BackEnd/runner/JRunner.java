/**
 * Note) This class cannot have a package header when it is compiled to work,
 * this is because this class is deployed to a default package to be run.
 *
 * This header was left in for anyone editing the file within BlueJ for BlueJ to
 * see the Java File. If left in this header will need to be commented out at
 * compile time either manually or within a build.
 */
//package Extension.BackEnd.runner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * A mobile class to be deployed into a users exercise project and called by the
 * extension to execute a JUnit test within the same project.
 *
 * @author Team BirdFeedr
 */
public class JRunner
{

    /**
     * A flag used to signal when its ok for the extension to remove
     * this class and the JUnit test class files from the exercise project.
     */
    public static boolean running = false;

    /**
     * Resets the static running flag for a future test to be run.
     */
    public static void resetRunning()
    {
        running = false;
    }

    /**
     * Executes the JUnit Test of a given JUnit class and returns the results in
     * the form of a string.
     *
     * @param testClass the name of the JUnit test class to execute.
     * @return the results of the test
     */
    public static String run(String testClass)
    {
        Class testC = null;
        try
        {
            testC = Class.forName(testClass);
        } catch (Exception e)
        {
            return e.getMessage();
        }

        //Set the running flag here to let the extension know that both 
        //the JRunner and JUnitTest are loaded into memory and its ok for 
        //the underlying class files to be removed from the exercise project.
        running = true;

        Result result = new JUnitCore().run(testC);
        if (result.getFailureCount() == 0)
        {
            return "All test Passed!";
        } else
        {
            String msg = "";
            for (Failure fail : result.getFailures())
            {
                msg += fail.getMessage() + "\n";
            }
            return msg;
        }
    }
}
