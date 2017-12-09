package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;
import uk.co.compendiumdev.javafortesters.gui.javafx.robottasks.RobotTyperTask;

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
            final TextField milliPauseVal = JavaFX.textField("500", "The time to wait between keypresses in milliseconds");
            final Button configureRobotButton = JavaFX.button("Configure Robot","Configure Robot To use current typing settings");
            final Button robotTypeButton = JavaFX.button("Robot","Have robot type string into field");
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
        JavaFX.showSimpleErrorAlert("Length is not numeric","Length needs to be an integer");
    }


    public static void stopServices() {
        if(robotTasker!=null){
            robotTasker.stopTheTask();
        }
    }

}
