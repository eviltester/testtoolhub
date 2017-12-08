package uk.co.compendiumdev.javafortesters.gui.javafx;

import uk.co.compendiumdev.javafortesters.html.comments.HTMLCommentReporter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

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
        final TextField urlToCheck = new TextField ();
        urlToCheck.setTooltip(new Tooltip("What URL to check?"));
        urlToCheck.setText("");
        Button checkURL = new Button();
        checkURL.setText("Check");
        checkURL.setTooltip(new Tooltip("Check URL For Comments"));


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
                            alertException(ex);
                        }

                    }
                });

        };

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

}
