package uk.co.compendiumdev.javafortesters.domain.tree;

public class TreeKeyValue {
    public String key;
    public String value;

    public TreeKeyValue(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String toString(){
        return this.value;
    }
}
