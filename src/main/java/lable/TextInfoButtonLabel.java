package lable;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TextInfoButtonLabel extends Label {

    public final static String FONT_PATH = "src/main/resources/kenvector_future.ttf";

    public TextInfoButtonLabel(String text) {
        setPrefWidth(500);
        setPrefHeight(500);
        setText(text);
        setLableFont();
        setAlignment(Pos.TOP_LEFT);
        setLayoutX(40);
        setLayoutY(50);
    }

    private void setLableFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 18));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdena", 25));
        }
    }
}
