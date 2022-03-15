# P532 Assignment 3 Overview
## By: Saurabh Gulati, Christian Dummer, Raghunadham Gattu, Ethan Behar

### Assignment 3 requirements:

1. Familiar objectives:
    * Breakout game with clock - we all know this one very well.
    * Pause button functionality - we should be familiar with this.
    * Undo button functionality - we should be familiar with this.
    * Replay button functionality - we should be familiar with this.
2. New objectives:
    * Control Panel - This control panel holds the old and new buttons.
    * ChangeLayout Button - This button will change how the Control Panel and Buttons are arranged.
       * Two example layouts that where given are: Flow Layout and Border Layout.
    * Save Button - Saves the current state of the game out to a file.
    * Load Button - Loads a file that reconstructs the game state of the selected saved file.

# Instructions To Run
Built using the Eclipse IDE version 2021-16. Written in Java8 and JavaFX.

1. For Eclipse 2021-16 to run properly install JDK/JRE 11 or higher.
    * A download can be found here: https://www.oracle.com/java/technologies/javase-jre11-downloads.html
2. For OpenJDK8 and JavaFX you need Azul's OpenJDK build. OpenJDK8 does not have native JavaFX support and we couldn't find a JavaFX build that worked with OpenJDK8. All JavaFX builds are created with compilers greater than what is compatible with OpenJDK8.
    * Download and install/unzip Zulu OpenJDK8 from here: https://www.azul.com/downloads/?version=java-8-lts&package=jdk-fx
      * This build of OpenJDK8 does include JavaFX. (Praise the lord). 
      * #### MAKE SURE YOU USE THE FILTERS JAVA VERSION: Java 8 (LTS) AND JAVA PACKAGE: JDK FX 
3. In Eclipse we have to manually set up Zulu OpenJDK8. To do this go to Preferences->Java->Installed JREs. 
    1. Click the "Add" button. 
    2. Select "Standard VM." 
    3. Using the "JRE Home" field navigate to where you installed or unzipped Zulu OpenJDK8. 
    4. Enter an appropriate name for the JRE. "Zulu OpenJDK8'' is a solid name.
    5. Hit "Finish" to finish.
4. After adding the JRE, ensure it is the selected one if your list has more than one. Hit "Apply and Close" to finish.
   * This will default all projects to use this JRE. A user may not want this but since this is the requirement for this class it is a good idea.
5. Eclipse will warn us about compiler levels not being the same as the OpenJDK8 version. To remove this warning, go to Preferences->Java->Compilers. Change the "Compiler Compliance Level" to 1.8, this is the version needed for OpenJDK8. Although having a higher version shouldn't be an issue. We've tested the project with 16 set and it still compiled, ran, and functioned properly.
6. Next, you'll need to import this project into your workspace.   
   1. Import the project to your workspace via File->Import
   2. In the Import Window select "Existing Projects into Workspace"
   3. Navigate to wherever you unzipped or cloned this project and select Finish.
7. You now need to get gson 2.8.8. The jar can be downloaded from here https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.8/. 
   1. Download "gson-2.8.8.jar"
   2. Save it somewhere safe, where you keep the Zulu openJDK8 is a good spot.
   3. Right Click on the project and select "Properties."
   4. Select "Java Build Path" and select the "Libraries" tab.
   5. Hit "Add External Jar."
   6. Navigate to where you saved the gson-2.8.8.jar and select it.
   7. Hit "Apply and close."

There may be an issue if you imported the project before following these steps. That is the project might already be set to use whatever JDK was previous set up. To fix this issue follow the below steps.
   1. Right-Click the project in the Package Explore.
   2. Click "Properties"
   3. Select "Java Build Path"
   4. In the "Libraries" tab make sure the library we manually set up is the only one available and selected.
      1. You can use the "Remove" button to delete any that aren't needed.
      2. You can use the "Add Library" button to add the library we manually created. Select "JRE System Library" and select the one you created with the above instructions.

If JavaFX libraries are missing after set up you properly download the Zulu openJDK that does not include Java FX. Downloading the proper openJDK package and setting it up like we did in 3 will fix this issue.


