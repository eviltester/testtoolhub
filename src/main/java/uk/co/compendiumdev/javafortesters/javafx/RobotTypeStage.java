package uk.co.compendiumdev.javafortesters.javafx;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.KeyEvent;
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

    public static void singletonActivate(){

        if(robotTypeStage ==null)
            robotTypeStage = new RobotTypeStage(false);

        robotTypeStage.show();
        robotTypeStage.requestFocus();
    }

    private class Robo{

        private long millisecondsPause;
        private Robot robot;
        private String textToType;
        private int currentChar;
        private long waitTime;

        public void setMilliseconds(long millisecondsPause) {
            this.millisecondsPause = millisecondsPause;
        }

        public Robot getRobot(){

            if(this.robot==null) {
                try {
                    this.robot = new Robot();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }

            return this.robot;
        }

        public boolean hasAnotherCharToType(){
            if(this.textToType!=null && this.currentChar<this.textToType.length()){
                return true;
            }
            return false;
        }

        public void setTextToType(String textToType) {
            this.textToType = textToType;
            this.currentChar = 0;
        }

        public String getNextCharToType() {
            int nextChar = this.currentChar+1;
            String retString =  this.textToType.substring(this.currentChar,nextChar);
            this.currentChar = nextChar;
            return retString;
        }

        public long getwaitTime() {
            return waitTime;
        }
    }

    public RobotTypeStage(boolean hidden) {

        BorderPane root = new BorderPane();

        HBox robotTypeControl = new HBox();
        final Label milliPauseLabel = new Label("MilliSeconds:");
        final TextField milliPauseVal = new TextField ();
        milliPauseVal.setTooltip(new Tooltip("The time to wait between keypresses in milliseconds"));
        milliPauseVal.setText("500");

        final Button robotType = new Button();
        robotType.setText("Robot");
        robotType.setTooltip(new Tooltip("Have robot type string into field"));

        robotTypeControl.getChildren().addAll(milliPauseLabel, milliPauseVal, robotType);
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

        Robo roboTyper = new Robo();


        //robot typing into field- never stop thread - once robot is used a thread ticks over in the background
        // ready to be re-used
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                int x=5;
                while(true) {
                    if(robotType.getText().startsWith("Robot")){
                        x=5;
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
                            if (robotType.getText().startsWith("Start") || robotType.getText().startsWith("In [")) {
                                robotType.setText("In [" + finalX + "] secs");
                            }
                            if (finalX <= 0) {
                                robotType.setText("GO");
                                // calculate counterstring and iterator here

                                robotType.setText("...");
                            }
                            if (robotType.getText().startsWith("...")) {
                                if (roboTyper.hasAnotherCharToType()) {
                                    String outputString = roboTyper.getNextCharToType();
                                    robotType.setText("..." + outputString);
                                    //System.out.println(outputString);
                                    for (char c : outputString.toCharArray()) {
                                        //System.out.println(""+c);
                                        // this hack means that with robot we should really default to *
                                        if (c == '*') {
                                            roboTyper.getRobot().keyPress(KeyEvent.VK_SHIFT);
                                            roboTyper.getRobot().keyPress(KeyEvent.VK_8);
                                            roboTyper.getRobot().keyRelease(KeyEvent.VK_8);
                                            roboTyper.getRobot().keyRelease(KeyEvent.VK_SHIFT);
                                        } else {
                                            roboTyper.getRobot().keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
                                            roboTyper.getRobot().keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));
                                        }
                                    }

                                    try {
                                        Thread.sleep(roboTyper.getwaitTime());
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    
                                } else {
                                    // we are finished
                                    robotType.setText("Robot");
                                }
                            }
                        }
                    });

                    if(robotType.getText().startsWith("Robot")||robotType.getText().startsWith("In [")) {
                        Thread.sleep(1000);
                    }else{
                        Thread.sleep(10);
                        x=5;
                    }
                    System.out.println("Thread " + x);
                }
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);





        robotType.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {

                        System.out.println("clicked robot");

                        try {

                            if (!th.isAlive()) {
                                th.start();
                            }

                            if (robotType.getText().startsWith("Robot")) {

                                roboTyper.setMilliseconds(Long.parseLong(milliPauseVal.getText()));
                                roboTyper.setTextToType(textArea.getText());
                                robotType.setText("Start");

                            } else {
                                robotType.setText("Robot");
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
