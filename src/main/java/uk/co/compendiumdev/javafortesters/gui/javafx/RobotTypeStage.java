package uk.co.compendiumdev.javafortesters.gui.javafx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.compendiumdev.javafortesters.gui.javafx.robottasks.RobotTyperTask;

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
    static RobotTyperTask robotTasker;

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

        final Button configureRobotButton = new Button();
        configureRobotButton.setText("Configure Robot");
        configureRobotButton.setTooltip(new Tooltip("Configure Robot To use current typing settings"));

        final Button robotTypeButton = new Button();
        robotTypeButton.setText("Robot");
        robotTypeButton.setTooltip(new Tooltip("Have robot type string into field"));

        robotTypeControl.getChildren().addAll(milliPauseLabel, milliPauseVal, configureRobotButton, robotTypeButton);
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


        robotTasker = new RobotTyperTask(robotTypeButton);
        robotTasker.configureRobot(Long.parseLong(milliPauseVal.getText()), textArea.getText());


        // when close stage, stop the robot typing generation
        this.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<javafx.event.Event>() {
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
                            robotTasker.configureRobot(Long.parseLong(milliPauseVal.getText()), textArea.getText());
                        }
                        catch(NumberFormatException ex){
                            alertLengthNotNumeric();
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


    public static void stopServices() {
        if(robotTasker!=null){
            robotTasker.stopTheTask();
        }
    }

}
