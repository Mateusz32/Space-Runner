package lable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EnterNamePlayerLabel extends Label {

    private final static String FONT_PATH = "src/main/resources/kenvector_future.ttf";

    public EnterNamePlayerLabel(String text) {
        prefWidth(600);
        prefHeight(400);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("file:src/main/resources/yellow_panel.png", 430, 330, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER);
        setPadding(new Insets(100, 40, 200, 40));
        setLabelFont();
        setText(text);
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 30));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 15));
        }
    }
}
