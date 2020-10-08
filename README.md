# GROUP09_2020
## How to use the normal version:
  1. Extract the files in the .zip archive to a folder of your choice and navigate into it with your terminal
  2. Compile ReadGraph.java (make sure that all the files are in the same directory!) with the command 'javac ReadGraph.java'
  3. Run ReadGraph.java using the command 'java ReadGraph <path to graph file>'
Have fun!

A few things about the program:

You can choose your own time limit for brute forcing and backtracking (in seconds). The program will prompt
you for input.

The triangle detection has a hard coded time limit of 20 seconds. If you want to change this, you can do so by changing it in the ReadGraph.java file
at line 150 (in the line above there is a comment saying you can change this value).

If the program detects that a graph is complete it will ask whether you want to continue with the other algorithms nevertheless. Simply tpye in 'y' or 'n'
when prompted.

## How to use the AnalyticalTool

Follow all steps as for the normal version, except you compile and run 'AnalyticalTools.java' instead.

About this tool:
This tool was created to evaluate the algorithms' efficiency. It will prompt for a time limit and a number of runs and will run the algorithms that often.
After the runs it will take the average of the time needed for every single run and will display it for every algorithm alongside the result respectively.

Note: The custom_graphs directory contains graphs we made ourselves to test our program.
