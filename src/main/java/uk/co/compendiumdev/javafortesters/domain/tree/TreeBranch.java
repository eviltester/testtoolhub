package uk.co.compendiumdev.javafortesters.domain.tree;

import java.util.ArrayList;

/**
 * Created by Alan on 27/02/2015.
 *
 * Generic structure to create a tree
 */
public class TreeBranch<T> {
    private final ArrayList<TreeBranch> children;
    private T leaf;
    private String label;

    public TreeBranch(){
        this("branch");
    }

    public TreeBranch(String label){
        this(label, null);
    }

    public TreeBranch(String label, T entity) {
        this.leaf = entity;
        this.label = label;
        this.children = new ArrayList<TreeBranch>();
    }

    public boolean isLeaf() {
        return leaf!=null;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TreeBranch addChild(String branchLabel) {
        TreeBranch<T> child = new TreeBranch<T>(branchLabel);
        children.add(child);
        return child;
    }

    public TreeBranch addChild(String branchLabel, T entity){
        TreeBranch<T> child = new TreeBranch<T>(branchLabel, entity);
        children.add(child);
        return child;
    }

    public int countChildren() {
        return children.size();
    }

    public T getLeaf() {
        return leaf;
    }

    public String toString(){
        return stringify(0);

    }

    private String stringify(int indent) {
        StringBuilder out = new StringBuilder();

        for(int i=0; i<indent; i++){
            out.append(" ");
        }

        out.append(label).append("\n");

        for(TreeBranch child : children){
            out.append(child.stringify(indent+1));
        }

        return out.toString();
    }


    public ArrayList<TreeBranch> getChildren() {
        return children;
    }

    public void addChild(TreeBranch<T> lastBranch) {
        children.add(lastBranch);
    }
}
