package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;

import java.awt.*;
import java.net.URI;


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

        final TextField firstCharTxt = JavaFX.textField("1", "Starting Ascii Code Value");

        final Label secondCharLbl = new Label("Second Value:");
        final TextField secondCharTxt = JavaFX.textField("255", "Final Ascii Code Value");

        HBox asciiControlCharToCharButtons = new HBox();
        Button createAsciiCharToChar = JavaFX.button("Create", "Create a string from first char val to second char val");

        Button createAsciiCharToCharClipboard = JavaFX.button("=>", "Create direct to clipboard\nUseful for non display or high unicode values");

        final Button copyToClipboard = JavaFX.button("Copy","Copy the text in the text area to the clipboard");

        final Button asciihelp = JavaFX.button("?","see " + asciiCodeURL + "\n" +
                "ASCII control characters (character code 0-31)\n" +
                "ASCII printable characters (character code 32-127)\n" +
                "The extended ASCII codes (character code 128-255)\n" +
                "Unicode (above 255)");

        asciiControlCharToCharInputs.getChildren().addAll(  firstCharLbl, firstCharTxt,
                                                      secondCharLbl, secondCharTxt);
        asciiControlCharToCharButtons.getChildren().addAll(  createAsciiCharToChar, createAsciiCharToCharClipboard, copyToClipboard,
                                                      asciihelp);
        asciiControlCharToCharInputs.setSpacing(10);
        asciiControlCharToCharButtons.setSpacing(10);

        final Label warning = new Label("Warning: unicode values can cause rendering issues in this control, and '0' will not render to clipboard");

        final Button clearTextArea = JavaFX.button("Clear", "Clear the text area below");

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
                            JavaFX.alertErrorDialogWithException(ex);
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
                            JavaFX.alertErrorDialogWithException(ex);
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
                            JavaFX.alertErrorDialogWithException(ex);
                        }

                    }
                });

        asciihelp.setOnAction(new EventHandler<ActionEvent>() {
                               @Override public void handle(ActionEvent e) {
                                   try {
                                       Desktop.getDesktop().browse(new URI(asciiCodeURL));
                                   } catch (Exception e1) {
                                       JavaFX.alertErrorDialogWithException(e1);
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
                    JavaFX.alertErrorDialogWithException(ex);
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




    private void alertFirstOrSecondCharNotNumeric() {
        JavaFX.showSimpleErrorAlert("First and Second Values need to be numeric",
                "First and Second Values need to be numeric, and between 0 and 255 for ascii");
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
