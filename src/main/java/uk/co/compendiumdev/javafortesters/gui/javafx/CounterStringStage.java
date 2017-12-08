package uk.co.compendiumdev.javafortesters.gui.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.compendiumdev.javafortesters.counterstrings.CounterString;
import uk.co.compendiumdev.javafortesters.counterstrings.CounterStringCreationError;
import uk.co.compendiumdev.javafortesters.counterstrings.County;

import java.io.PrintWriter;
import java.io.StringWriter;


public class CounterStringStage extends Stage {

    private static CounterStringStage counterStringSingletonStage=null;
    private static Service task;

    public static void singletonActivate(){

        if(counterStringSingletonStage==null)
            counterStringSingletonStage = new CounterStringStage(false);

        counterStringSingletonStage.show();
        counterStringSingletonStage.requestFocus();
    }



    public CounterStringStage(boolean hidden) {

        BorderPane root = new BorderPane();

        HBox counterstringControl = new HBox();
        final Label lengthLabel = new Label("Length:");
        final TextField counterLength = new TextField ();
        counterLength.setTooltip(new Tooltip("The length of the CounterString to create"));
        counterLength.setText("100");
        Button createCounter = new Button();
        createCounter.setText("Create");
        createCounter.setTooltip(new Tooltip("Create a CounterString and display in the text area"));

        final TextField counterstringSpacer = new TextField ();
        counterstringSpacer.setText("*");
        counterstringSpacer.setTooltip(new Tooltip("The spacer value for the counterstring"));
        counterstringSpacer.setMaxWidth(50);
        addTextLimiter(counterstringSpacer,1);

        Button createClipboard = new Button();
        createClipboard.setText("=>");
        createClipboard.setTooltip(new Tooltip("Create direct to clipboard"));

        final Button copyCounterString = new Button();
        copyCounterString.setText("Copy");
        copyCounterString.setTooltip(new Tooltip("Copy the text in the text area to the clipboard"));

        counterstringControl.getChildren().addAll(lengthLabel, counterLength, counterstringSpacer,
                createCounter, createClipboard, copyCounterString);
        counterstringControl.setSpacing(10);


        HBox lengthControl = new HBox();
        final Button lenCounter = new Button();
        lenCounter.setText("Length?");
        lenCounter.setTooltip(new Tooltip("Count the number of characters in the text area below"));
        final Label lengthCount = new Label("");

        final Button clearTextArea = new Button();
        clearTextArea.setText("Clear");
        clearTextArea.setTooltip(new Tooltip("Clear the text from the text area"));


        final Button configureRobotButton = new Button();
        configureRobotButton.setText("Configure Robot");
        configureRobotButton.setTooltip(new Tooltip("Configure Robot To use current counterstring settings"));

        final Button robotTypeButton = new Button();
        robotTypeButton.setText("Robot");
        robotTypeButton.setTooltip(new Tooltip("Have robot type counterstring into field"));

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


        final RobotTask robotTasker = new RobotTask(robotTypeButton);

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
                            alertException(ex);
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
                            alertException(counterStringCreationError);
                        }catch(Exception ex){
                            alertException(ex);
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

        lenCounter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    lengthCount.setText(String.valueOf(textArea.getText().length()));
                }catch (Exception ex){
                    alertException(ex);
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
                    alertException(counterStringCreationError);
                }catch(Exception ex){
                    alertException(ex);
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

    private void alertLengthNotNumeric() {
        // http://code.makery.ch/blog/javafx-dialogs-official/
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Length is not numeric");
        alert.setHeaderText(null);
        alert.setContentText("Length needs to be an integer");
        alert.showAndWait();
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
        if(task!=null){
            if(task.isRunning()){
                task.cancel();
                task=null;
            }
        }

    }
}
