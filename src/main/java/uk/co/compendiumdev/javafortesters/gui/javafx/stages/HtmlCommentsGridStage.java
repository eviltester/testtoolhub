package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;
import uk.co.compendiumdev.javafortesters.html.comments.HTMLCommentReporter;

public class HtmlCommentsGridStage extends Stage {


    private static HtmlCommentsGridStage htmlCommentsGridSingletonStage=null;

    public static void singletonActivate() {

        if(htmlCommentsGridSingletonStage==null)
            htmlCommentsGridSingletonStage = new HtmlCommentsGridStage(false);

        htmlCommentsGridSingletonStage.show();
        htmlCommentsGridSingletonStage.requestFocus();
    }


    public HtmlCommentsGridStage(boolean hidden){


        BorderPane root = new BorderPane();

        // url
        HBox urlBox = new HBox();
            final Label urlLabel = new Label("URL:");
            final TextField urlToCheck = JavaFX.textField("","What URL to check?");
            Button checkURL = JavaFX.button("Check","Check URL For Comments");
        urlBox.getChildren().addAll(urlLabel, urlToCheck, checkURL);
        urlBox.setHgrow(urlToCheck, Priority.ALWAYS);

        // comments
        final TextArea textArea = new TextArea("");
        textArea.setWrapText(true);
        HBox form = new HBox();
        form.getChildren().addAll(textArea);
        form.setHgrow(textArea, Priority.ALWAYS);


        root.setTop(urlBox);
        root.setCenter(form);

        Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
        this.setTitle("HTML Page Comment Finder");

        this.setScene(scene);

        if(!hidden)
                this.show();



        checkURL.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {

                            textArea.setText(new HTMLCommentReporter(urlToCheck.getText()).commentsReport());

                        } catch (Exception ex) {
                            JavaFX.alertErrorDialogWithException(ex);
                        }

                    }
                });

        };



}
