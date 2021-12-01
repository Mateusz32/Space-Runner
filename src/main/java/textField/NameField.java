package textField;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NameField extends TextField {

    public final static String FONT_PATH = "src/main/resources/kenvector_future.ttf";

    public NameField() {
        setNameFont();
        setAlignment(Pos.CENTER);
    }

    private void setNameFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdena", 23));
        }
    }
}

