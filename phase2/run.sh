#!/bin/bash

javac --module-path $PATH_TO_FX --add-modules javafx.controls *.java
java --module-path $PATH_TO_FX --add-modules javafx.controls Menu
rm *.class