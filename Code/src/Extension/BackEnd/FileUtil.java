package Extension.BackEnd;

import bluej.extensions.*;
import java.io.*;

/**
 * Utility methods for dealing with files and BlueJ projects.
 *
 * @author Miguel Roman-Roman
 * @author Wei Huang
 * @author Di Tran
 * @author Josh Gillham
 * @author Nathan Witt
 * @author Thomas Macari
 * @version April2015
 */
public abstract class FileUtil 
{
    /**
     * Saves the given contents into the given file specified by path.
     *
     * @param path the file to save
     * @param contents the contents to save into the file
     * @return if the save was successful.
     */
    public static boolean save(File path, String contents) 
    {
        String[] lines = contents.split(System.lineSeparator());
        try {
            PrintWriter out = new PrintWriter(path);
            for (String line : lines)
            {
                out.println(line);
            }
            out.close();
            return true;
        } catch (Exception e) 
        {
            return false;
        }
    }

    /**
     * Copies the contents of the source file into the destination file.
     *
     * @param source the source file
     * @param dest the destination file
     * @references
     * http://examples.javacodegeeks.com/core-java/io/file/4-ways-to-copy-file-in-java/
     */
    public static void copy(File source, File dest) 
    {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            transferData(input, output);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    /**
     * Saves the given resource within this jar to the a file in the directory
     * specified.
     *
     * @param resLoc the directory within the jar to look for the file, relative
     * to this FileUtil class.
     * @param resName the name of the file to extract. This parameter will also
     * act as the name of the extracted file.
     * @param destDir the directory to save the file to.
     */
    public static void extractResource(String resLoc, String resName, File destDir) 
    {
        try {
            InputStream input = FileUtil.class.getResourceAsStream(resLoc + resName);
            OutputStream output = new FileOutputStream(new File(destDir, resName));
            transferData(input, output);
        } catch (Exception e) 
        {
            System.err.println("Error: " + e);
        }
    }

    /**
     * Transfers the data from the given InputStream to the given OutputStream
     *
     * @param input the InputStream to retrieve data from.
     * @param output the OutputStream to send the data to.
     */
    private static void transferData(InputStream input, OutputStream output) throws IOException
    {
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buf)) > 0) 
        {
            output.write(buf, 0, bytesRead);
        }
        input.close();
        output.close();
    }

    /**
     * Deletes the directory and all the contents within the directory.
     *
     * @param path the directory to delete.
     * @return if the directory was deleted successfully.
     * @references http://www.rgagnon.com/javadetails/java-0483.html
     */
    public static boolean deleteDir(File path)
    {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) 
            {
                if (files[i].isDirectory()) 
                {
                    deleteDir(files[i]);
                } else 
                {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    /**
     * Closes the any open bluej projects that have the given name.
     *
     * @param bluej the running bluej program.
     * @param name the name of the project to close.
     */
    public static void closeProj(BlueJ bluej, String name)
    {
        for (BProject proj : bluej.getOpenProjects())
        {
            try
            {
                if (proj.getName().equals(name))
                {
                    proj.close();
                }
            } catch (Exception e)
            {
                System.out.println("Error: " + e);
            }
        }
    }
}
