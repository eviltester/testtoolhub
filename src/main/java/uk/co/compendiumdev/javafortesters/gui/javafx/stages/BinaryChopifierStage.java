package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uk.co.compendiumdev.javafortesters.domain.binarychopifier.BinaryChopReporter;
import uk.co.compendiumdev.javafortesters.domain.binarychopifier.BinaryChopResults;
import uk.co.compendiumdev.javafortesters.domain.binarychopifier.BinaryChopifier;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;

public class BinaryChopifierStage extends Stage {


    private static BinaryChopifierStage htmlCommentsGridSingletonStage=null;

    public static void singletonActivate() {

        if(htmlCommentsGridSingletonStage==null)
            htmlCommentsGridSingletonStage = new BinaryChopifierStage(false);

        htmlCommentsGridSingletonStage.show();
        htmlCommentsGridSingletonStage.requestFocus();
    }

    public static EventHandler<ActionEvent> getActivationEvent() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                BinaryChopifierStage.singletonActivate();
            }
        };
    }

    public BinaryChopifierStage(boolean hidden){


        BorderPane root = new BorderPane();

        HBox chopifierInputs = new HBox();
        final Label firstCharLbl = new Label("First Value:");

        final TextField firstCharTxt = JavaFX.textField("1", "Starting Ascii Code Value");

        final Label secondCharLbl = new Label("Second Value:");
        final TextField secondCharTxt = JavaFX.textField("255", "Final Ascii Code Value");

        HBox chopifierInputButtons = new HBox();
        Button chopify = JavaFX.button("Chop", "Calculate Binary Chopify Values");

        chopifierInputs.getChildren().addAll(  firstCharLbl, firstCharTxt, secondCharLbl, secondCharTxt);
        chopifierInputButtons.getChildren().addAll(  chopify);
        chopifierInputs.setSpacing(10);
        chopifierInputButtons.setSpacing(10);

        VBox inputs = new VBox();
        inputs.getChildren().addAll(chopifierInputs, chopifierInputButtons);


        // comments
        final TextArea textArea = new TextArea("");
        textArea.setWrapText(true);
        HBox form = new HBox();
        form.getChildren().addAll(textArea);
        form.setHgrow(textArea, Priority.ALWAYS);


        root.setTop(inputs);
        root.setCenter(form);

        Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
        this.setTitle("Binary Chopifier");

        this.setScene(scene);

        if(!hidden)
                this.show();



        chopify.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {

                            BinaryChopifier binaryChopper = new BinaryChopifier();
                            int firstVal = Integer.parseInt(firstCharTxt.getText());
                            int secondVal = Integer.parseInt(secondCharTxt.getText());
                            BinaryChopResults binaryChop = binaryChopper.chop(firstVal, secondVal);
                            textArea.setText(new BinaryChopReporter(binaryChop).getStringReport());

                        } catch (Exception ex) {
                            JavaFX.alertErrorDialogWithException(ex);
                        }

                    }
                });

        };




}
