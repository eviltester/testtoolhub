package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import uk.co.compendiumdev.javafortesters.domain.cannedtext.CannedText;
import uk.co.compendiumdev.javafortesters.domain.cannedtext.CannedTextItem;
import uk.co.compendiumdev.javafortesters.domain.cannedtext.CannedTextItemTreeFinder;
import uk.co.compendiumdev.javafortesters.domain.tree.TreeKeyValue;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;
import uk.co.compendiumdev.javafortesters.domain.tree.TreeBranch;


public class CannedTextTreeStage extends Stage {


    private static CannedTextTreeStage cannedTextTreeSingletonStage=null;

    public static void singletonActivate() {

        if(cannedTextTreeSingletonStage==null)
            cannedTextTreeSingletonStage = new CannedTextTreeStage(false);

        cannedTextTreeSingletonStage.show();
        cannedTextTreeSingletonStage.requestFocus();
    }

    public static EventHandler<ActionEvent> getActivationEvent() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                CannedTextTreeStage.singletonActivate();
            }
        };
    }


    public CannedTextTreeStage(boolean hidden){


        TreeBranch<CannedTextItem> treeRoot = CannedText.getDefaultTree();
        final CannedTextItemTreeFinder finder = new CannedTextItemTreeFinder(treeRoot);

        // http://docs.oracle.com/javafx/2/api/javafx/scene/control/TreeItem.html
        // store label in label
        // store id in setValue
        TreeItem<TreeKeyValue> displayTreeRoot = new TreeItem<>(new TreeKeyValue("",treeRoot.getLabel()));

        addTreeChildrenToDisplayTree(treeRoot, displayTreeRoot);

        BorderPane root = new BorderPane();

        final TreeView<TreeKeyValue> displayTreeView = new TreeView<>(displayTreeRoot);

        root.setCenter(displayTreeView);

        Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
        this.setTitle("Canned Text Tree");
        this.setScene(scene);
        if(!hidden)
            this.show();


        displayTreeView.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getClickCount()==2){
                            TreeItem<TreeKeyValue> item = displayTreeView.getSelectionModel().getSelectedItem();
                            // add the value to the clipboard
                            if(item.getValue().key!=""){
                                // find it in the CannedTextTree
                                CannedTextItem texty = finder.find(item.getValue().key);
                                // get the value
                                if(texty!=null)
                                    sendToClipboard(texty.getTextValue());
                            }
                        }
                    }
                }
        );

    }

    private void addTreeChildrenToDisplayTree(TreeBranch<CannedTextItem> treeRoot, TreeItem<TreeKeyValue> displayTreeRoot) {

        displayTreeRoot.setExpanded(true);

        for(TreeBranch<CannedTextItem> cannedBranch : treeRoot.getChildren()){

            TreeItem<TreeKeyValue> aDisplayBranch = new TreeItem<>(new TreeKeyValue("",cannedBranch.getLabel()));
            displayTreeRoot.getChildren().add(aDisplayBranch);
            if(cannedBranch.isLeaf()) {
                aDisplayBranch.getValue().key=cannedBranch.getLeaf().getId();
            }
            addTreeChildrenToDisplayTree(cannedBranch, aDisplayBranch);
        }
    }

    private void sendToClipboard(String contents) {
        JavaFX.sendTextToClipboard(contents);
    }


}
