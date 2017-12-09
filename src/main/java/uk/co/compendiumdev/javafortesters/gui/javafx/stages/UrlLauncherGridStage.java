package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.urllauncher.PhysicalUrlLauncher;
import uk.co.compendiumdev.javafortesters.launcher.LauncherUrl;
import uk.co.compendiumdev.javafortesters.launcher.LauncherUrlLoader;
import uk.co.compendiumdev.javafortesters.launcher.LauncherUrlSet;
import uk.co.compendiumdev.javafortesters.launcher.UrlLauncher;


public class UrlLauncherGridStage  extends Stage {

    private static UrlLauncherGridStage urlLauncherGridSingletonStage=null;

    public static void singletonActivate() {

        if(urlLauncherGridSingletonStage==null)
            urlLauncherGridSingletonStage = new UrlLauncherGridStage(false);

        urlLauncherGridSingletonStage.show();
        urlLauncherGridSingletonStage.requestFocus();
    }

    public UrlLauncherGridStage(boolean hidden){



        // get the data
        LauncherUrlLoader loader = new LauncherUrlLoader();
        UrlLauncher urls = loader.load();


        final ObservableList<LauncherUrlSet> profiles = FXCollections.observableArrayList();
        profiles.addAll(urls.getSets());

        final ObservableList<LauncherUrl> urlsList = FXCollections.observableArrayList();
        urlsList.addAll(profiles.get(0).getUrls().values());



        BorderPane root = new BorderPane();

        // left grid
        TableView leftside = new TableView();
        leftside.setEditable(false);
        leftside.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn profileCol = new TableColumn("Profile");
        leftside.getColumns().add(profileCol);



        profileCol.setCellFactory(new Callback<TableColumn<LauncherUrlSet, String>, TableCell<LauncherUrlSet, String>>() {
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
                            LauncherUrlSet rowData = (LauncherUrlSet)cell.getTableRow().getItem();
                            PhysicalUrlLauncher.launch(rowData);
                        }else {
                            // if click count = 1 then set the data on the right hand side of the grid
                            urlsList.clear();
                            LauncherUrlSet rowData = (LauncherUrlSet)cell.getTableRow().getItem();
                            urlsList.addAll(rowData.getUrls().values());
                        }
                    }
                });
                return cell ;
            }
        });

        profileCol.setCellValueFactory(new PropertyValueFactory<LauncherUrlSet,String>("name"));
        leftside.setItems(profiles);


        // right grid
        TableView rightside = new TableView();
        rightside.setEditable(false);
        rightside.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn nameCol = new TableColumn("Name");
        TableColumn urlCol = new TableColumn("URL");
        rightside.getColumns().addAll(nameCol, urlCol);




        Callback<TableColumn<LauncherUrlSet, String>, TableCell<LauncherUrlSet, String>> urlcellhandler = new Callback<TableColumn<LauncherUrlSet, String>, TableCell<LauncherUrlSet, String>>() {
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
                            LauncherUrl rowData = (LauncherUrl) cell.getTableRow().getItem();
                            PhysicalUrlLauncher.launch(rowData.getUrl());
                        }
                    }
                });
                return cell;
            }};

            nameCol.setCellFactory(urlcellhandler);
            urlCol.setCellFactory(urlcellhandler);

            nameCol.setCellValueFactory(new PropertyValueFactory<LauncherUrl, String>("name"));
            urlCol.setCellValueFactory(new PropertyValueFactory<LauncherUrl, String>("url"));
            rightside.setItems(urlsList);

            // double click to launch


            HBox form = new HBox();
            form.getChildren().addAll(leftside, rightside);
            form.setHgrow(rightside, Priority.ALWAYS);


            root.setCenter(form);

            Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
            this.

            setTitle("URL Launcher Generator");

            this.

            setScene(scene);

            if(!hidden)
                    this.

            show();


        };

}
