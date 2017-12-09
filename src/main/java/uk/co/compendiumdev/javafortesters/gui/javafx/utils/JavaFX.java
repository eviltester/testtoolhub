package uk.co.compendiumdev.javafortesters.gui.javafx.utils;

import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * An unsorted list of wrapper methods around JavaFX functionality to clean up other classes
 * If this gets too big then refactor into separate classes
 */
public class JavaFX {

    public static Button button(String text, String tooltip) {
        Button abutton = new Button();
        abutton.setText(text);
        abutton.setTooltip(new Tooltip(tooltip));
        return abutton;
    }

    public static TextField textField(String text, String tooltip) {
        TextField aTextField = new TextField ();
        aTextField.setText(text);
        aTextField.setTooltip(new Tooltip(tooltip));
        return aTextField;
    }

    public static TextField textField(String text, String tooltip, int maxWidth) {
        TextField aTextField = textField(text, tooltip);
        aTextField.setMaxWidth(maxWidth);
        return aTextField;
    }

    public static void alertErrorDialogWithException(Throwable ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static void showSimpleErrorAlert(String title, String message) {
        // http://code.makery.ch/blog/javafx-dialogs-official/
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    public static void sendTextToClipboard(String textToSend) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.clear();
        ClipboardContent content = new ClipboardContent();
        content.putString(textToSend);
        clipboard.setContent(content);
    }
}
