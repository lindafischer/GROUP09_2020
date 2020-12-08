# README

## Installation:

### 1. Download the newest version of JavaFX to your system according to this guide: https://openjfx.io/openjfx-docs/#install-java
	
### 2. Make sure your environment variable is called PATH_TO_FX and set to the path of lib folder in the javafx files you downloaded in the previous step.

### 3. For MacOS/Linux:

- Open your terminal, navigate to the project's folder and type in the following commands:

	- ```chmod +x run.sh```
	- ```./run```

###    For Windows:

- Either double click on the file run.bat or open your terminal, navigate into the project's folder and type in "run"

# Troubleshooting:

## If you encounter any issues regarding step 3, you need to run the app yourself by running the following commands:
```
  "javac --module-path %PATH_TO_FX% --add-modules javafx.controls Menu.java"
  "java --module-path %PATH_TO_FX% --add-modules javafx.controls Menu"
```
### Note that you need to substitute %PATH_TO_FX% with the path to the lib folder of your javafx installation if you encounter any issues setting the PATH-variable
