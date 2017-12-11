package uk.co.compendiumdev.javafortesters.gui.javafx.utils;

import javafx.beans.property.StringProperty;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.OutputTo;

public class JavaFxTextOutputter implements OutputTo {
    private final StringProperty textArea;

    public JavaFxTextOutputter(StringProperty textArea) {
        this.textArea = textArea;
    }

    @Override
    public void append(String text) {
        textArea.setValue(String.format("%s%n%s", textArea.getValue(), text ));
        //textArea.setText(String.format("%s%n%s", textArea.getText(), text ));
    }

    @Override
    public void output(String text) {
        textArea.setValue(String.format("%s%n", text ));
        //textArea.setText(String.format("%s%n", text ));
    }
}
