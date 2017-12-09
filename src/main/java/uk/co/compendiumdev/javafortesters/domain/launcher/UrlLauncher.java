package uk.co.compendiumdev.javafortesters.domain.launcher;

import java.util.*;

/**
 * Created by alan on 10/03/15.
 */
public class UrlLauncher {
    private Map<String, LauncherUrlSet> urlSets;

    public UrlLauncher(){
        this.urlSets = new HashMap<String, LauncherUrlSet>();
    }

    public void add(LauncherUrlSet urlSet) {
        this.urlSets.put(urlSet.getName(), urlSet);
    }

    public Set<String> getSetNames(){
        return urlSets.keySet();
    }

    public Collection<LauncherUrlSet> getSets(){
        return urlSets.values();
    }

    public LauncherUrlSet getSet(String setName){
        return urlSets.get(setName);
    }

}
