package lable;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RankingLabel extends Label {

    public final static String FONT_PATH = "src/main/resources/kenvector_future.ttf";

    public RankingLabel(String text) {
        setPrefWidth(380);
        setPrefHeight(49);
        setText(text);
        setWrapText(true);
        setLableFont();
        setAlignment(Pos.CENTER_LEFT);
    }

    private void setLableFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 18));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdena", 18));
        }
    }
}
