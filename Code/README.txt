===============================================================================
BlueJ Teacher's Assistant (TA) Extension
===============================================================================
===============================================================================



===============================================================================
Description

The BlueJ TA Extension allows BlueJ users to practice Java in the BlueJ IDE.

===============================================================================
Installing

 . Make sure that the JDK 1.7 or greater is installed.
 . Make sure that ANT is installed. If you don't have Ant then goto:
        http://ant.apache.org/ and click on "Binary Distributions"

===============================================================================
Directory Structure

Code
├───build.xml                       The build file compiles, installs, and
│                                    runs the extension.
├───local.properties.macOSX         Specifies build properties for a Mac.
├───local.properties.unix           Specifies build properties for a Linux.
├───local.properties.windows        Specifies build properties for a Windows.
└───src                             The source code of the project.
                                    Note: this is itself a BlueJ project.

                                    
===============================================================================
Manual build instructions:

Warning!
Please use the ant build file as it builds everything for you!
Manual building should be done only if the ant build file fails.

Note: Replace [and anything inside]
1. Create manifest.txt with following text inside: 
Main-Class: [Your startup file. No file extension]
2. Move manifest.txt to your extension's .class files
3. Open command prompt and navigate to your .class files
4. Type in the follow command prompt argument to build: 
jar cmf manifest.txt [Name of jar file].jar *.class
5. The Bluej extension .jar file should be in your current directory.

More information at: http://www.bluej.org/extensions/writingextensions.html

-------------------------------------------------------------------------------

Installing an extension into BlueJ: 

Drop the extension jar file into the extension folder for bluej to install.
Instructions on creating the jar file above.

For all users of this system in all projects.

<BLUEJ_HOME>/lib/extensions (Unix),
or <BLUEJ_HOME>\lib\extensions (Windows),
or <BLUEJ_HOME>/BlueJ.app/Contents/Resources/Java/extensions 
(Mac, Control-click BlueJ.app and choose Show Package Contents)

For a single user for all projects.

<USER_HOME>/.bluej/extensions (Unix), 
or <USER_HOME>\bluej\extensions (Windows), 
or <USER_HOME>/Library/Preferences/org.bluej/extensions (Mac)

More information at: http://www.bluej.org/extensions/extensions.html

===============================================================================
Authors
 
 - Josh Gillham
 - Wei Hang
 - Thomas Macari
 - Miguel Roman-Roman
 - Di Tran
 - Nathan WittFolder PATH listing for volume SYSTEM