package uk.co.compendiumdev.javafortesters;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.compendiumdev.javafortesters.gui.javafx.stages.*;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;


public class MainGui extends Application{

    //DONE: Prompt "Are you sure?" when closing main form, then exit app if YES
    //TODO: Repeat strings
    //DONE: counterstring generator
    //TODO: string generator to file
    //TODO: Locky
    //TODO: file generator    (binary, text)
    //TODO: Robot Typer
    //    - (done 1.3) show any characters we could not type, after typing finished or paused
    //    - (done 1.3) automatically add the untypeable characters into the clipboard
    //TODO: Configuration
    //    - (done 1.3) have a configuration dialog
    //    - (done 1.3) allow configuration of the shift modifiers from config dialog
    //    - save shift modifiers from config dialog to a file
    //    - load shift modifiers from a file automatically
    //    - load shift modifiers from a file on config dialog
    //TODO: HTML comment Scanner
        // TODO show progress on scanner
        // Fix bugs as it can't parse every page e.g.   http://stackoverflow.com/questions/231051/is-there-a-memory-efficient-replacement-of-java-lang-string
        // TODO: Show any exceptions as a result of parsing
        // TODO: if no comments - show a 'no comments found' type message
    // Web Helpers
    //   - find all HTML comments on a web page <!-- -->
    //   - list all images
    //   - check all links
    //   - comments in Java Script
    //   - comments in css
    //   - spell check?
    //TODO program launcher
//        1) - just write 'launch' code for hardcoded urls in a list [done]
//                1.1) - create a button to launch hard coded items [no need for this, run it from a test]
//        2) - load list from properties file [done]
//                2.1) replace hard coded list with default properties file load [done]
//                2.2) move default properties file from resources into a relative path location, [done]
//                2.3) if relative path one does not exist then use resource file [done]
//                2.4) load files into a structure for urls [done]
//        3) show launch list as a 2 grid in GUI (left properties file name, right key/url) [done]
//        3.1) - launch all from gui [done]
//        3.2) - launch a single url from gui [done]
//        3.3) - allow gui to load from properties file into launch grid
//        4) - load from properties file from a url as well as a file e.g. shared on google docs or something
//        5) - have configuration file of which properties files to load
//        6) - expand gui to amend the tree and add new launch items

    //TODO URL launcher
    // TODO Copy Paste String helper
        // [DONE] hierarchy of 'canned' text
        // [DONE] Render default hierarchy in a tree view
        // [DONE]Paste from treeview to clipboard
        // [DONE] Fix resizing of the treeview
        // [DONE] Expand data in the default tree view (by reading from resource file)
        // allow dynamic text - i.e. generate from templates
        // import gojko bugmagnet config
        // https://github.com/gojko/bugmagnet/blob/master/template/common/config.json
        // https://raw.githubusercontent.com/gojko/bugmagnet/master/template/common/config.json

        // canned text (id, label, text, description)
        // canned text template

        // hierarchy

    // TODO Log all actions - consider aspects for this http://en.wikipedia.org/wiki/Aspect-oriented_programming

    // Resources
    //   links to validators etc.  CSS Lint, CSS Validator, HTML etc.

    //http://fxexperience.com/controlsfx/features/

    @Override
    public void start(Stage stage) {
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 100);
        stage.setOnCloseRequest(exitApplicationHandler());

        VBox buttonList = new VBox(10);
            HBox buttonsRow1 = new HBox();
                Button createCounter = JavaFX.button("Counterstrings","Create a CounterString");
                Button createString = JavaFX.button("Strings","Create different Strings");
                Button robotTyper =JavaFX.button("Typer","Type Strings Using Robot");
            buttonsRow1.getChildren().addAll(createCounter, createString, robotTyper);
            buttonsRow1.setSpacing(10);

            HBox buttonsRow2 = new HBox();
                Button cannedText = JavaFX.button("Canned Text Tree","Send Canned Text to the Clipboard");
                Button launcher = JavaFX.button("Launch URLs", "Launch URLs defined in config files");
                Button htmlComments = JavaFX.button("HTML Comments","Find HTML Comments from a URL");
            buttonsRow2.getChildren().addAll(cannedText, launcher, htmlComments);
            buttonsRow2.setSpacing(10);

            HBox buttonsRow3 = new HBox();
                Button binaryChopper = JavaFX.button("Binary Chop","Calculate a Binary Chop");
                Button configEditor = JavaFX.button("Config","Show/Edit Config");
            buttonsRow3.getChildren().addAll(binaryChopper,configEditor);
            buttonsRow3.setSpacing(10);

        buttonList.getChildren().addAll(buttonsRow1, buttonsRow2, buttonsRow3);

        root.setCenter(buttonList);

        stage.setTitle("Test Tool Hub");
        stage.setScene(scene);
        stage.show();

        createCounter.setOnAction( CounterStringStage.getActivationEvent() );
        createString.setOnAction( StringGeneratorStage.getActivationEvent() );
        robotTyper.setOnAction( RobotTypeStage.getActivationEvent());
        cannedText.setOnAction( CannedTextTreeStage.getActivationEvent());
        launcher.setOnAction(UrlLauncherGridStage.getActivationEvent());
        htmlComments.setOnAction(HtmlCommentsStage.getActivationEvent());
        binaryChopper.setOnAction(BinaryChopifierStage.getActivationEvent());
        configEditor.setOnAction(ConfigEditStage.getActivationEvent());
    }

    private EventHandler<WindowEvent> exitApplicationHandler() {
        return new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                // TODO: Known bug - if a robot is working then it willissue keypresses that will probably activate the ok button
                // and the dialog will probably not prompt - could 'pause' the robot prior to dialog
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Application Exit");
                alert.setHeaderText("Are you sure you want to quit the application?");
                alert.setContentText("Press OK to exit the application. Press Cancel to exit this dialog and keep the application running.");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {

                        // stop any stages that might have robots running
                        CounterStringStage.stopServices();
                        RobotTypeStage.stopServices();

                        Platform.exit();
                        System.exit(0);
                    }else{
                        event.consume();
                    }
                });
            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
