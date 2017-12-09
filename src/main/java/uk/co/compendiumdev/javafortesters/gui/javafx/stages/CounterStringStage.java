package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.compendiumdev.javafortesters.counterstrings.CounterString;
import uk.co.compendiumdev.javafortesters.counterstrings.CounterStringCreationError;
import uk.co.compendiumdev.javafortesters.counterstrings.County;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;
import uk.co.compendiumdev.javafortesters.gui.javafx.robottasks.CounterStringRobotTask;


public class CounterStringStage extends Stage {

    private static CounterStringStage counterStringSingletonStage=null;
    static CounterStringRobotTask robotTasker;

    public static void singletonActivate(){

        if(counterStringSingletonStage==null)
            counterStringSingletonStage = new CounterStringStage(false);

        counterStringSingletonStage.show();
        counterStringSingletonStage.requestFocus();
    }


    public CounterStringStage(boolean hidden) {

        BorderPane root = new BorderPane();

        HBox counterstringControl = new HBox();
        Label lengthLabel = new Label("Length:");

        TextField counterLength = JavaFX.textField("100", "The length of the CounterString to create");
        Button createCounter = JavaFX.button("Create", "Create a CounterString and display in the text area");
        TextField counterstringSpacer = JavaFX.textField("*", "The spacer value for the counterstring", 50);
        addTextLimiter(counterstringSpacer,1);

        Button createClipboard = JavaFX.button("=>", "Create direct to clipboard");
        Button copyCounterString = JavaFX.button("Copy", "Copy the text in the text area to the clipboard");


        counterstringControl.getChildren().addAll(lengthLabel, counterLength, counterstringSpacer,
                createCounter, createClipboard, copyCounterString);
        counterstringControl.setSpacing(10);


        HBox lengthControl = new HBox();

        Button lenCounter = JavaFX.button("Length?", "Count the number of characters in the text area below");

        Label lengthCount = new Label("");

        Button clearTextArea = JavaFX.button("Clear", "Clear the text from the text area");
        Button configureRobotButton = JavaFX.button("Configure Robot", "Configure Robot To use current counterstring settings");
        Button robotTypeButton = JavaFX.button("Robot", "Have robot type counterstring into field");

        lengthControl.getChildren().addAll(lenCounter, lengthCount, clearTextArea, configureRobotButton, robotTypeButton);
        lengthControl.setSpacing(10);



        final TextArea textArea = new TextArea("");
        textArea.setWrapText(true);

        VBox form = new VBox();
        form.getChildren().addAll(counterstringControl, lengthControl);

        root.setTop(form);
        root.setCenter(textArea);

        Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
        this.setTitle("Counterstrings");
        this.setScene(scene);

        if(!hidden)
            this.show();


        robotTasker = new CounterStringRobotTask(robotTypeButton);

        County county = new County();
        int counterStringLength = Integer.parseInt(counterLength.getText());
        String counterSpacer = counterstringSpacer.getText();
        robotTasker.configureCounterStringRobotTyper(county, counterStringLength, counterSpacer);

        // when close stage, stop the counterstring generation
        this.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                robotTasker.stopTheTask();
            }
        });



        configureRobotButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            int counterStringLength = Integer.parseInt(counterLength.getText());
                            String counterSpacer = counterstringSpacer.getText();
                            robotTasker.configureRobot(counterStringLength, counterSpacer);
                        }
                        catch(NumberFormatException ex){
                            alertLengthNotNumeric();
                        //} catch (CounterStringCreationError counterStringCreationError) {
                        //    alertException(counterStringCreationError);
                        }catch(Exception ex){
                            JavaFX.alertErrorDialogWithException(ex);
                        }
                    }
                });


        createClipboard.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            int len = Integer.parseInt(counterLength.getText());
                            sendToClipboard(new CounterString().create(len,counterstringSpacer.getText()), copyCounterString);
                        }
                        catch(NumberFormatException ex){
                            alertLengthNotNumeric();
                        } catch (CounterStringCreationError counterStringCreationError) {
                            JavaFX.alertErrorDialogWithException(counterStringCreationError);
                        }catch(Exception ex){
                            JavaFX.alertErrorDialogWithException(ex);
                        }
                    }
                });

        copyCounterString.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {

                            sendToClipboard(textArea.getText(), copyCounterString);

                        } catch (NumberFormatException ex) {
                            alertLengthNotNumeric();
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

        lenCounter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    lengthCount.setText(String.valueOf(textArea.getText().length()));
                }catch (Exception ex){
                    JavaFX.alertErrorDialogWithException(ex);
                }
            }
        });

        createCounter.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                try {
                    int len = Integer.parseInt(counterLength.getText());
                    textArea.setText(new CounterString().create(len,counterstringSpacer.getText()));
                    copyCounterString.setText("Copy");
                }
                catch(NumberFormatException ex){
                    alertLengthNotNumeric();
                } catch (CounterStringCreationError counterStringCreationError) {
                    JavaFX.alertErrorDialogWithException(counterStringCreationError);
                }catch(Exception ex){
                    JavaFX.alertErrorDialogWithException(ex);
                }

            }
        });



    }


    //http://stackoverflow.com/questions/15159988/javafx-2-2-textfield-maxlength
    public void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    private void alertLengthNotNumeric() {
        JavaFX.showSimpleErrorAlert("Length is not numeric", "Length needs to be an integer");
    }

    private void sendToClipboard(String contents, Button copyCounter) {
        copyCounter.setText("Copying");
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(contents);
        clipboard.setContent(content);
        copyCounter.setText("Copied");
    }


    public static void stopServices() {
        if(robotTasker!=null){
            robotTasker.stopTheTask();
        }
    }
}
