package uk.co.compendiumdev.javafortesters.launcher;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alan on 10/03/15.
 */
public class LauncherUrlSet {
    private static final int DEFAULT_MILLIS = 1000;
    private static final int DEFAULT_RETRIES = 3;
    private String name;
    private Map<String, LauncherUrl> urls;

    public LauncherUrlSet(String name) {
        this.name = name;
        this.urls = new HashMap<String, LauncherUrl>();
    }

    public void add(LauncherUrl aUrl) {
        urls.put(aUrl.getName(), aUrl);
    }

    public void launch(){
        launch(DEFAULT_MILLIS, DEFAULT_RETRIES);
    }

    public void launch(int millisBetweenLaunches, int retries) {
        if(Desktop.isDesktopSupported()){
            for( Map.Entry<String, LauncherUrl> aUrl : this.urls.entrySet()){

                for(int tries=0; tries<retries; tries++){

                    System.out.println("Opening " + aUrl.getKey());
                    boolean opened = aUrl.getValue().launch();

                    try {
                        Thread.sleep(millisBetweenLaunches);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(opened){
                        break;
                    }
                }
            }
        }else{
            System.out.println("Cannot launch, desktop launching not supported by JVM");
        }
    }

    public String getName() {
        return name;
    }

    public Map<String, LauncherUrl> getUrls(){
        return urls;
    }
}
