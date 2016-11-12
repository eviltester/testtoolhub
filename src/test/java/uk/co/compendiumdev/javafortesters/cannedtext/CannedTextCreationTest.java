package uk.co.compendiumdev.javafortesters.cannedtext;

import uk.co.compendiumdev.javafortesters.tree.TreeBranch;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alan on 27/02/2015.
 * To allow creation and exploration of canned text functionality
 */
public class CannedTextCreationTest {

    @Test
    public void canCreateASimpleCannedTextItem(){
        CannedTextItem text = new CannedTextItem("0");
        Assert.assertEquals("0",text.getTextValue());
    }

    @Test
    public void canCreateASimpleCannedTextItemWithDefaultLabel(){
        CannedTextItem text = new CannedTextItem("defaultLabel");
        Assert.assertEquals("defaultLabel",text.getTextValue());
        Assert.assertEquals("defaultLabel",text.getLabel());
    }

    @Test
    public void canCreateCannedTextItemWithLabel(){
        CannedTextItem text = new CannedTextItem("number1","1");
        Assert.assertEquals("1",text.getTextValue());
        Assert.assertEquals("number1",text.getLabel());
    }

    @Test
    public void canCreateCannedTextItemsWithUniqueIDs(){
        CannedTextItem text1 = new CannedTextItem("number2","2");
        CannedTextItem text2 = new CannedTextItem("number3","3");
        Assert.assertTrue(text1.getId().length() > 0);
        Assert.assertTrue(text2.getId().length()>0);
        Assert.assertNotEquals(text1.getId(), text2.getId());
    }

    @Test
    public void canCreateATreeOfCannedTextItems(){

        TreeBranch<CannedTextItem> root = new TreeBranch<CannedTextItem>();

        Assert.assertFalse(root.isLeaf());
        Assert.assertEquals("branch", root.getLabel());
        root.setLabel("root");
        Assert.assertEquals("root", root.getLabel());

        TreeBranch<CannedTextItem> branch1 = root.addChild("branch1");
        Assert.assertEquals(1,root.countChildren());
        Assert.assertEquals("branch1", branch1.getLabel());
        Assert.assertEquals(0,branch1.countChildren());

        CannedTextItem canned = new CannedTextItem("canned","cannedTextValue");
        TreeBranch<CannedTextItem> cannedBranch = branch1.addChild(canned.getLabel(), canned);
        Assert.assertEquals(1,branch1.countChildren());
        Assert.assertEquals(0,cannedBranch.countChildren());
        Assert.assertTrue(cannedBranch.isLeaf());
        Assert.assertEquals("canned", cannedBranch.getLabel());

        CannedTextItem fromBranch;
        fromBranch = cannedBranch.getLeaf();
        Assert.assertEquals("canned", fromBranch.getLabel());
        Assert.assertEquals("cannedTextValue", fromBranch.getTextValue());

        System.out.println(root.toString());
    }

    @Test
    public void haveADefaultTreeOfCannedTextItems(){
        TreeBranch<CannedTextItem> root = CannedText.getDefaultTree();
        Assert.assertTrue(root.countChildren()>0);
        Assert.assertFalse(root.isLeaf());
        Assert.assertNotEquals("branch", root.getLabel());
        System.out.println(root.toString());
    }


    @Test
    public void canFindAnItemInATree(){
        TreeBranch<CannedTextItem> root = CannedText.createDefaultTree();
        Assert.assertTrue(root.countChildren()>0);
        Assert.assertFalse(root.isLeaf());
        Assert.assertNotEquals("branch", root.getLabel());
        System.out.println(root.toString());

        CannedTextItem canned = new CannedTextItem("findMe","find this canned text");
        TreeBranch<CannedTextItem> cannedBranch = root.addChild(canned.getLabel(), canned);

        CannedTextItemTreeFinder finder = new CannedTextItemTreeFinder(root);
        CannedTextItem foundItem = finder.find(canned.getId());

        Assert.assertNotNull(foundItem);
        Assert.assertEquals(canned.getId(), foundItem.getId());
    }


    @Test
    public void canNotFindAnItemInATreeReturnsNull(){
        TreeBranch<CannedTextItem> root = CannedText.getDefaultTree();
        Assert.assertTrue(root.countChildren()>0);
        Assert.assertFalse(root.isLeaf());
        Assert.assertNotEquals("branch", root.getLabel());
        System.out.println(root.toString());

        CannedTextItemTreeFinder finder = new CannedTextItemTreeFinder(root);
        CannedTextItem foundItem = finder.find("nonexistantid");

        Assert.assertNull(foundItem);
    }


    @Test
    public void canCountTabs(){
        Assert.assertEquals(0,CannedText.countLineTabs(""));
        Assert.assertEquals(0,CannedText.countLineTabs("noTabs"));
        Assert.assertEquals(1,CannedText.countLineTabs("\tnoTabs"));
        Assert.assertEquals(2,CannedText.countLineTabs("\t\tnoTabs"));
    }

    @Test
    public void canCreateCannedTextTreeFromString(){

        String newLine = System.getProperty("line.separator");

        TreeBranch<CannedTextItem> tree = CannedText.createCannedTextTree("#comment"+newLine+"aRoot");

        Assert.assertNotNull(tree);
        Assert.assertEquals(0,tree.countChildren());


        //#comment
        //aRoot
        //  numbers
        tree = null;
        tree = CannedText.createCannedTextTree( "#comment"+newLine+
                                                "aRoot"+newLine+
                                                "\tnumbers"+newLine
                                              );

        Assert.assertNotNull(tree);
        Assert.assertEquals(1,tree.countChildren());
        System.out.println(tree);




        //#comment
        //aRoot
        //  numbers
        //    0 = 0
        tree = null;
        tree = CannedText.createCannedTextTree( "#comment"+newLine+
                        "aRoot" + newLine+
                        "\tnumbers" + newLine+
                        "\t\t0 = 0" + newLine +
                        "\t\t127 Signed Char MAX = 127" + newLine +
                        "\tcharacters" + newLine +
                        "\t\tempty = " + newLine +
                        "\t\tnonempty = last child" + newLine
        );

        Assert.assertNotNull(tree);
        Assert.assertEquals(2,tree.countChildren());
        System.out.println(tree);

    }
}
