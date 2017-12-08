package uk.co.compendiumdev.javafortesters.launcher;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LauncherUrlSet {
    public static final int DEFAULT_MILLIS = 1000;
    public static final int DEFAULT_RETRIES = 3;
    private String name;
    private Map<String, LauncherUrl> urls;

    public LauncherUrlSet(String name) {
        this.name = name;
        this.urls = new HashMap<String, LauncherUrl>();
    }

    public void add(LauncherUrl aUrl) {
        urls.put(aUrl.getName(), aUrl);
    }



    public String getName() {
        return name;
    }

    public Map<String, LauncherUrl> getUrls(){
        return urls;
    }
}
