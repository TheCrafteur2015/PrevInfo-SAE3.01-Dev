#!/bin/bash

FX_PATH="./lib/javafx-sdk-17.0.9/lib/:./lib/"

javac --module-path "$FX_PATH" --add-modules javafx.controls,javafx.fxml,org.controlsfx.controls -encoding utf8 "@compile.list" -d ./bin 

cd bin

FX_PATH="../lib/javafx-sdk-17.0.9/lib/:../lib/"

java --module-path "$FX_PATH" --add-modules javafx.controls,javafx.fxml,org.controlsfx.controls vue.App
