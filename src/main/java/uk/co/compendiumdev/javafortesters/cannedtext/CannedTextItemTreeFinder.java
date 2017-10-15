package uk.co.compendiumdev.javafortesters.cannedtext;

import uk.co.compendiumdev.javafortesters.tree.TreeBranch;


public class CannedTextItemTreeFinder {
    private final TreeBranch<CannedTextItem> root;

    public CannedTextItemTreeFinder(TreeBranch<CannedTextItem> startRoot) {
        this.root = startRoot;
    }

    public CannedTextItem find(String id) {

        return finder(this.root, id);

    }

    private CannedTextItem finder(TreeBranch<CannedTextItem> aRoot, String id) {
        if(aRoot.isLeaf()){
            if(aRoot.getLeaf().getId().contentEquals(id)) {
                return aRoot.getLeaf();
            }else{
                return null;
            }
        }else {

            for (TreeBranch<CannedTextItem> child :aRoot.getChildren()) {
                // find in children
                CannedTextItem didIFind = finder(child, id);
                if(didIFind!=null){
                    return didIFind;
                }
            }
        }

        return null;
    }
}
