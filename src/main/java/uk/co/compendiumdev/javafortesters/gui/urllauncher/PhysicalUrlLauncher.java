package uk.co.compendiumdev.javafortesters.gui.urllauncher;

import uk.co.compendiumdev.javafortesters.launcher.LauncherUrl;
import uk.co.compendiumdev.javafortesters.launcher.LauncherUrlSet;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class PhysicalUrlLauncher {

    public static boolean launch(String aUrl) {

        if(!Desktop.isDesktopSupported()) return false;

        try {
            Desktop.getDesktop().browse(new URI(aUrl));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void launch(LauncherUrlSet aLauncherUrlSet){
        launch(aLauncherUrlSet.DEFAULT_MILLIS, aLauncherUrlSet.DEFAULT_RETRIES, aLauncherUrlSet);
    }

    public static void launch(int millisBetweenLaunches, int retries, LauncherUrlSet aLauncherUrlSet) {
        if(Desktop.isDesktopSupported()){
            for( Map.Entry<String, LauncherUrl> aUrl : aLauncherUrlSet.getUrls().entrySet()){

                for(int tries=0; tries<retries; tries++){

                    System.out.println("Opening " + aUrl.getKey());
                    boolean opened = launch(aUrl.getValue().getUrl());

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
}
