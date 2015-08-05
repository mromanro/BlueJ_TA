package Extension.BackEnd;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;

/**
 * XMLReader class is used to read in XML files and convert them into Exercise objects.
 * This class assumes the XML file passed in is a BlueJ TA exercise.
 * Uses JAXB 
 *
 * @author Miguel Roman-Roman
 * @author Wei Huang
 * @author Di Tran
 * @author Josh Gillham
 * @author Nathan Witt
 * @author Thomas Macari
 * @version April2015
 */
public class XMLReader
{
    private JAXBContext context;
    private Unmarshaller um;
    private Exercise ex;
    
    /**
     * Constructor
     */
    public XMLReader()
    { 
    }
    
    /**
     * Reads an XML file that has been converted into an InputStream
     * 
     * @param is    the XML file as InputStream
     * @return  the Exercise object created from the XML file.
     */
    public Exercise readExercise(InputStream is)
    {
        ex = null;
        try 
        {
            context=JAXBContext.newInstance(Exercise.class);
            um=context.createUnmarshaller();
            ex=(Exercise) um.unmarshal(new InputStreamReader( is ) );
        } catch (JAXBException ex)
        {
            ex.printStackTrace();
        }
        return ex;
    }
    
    /**
     * Reads in an XML file from the given path
     * 
     * @param filePath  the location of the exercise
     * @return  the Exercise object created from the given XML file.
     */
    public Exercise readExercise(String filePath)  {
       try 
       {
           return readExercise( new FileInputStream( filePath ) );
       } catch ( Exception  e ) 
       {
           System.out.println("Error: " + e);
           e.printStackTrace();
           return null;
       }
    }
}
