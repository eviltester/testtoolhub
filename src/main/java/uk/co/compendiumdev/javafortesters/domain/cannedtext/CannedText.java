    package uk.co.compendiumdev.javafortesters.domain.cannedtext;

    import uk.co.compendiumdev.javafortesters.files.InputStreamReaderUtils;
    import uk.co.compendiumdev.javafortesters.domain.tree.TreeBranch;

    import java.io.*;
    import java.util.ArrayList;

    /**
 * Created by Alan on 27/02/2015.
 */
public class CannedText {
    private static TreeBranch<CannedTextItem> defaultTree;

    public static TreeBranch<CannedTextItem> getDefaultTree() {
        TreeBranch<CannedTextItem> root = null;

        // does a resource file exist?
        if(CannedText.class.getResource("defaultCannedText.txt")!=null){

            InputStream defaultStream = CannedText.class.getResourceAsStream("defaultCannedText.txt");

            try {
                String cannedText = InputStreamReaderUtils.stringFromStream(defaultStream);
                defaultTree = createCannedTextTree(cannedText);
                root = defaultTree;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(root == null) {
            return createDefaultTree();
        }else{
            return defaultTree;
        }

    }

        public static TreeBranch<CannedTextItem> createCannedTextTree(String cannedText) {
            // given a string, parse it and create a tree
            String newLine = System.getProperty("line.separator");
            String[] treeDefn = cannedText.split(newLine);

            ArrayList<String> readTreeBranches = new ArrayList<>();
            ArrayList<TreeBranch<CannedTextItem>> readTreeBranchesInstantiated = new ArrayList<>();

            TreeBranch<CannedTextItem> root = null;

            for(String treeBranch : treeDefn){

                if(treeBranch.startsWith("#")){
                    // ignore
                }else{

                    int tabs = countLineTabs(treeBranch);

                    if(tabs==0){
                        //it is a root
                        if(root==null){
                            root = new TreeBranch<CannedTextItem>(treeBranch);
                            readTreeBranches.add(treeBranch);
                            readTreeBranchesInstantiated.add(root);
                        }else{
                            System.out.println("Found more than one root");
                            System.out.println("Ignoring root");
                        }
                    }else{
                        // need to add a branch
                        String branchText = treeBranch.trim();
                        TreeBranch<CannedTextItem> lastBranch;
                        TreeBranch<CannedTextItem> newBranch;

                        if(tabs>=readTreeBranches.size()){
                            // we have a new branch
                            lastBranch = readTreeBranchesInstantiated.get(readTreeBranchesInstantiated.size() - 1);

                        }else{
                            // we need to unwind and find the correct parent to add to
                            // parent is tabs-1
                            while(readTreeBranches.size() > tabs){
                                readTreeBranches.remove(tabs);
                                readTreeBranchesInstantiated.remove(tabs);
                            }
                            lastBranch = readTreeBranchesInstantiated.get(tabs - 1);
                        }

                        if(treeBranch.contains("=")){
                            // it is a leaf for clipping
                            String[] labelClipText = branchText.split("=");
                            labelClipText[0] = labelClipText[0].trim();
                            String clipValue = "";
                            if(labelClipText.length>1) {
                                clipValue = labelClipText[1].trim();
                            }


                            newBranch = lastBranch.addChild(labelClipText[0], new CannedTextItem(labelClipText[0], clipValue));


                        }else{
                            // last branch
                            newBranch = lastBranch.addChild(branchText);
                        }

                        readTreeBranches.add(branchText);
                        readTreeBranchesInstantiated.add(newBranch);
                    }

                }
            }

            return root;
        }

        public static int countLineTabs(String treeBranch) {
            String[] tabs = treeBranch.split("\t");
            return tabs.length-1;
        }


        public static TreeBranch<CannedTextItem> createDefaultTree() {
        TreeBranch<CannedTextItem> root = new TreeBranch<CannedTextItem>();

        root.setLabel("Canned Text");

        TreeBranch numbers = root.addChild("numbers");

        addCannedTextTo(numbers, "0");

        // https://msdn.microsoft.com/en-gb/library/296az74e.aspx
        addCannedTextTo(numbers, "127", "127 Signed Char MAX");
        addCannedTextTo(numbers, "128", "127+1 Signed Char MAX+1");
        addCannedTextTo(numbers, "255", "255 Unsigned Char MAX");
        addCannedTextTo(numbers, "256", "255 Unsigned Char MAX+1");
        addCannedTextTo(numbers, "32767", "32767 short MAX");
        addCannedTextTo(numbers, "32768", "32768 short MAX + 1");

        return root;
    }



    private static void addCannedTextTo(TreeBranch parent, String labelValue) {
        addCannedTextTo(parent, labelValue, labelValue);
    }

    private static void addCannedTextTo(TreeBranch parent, String value, String label) {
        parent.addChild(label, new CannedTextItem(label,value));
    }


}
