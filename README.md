This is a simple java program that is ran as a JAR via a batch file in order to pass both the path to the JavaFX jdk and neccessary modules to load as command line arguments.

The program walks through the file tree starting at the selected parent folder, and adds only files to an ArrayList. This list is then iterated through, searching each for the line "Build Number: " + <desired build number>. Once it is found the program then takes the absolute path of the file and displays it for the user via the GUI, allowing the user to copy and paste it into the windows file explorer.

It was designed as a way to decrease search times through the file system for a local company, and aims to assist their transition towards near-complete digital record keeping. 

Usage:

- Select the parent folder, or the folder that contains the folders of every company serviced.

- Enter the build number into the indicated field, and hit search.

- If the desired build number is found, the program gives you the absolute path to the text file containing the build number, which is stored alongside the desired CAD drawings for that specific job/build.
