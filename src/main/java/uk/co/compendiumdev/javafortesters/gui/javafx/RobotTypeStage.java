package uk.co.compendiumdev.javafortesters.gui.javafx;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.compendiumdev.javafortesters.gui.awtbridge.RobotTyper;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
    20170306 knocked up quick typer for my use
    Not ready for prime time
    - does not handle upper case
    - does not handle special chars (except * which is hard coded
    - when fix this then can enable in MainGui
 */

public class RobotTypeStage extends Stage {

    private static RobotTypeStage robotTypeStage =null;
    private static Service task;

    public static void singletonActivate(){

        if(robotTypeStage ==null)
            robotTypeStage = new RobotTypeStage(false);

        robotTypeStage.show();
        robotTypeStage.requestFocus();
    }

    public RobotTypeStage(boolean hidden) {

        BorderPane root = new BorderPane();

        HBox robotTypeControl = new HBox();
        final Label milliPauseLabel = new Label("MilliSeconds:");
        final TextField milliPauseVal = new TextField ();
        milliPauseVal.setTooltip(new Tooltip("The time to wait between keypresses in milliseconds"));
        milliPauseVal.setText("500");

        final Button robotTypeButton = new Button();
        robotTypeButton.setText("Robot");
        robotTypeButton.setTooltip(new Tooltip("Have robot type string into field"));

        robotTypeControl.getChildren().addAll(milliPauseLabel, milliPauseVal, robotTypeButton);
        robotTypeControl.setSpacing(10);

        final TextArea textArea = new TextArea("");
        textArea.setWrapText(true);

        VBox form = new VBox();
        form.getChildren().addAll(robotTypeControl);

        root.setTop(form);
        root.setCenter(textArea);

        Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
        this.setTitle("Robot Typer");
        this.setScene(scene);
        if(!hidden)
            this.show();


        // when close stage, stop the counterstring generation
        this.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<javafx.event.Event>() {
            @Override
            public void handle(Event event) {
                if(task!=null) {
                    if (task.isRunning()) {
                        task.cancel();
                        robotTypeButton.setText("Robot");
                        robotTypeButton.setTooltip(new Tooltip("Have robot type string into field"));
                    }
                }
            }
        });

        RobotTyper roboTyper = new RobotTyper();


        //robot typing into field- never stop thread - once robot is used a thread ticks over in the background
        // ready to be re-used
        task = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    public Void call() throws Exception {
                        int x = 5;

                        while (!isCancelled()) {
                            if (robotTypeButton.getText().startsWith("Robot")) {
                                x = 5;
                            }
                            final int finalX = x--;

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {


                                    // This needs to be a state machine
                                    // Robot means stop don't do anything
                                    // Start means start counting down
                                    // In [ means continue counting
                                    // GO means calculate the counterstring
                                    // ... means iterate through and send the keys
                                    if (robotTypeButton.getText().startsWith("Start") || robotTypeButton.getText().startsWith("In [")) {
                                        robotTypeButton.setText("In [" + finalX + "] secs");
                                    }
                                    if (finalX <= 0) {
                                        robotTypeButton.setText("GO");
                                        // calculate counterstring and iterator here

                                        robotTypeButton.setText("...");
                                    }
                                    if (robotTypeButton.getText().startsWith("...")) {
                                        if (roboTyper.hasAnotherCharToType()) {

                                            String outputString = roboTyper.revealNextCharToType();
                                            robotTypeButton.setText("..." + outputString);

                                            roboTyper.typeNextChar();

                                        } else {
                                            // we are finished
                                            robotTypeButton.setText("Robot");
                                        }
                                    }
                                }

                            });

                            if (robotTypeButton.getText().startsWith("Robot") || robotTypeButton.getText().startsWith("In [")) {
                                Thread.sleep(1000);
                            } else {
                                Thread.sleep(10);
                                x = 5;
                            }
                            System.out.println("Thread " + x);
                        }
                        return null;
                    }

                };
            }
        };




        robotTypeButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {

                        System.out.println("clicked robot");

                        try {

                            if(task.isRunning()){
                                robotTypeButton.setText("Cancelling");
                                task.cancel();
                                robotTypeButton.setText("Robot");
                                robotTypeButton.setTooltip(new Tooltip("Have robot start typing"));
                                return;
                            }

                            if (robotTypeButton.getText().startsWith("Robot")) {

                                if(!task.isRunning()){
                                    task.reset();
                                    roboTyper.setMilliseconds(Long.parseLong(milliPauseVal.getText()));
                                    roboTyper.setTextToType(textArea.getText());
                                    robotTypeButton.setText("Start");
                                    robotTypeButton.setTooltip(new Tooltip("Click Button again to cancel Robot Typing"));
                                    task.start();
                                }


                            } else {
                                robotTypeButton.setText("Robot");
                            }

                        } catch (NumberFormatException ex) {
                            alertLengthNotNumeric();
                        } catch (Exception ex) {
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


}
