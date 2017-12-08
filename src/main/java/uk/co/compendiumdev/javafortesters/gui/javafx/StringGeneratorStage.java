package uk.co.compendiumdev.javafortesters.gui.javafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;


public class StringGeneratorStage extends Stage {

    private static StringGeneratorStage stringGeneratorSingletonStage=null;

    public static void singletonActivate() {

        if(stringGeneratorSingletonStage==null)
            stringGeneratorSingletonStage = new StringGeneratorStage(false);

        stringGeneratorSingletonStage.show();
        stringGeneratorSingletonStage.requestFocus();
    }

    public StringGeneratorStage(boolean hidden){



        //From ascii val to ascii val i.e. 0 - 128 , 0 - 255
        //From Char to Char
        //From unicode to unicode


        BorderPane root = new BorderPane();

        // http://www.ascii-code.com/
        // ASCII control characters (character code 0-31)
        // ASCII printable characters (character code 32-127)
        // The extended ASCII codes (character code 128-255)


        final String asciiCodeURL = "http://www.ascii-code.com";

        HBox asciiControlCharToCharInputs = new HBox();
        final Label firstCharLbl = new Label("First Value:");
        final TextField firstCharTxt = new TextField ();
        firstCharTxt.setText("1");
        final Label secondCharLbl = new Label("Second Value:");
        final TextField secondCharTxt = new TextField ();
        secondCharTxt.setText("255");

        HBox asciiControlCharToCharButtons = new HBox();
        Button createAsciiCharToChar = new Button();
        createAsciiCharToChar.setText("Create");
        createAsciiCharToChar.setTooltip(new Tooltip("Create a string from first char val to second char val"));

        Button createAsciiCharToCharClipboard = new Button();
        createAsciiCharToCharClipboard.setText("=>");
        createAsciiCharToCharClipboard.setTooltip(new Tooltip("Create direct to clipboard\nUseful for non display or high unicode values"));

        final Button copyToClipboard = new Button();
        copyToClipboard.setText("Copy");
        copyToClipboard.setTooltip(new Tooltip("Copy the text in the text area to the clipboard"));

        final Button asciihelp = new Button();
        asciihelp.setText("?");
        asciihelp.setTooltip(new Tooltip("see " + asciiCodeURL + "\n" +
                "ASCII control characters (character code 0-31)\n" +
                "ASCII printable characters (character code 32-127)\n" +
                "The extended ASCII codes (character code 128-255)\n" +
                "Unicode (above 255)"));

        asciiControlCharToCharInputs.getChildren().addAll(  firstCharLbl, firstCharTxt,
                                                      secondCharLbl, secondCharTxt);
        asciiControlCharToCharButtons.getChildren().addAll(  createAsciiCharToChar, createAsciiCharToCharClipboard, copyToClipboard,
                                                      asciihelp);
        asciiControlCharToCharInputs.setSpacing(10);
        asciiControlCharToCharButtons.setSpacing(10);

        final Label warning = new Label("Warning: unicode values can cause rendering issues in this control, and '0' will not render to clipboard");

        final Button clearTextArea = new Button();
        clearTextArea.setText("Clear");
        clearTextArea.setTooltip(new Tooltip("Clear the text area below"));

        VBox form = new VBox();
        form.getChildren().addAll(asciiControlCharToCharInputs, asciiControlCharToCharButtons, warning, clearTextArea);

        final TextArea textArea = new TextArea("");
        textArea.setWrapText(true);



        root.setTop(form);
        root.setCenter(textArea);

        Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
        this.setTitle("String Generator");
        this.setScene(scene);
        if(!hidden)
            this.show();

        createAsciiCharToCharClipboard.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            int from = Integer.parseInt(firstCharTxt.getText());
                            int to = Integer.parseInt(secondCharTxt.getText());
                            sendToClipboard(createString(from, to), copyToClipboard);
                        }
                        catch(NumberFormatException ex){
                            alertFirstOrSecondCharNotNumeric();
                        }catch(Exception ex){
                            alertException(ex);
                        }
                    }
                });
                                                                                                                          
        copyToClipboard.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {

                            sendToClipboard(textArea.getText(), copyToClipboard);

                        } catch (Exception ex) {
                            alertException(ex);
                        }

                    }
                });

        clearTextArea.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {

                            textArea.setText("");

                        } catch (Exception ex) {
                            alertException(ex);
                        }

                    }
                });

        asciihelp.setOnAction(new EventHandler<ActionEvent>() {
                               @Override public void handle(ActionEvent e) {
                                   try {
                                       Desktop.getDesktop().browse(new URI(asciiCodeURL));
                                   } catch (IOException e1) {
                                       alertException(e1);
                                   } catch (URISyntaxException e1) {
                                       alertException(e1);
                                   }
                               }
                           }
        );

        createAsciiCharToChar.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                try {
                    int from = Integer.parseInt(firstCharTxt.getText());
                    int to = Integer.parseInt(secondCharTxt.getText());
                    String created = createString(from, to);
                    textArea.setText(created);
                    copyToClipboard.setText("Copy");
                }
                catch(NumberFormatException ex){
                    alertFirstOrSecondCharNotNumeric();

                }catch(Exception ex){
                    alertException(ex);
                }

            }
        });


    }

    private String createString(int from, int to) {
        StringBuilder created = new StringBuilder();

        int step = 1;

        if(from > to){
            step = -1;
        }

        boolean generated=false;

        int nextChar = from;

        do{
            char theChar = (char)nextChar;
            created.append(theChar);
            nextChar+=step;

            if(step==-1){
                if(nextChar<to){
                    generated=true;
                }
            }else{
                if(nextChar>to){
                    generated=true;
                }
            }
        }while(!generated);

        return created.toString();
    }


    private void alertException(Throwable ex) {
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

    private void alertFirstOrSecondCharNotNumeric() {
        // http://code.makery.ch/blog/javafx-dialogs-official/
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("First and Second Values need to be numeric");
        alert.setHeaderText(null);
        alert.setContentText("First and Second Values need to be numeric, and between 0 and 255 for ascii");
        alert.showAndWait();
    }

    private void sendToClipboard(String contents, Button copyCounter) {
        copyCounter.setText("Copying");
        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.clear();
        ClipboardContent content = new ClipboardContent();
        content.putString(contents);
        clipboard.setContent(content);
        copyCounter.setText("Copied");
    }

}
