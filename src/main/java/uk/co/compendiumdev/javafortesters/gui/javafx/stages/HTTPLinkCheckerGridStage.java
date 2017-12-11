package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.junit.Assert;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkChecker;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkQueue;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkQueueFileReader;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkToCheck;
import uk.co.compendiumdev.javafortesters.domain.launcher.LauncherUrlSet;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;
import uk.co.compendiumdev.javafortesters.gui.urllauncher.PhysicalUrlLauncher;

import java.io.File;
import java.net.URL;


public class HTTPLinkCheckerGridStage extends Stage {

    private static HTTPLinkCheckerGridStage urlLauncherGridSingletonStage=null;

    public static void singletonActivate() {

        if(urlLauncherGridSingletonStage==null)
            urlLauncherGridSingletonStage = new HTTPLinkCheckerGridStage(false);

        urlLauncherGridSingletonStage.show();
        urlLauncherGridSingletonStage.requestFocus();
    }

    public static EventHandler<ActionEvent> getActivationEvent() {
        return
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        HTTPLinkCheckerGridStage.singletonActivate();
                    }
                };
    }



    public HTTPLinkCheckerGridStage(boolean hidden){

        // filename of file loaded | load |
        // check urls | save report

        HBox fileLoadControl = new HBox();
        TextField filename = JavaFX.textField("Default Resource File", "The file of URLs loaded");
        filename.setPrefWidth(400);
        Button loadUrlButton = JavaFX.button("Load", "Load a test file of URLs");
        fileLoadControl.getChildren().addAll(filename, loadUrlButton);
        fileLoadControl.setSpacing(10);

        HBox actionButtonsControl = new HBox();
            Button checkUrlsButton = JavaFX.button("Check URLs", "Check all the URLs");
            //Button saveUrlReportButton = JavaFX.button("Save URL Report", "Save the URL report to file");
        actionButtonsControl.getChildren().addAll(checkUrlsButton); //, saveUrlReportButton);
        actionButtonsControl.setSpacing(10);


        VBox buttonsControl = new VBox();
        buttonsControl.getChildren().addAll(fileLoadControl, actionButtonsControl);

        // get the default data
        LinkQueue defaultLinks = loadDefaultLinks();

        //LauncherUrlLoader loader = new LauncherUrlLoader();
        //UrlLauncher urls = loader.load();

        final ObservableList<LinkToCheck> urlsToCheck = FXCollections.observableArrayList();
        urlsToCheck.addAll(defaultLinks.getLinks());

        //final ObservableList<LauncherUrl> urlsList = FXCollections.observableArrayList();
        //urlsList.addAll(profiles.get(0).getUrl());

        BorderPane root = new BorderPane();

        // left grid
        TableView leftside = new TableView();
        leftside.setEditable(false);
        leftside.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn urlColumn = new TableColumn("URLs");
        leftside.getColumns().add(urlColumn);

        urlColumn.setCellFactory(new Callback<TableColumn<LauncherUrlSet, String>, TableCell<LauncherUrlSet, String>>() {
            @Override
            public TableCell<LauncherUrlSet, String> call(TableColumn<LauncherUrlSet, String> col) {
                final TableCell<LauncherUrlSet, String> cell = new TableCell<LauncherUrlSet, String>() {
                    @Override
                    public void updateItem(String firstName, boolean empty) {
                        super.updateItem(firstName, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(firstName);
                        }
                    }
                };
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() > 1) {
                            // double click open URL
                            LinkToCheck rowData = (LinkToCheck) cell.getTableRow().getItem();
                            PhysicalUrlLauncher.launch(rowData.getUrl());
                        }else {
                            // single click ignore
                            // if click count = 1 then set the data on the right hand side of the grid
                            //urlsToCheck.clear();
                            //LauncherUrlSet rowData = (LauncherUrlSet)cell.getTableRow().getItem();
                            //urlsToCheck.addAll(rowData.getUrls().values());
                        }
                    }
                });
                return cell ;
            }
        });


        urlColumn.setCellValueFactory(new PropertyValueFactory<LauncherUrlSet,String>("url"));
        leftside.setItems(urlsToCheck);


        final TextArea textArea = new TextArea("");
        textArea.setWrapText(true);


        checkUrlsButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            LinkChecker linkChecker = new LinkChecker(defaultLinks);
                            textArea.setText(linkChecker.getReportStateOfLinks());

                            // need to update as we go so will need to create a worker/task for this
                            linkChecker.checkLinksReportingAsWeGo();
                            //linkChecker.checkLinksReportingAsWeGo(new JavaFxTextOutputter(stringProperty));
                            textArea.setText(linkChecker.getReportStateOfLinks());
                        } catch (Exception ex) {
                            JavaFX.alertErrorDialogWithException(ex);
                        }

                    }
                });



            HBox form = new HBox();
            form.getChildren().addAll(leftside, textArea);
            form.setHgrow(textArea, Priority.ALWAYS);


            root.setTop(buttonsControl);
            root.setCenter(form);

            Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
            this.

            setTitle("Simple Link Checker");

            this.setScene(scene);

            if(!hidden)
                this.show();


        }

    private LinkQueue loadDefaultLinks() {
        LinkQueue links = new LinkQueue();

        String filenameForResource = "defaultLinksToCheck.txt";

        try {

            URL fileToRead = HTTPLinkCheckerGridStage.class.getResource(filenameForResource);
            File linkQueueFile;

            linkQueueFile = new File(fileToRead.toURI());

            Assert.assertNotNull(linkQueueFile);

            LinkQueueFileReader fileReader = new LinkQueueFileReader(linkQueueFile);

            links = fileReader.getQueue();

        }catch(Exception e){
            JavaFX.alertErrorDialogWithException(e, "Error Loading Default Resource File " + filenameForResource);
        }

        return links;
    }

}
