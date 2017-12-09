package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;

/*
    20170306 knocked up quick typer for my use
    Not ready for prime time
    - does not handle upper case
    - does not handle special chars (except * which is hard coded
    - when fix this then can enable in MainGui
 */

public class ConfigEditStage extends Stage {

    private static ConfigEditStage configEditStage =null;

    public static void singletonActivate(){

        if(configEditStage ==null)
            configEditStage = new ConfigEditStage(false);

        configEditStage.show();
        configEditStage.requestFocus();
    }

    public static EventHandler<ActionEvent> getActivationEvent() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ConfigEditStage.singletonActivate();
            }
        };
    }

    public ConfigEditStage(boolean hidden) {

        BorderPane root = new BorderPane();

        HBox modifiersControl = new HBox();
            final Label modifiersLabel = new Label("Shift Modifiers:");
            final TextField modifiers = JavaFX.textField(Config.getCurrentShiftModifiers(), "A list of pairs of chars - \n first char is the char we want to type, \n next char is the key we press with \n shift to get the wanted char \n e.g. !1 means I want ! so press 1 with shift key");
            modifiers.setPrefWidth(300);
            final Button setModifiers = JavaFX.button("Set","Set modifiers to the text");
            final Button defaultModifiers = JavaFX.button("Defaults","Set modifiers to the defaults");
        modifiersControl.getChildren().addAll(modifiersLabel, modifiers, setModifiers, defaultModifiers);
        modifiersControl.setSpacing(10);

        root.setTop(modifiersControl);

        Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
        this.setTitle("Configuration");
        this.setScene(scene);
        if(!hidden)
            this.show();



        setModifiers.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            String theModifiers = modifiers.getText();
                            if(theModifiers.length()%2!=0){
                                System.out.println("ERROR: the modifier String must be pairs of chars 'wanted' followed by 'shifted'");
                                throw new IllegalArgumentException("The modifier String must be pairs of chars 'wanted' followed by 'shifted'");
                            }
                            Config.setCurrentShiftModifiers(theModifiers);
                        }
                        catch(IllegalArgumentException ex){
                            alertExplainModifiers();
                        }catch(Exception ex){
                            JavaFX.alertErrorDialogWithException(ex);
                        }
                    }
                });

        defaultModifiers.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            modifiers.setText(Config.getDefaultShiftModifiers());
                            Config.setCurrentShiftModifiers(Config.getDefaultShiftModifiers());
                        }
                        catch(IllegalArgumentException ex){
                            alertExplainModifiers();
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


    private void alertExplainModifiers() {
        JavaFX.showSimpleErrorAlert("Modifiers is not a list of paired Strings",
                "The modifiers is a string of paired characters.\n"+
                        "e.g. !1@2\n"+
                        "Which means - I want ! so press 1 with shift\n"+
                        " - and I want @ so press 2 with shift\n" +
                        "- the list should match all the shifted keys on your keyboard."
                );
    }


}
