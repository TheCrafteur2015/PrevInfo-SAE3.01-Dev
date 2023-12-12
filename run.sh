#!/bin/bash

FX_PATH="./lib/javafx-sdk-17.0.9/lib/"

javac --module-path "$FX_PATH" --add-modules javafx.controls,javafx.fxml -encoding utf8 "@compile.list" -d ./bin 

cd bin

FX_PATH="../lib/javafx-sdk-17.0.9/lib/"

java --module-path "$FX_PATH" --add-modules javafx.controls,javafx.fxml vue.App
