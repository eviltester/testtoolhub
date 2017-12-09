package uk.co.compendiumdev.javafortesters.gui.urllauncher;

import uk.co.compendiumdev.javafortesters.domain.launcher.LauncherUrl;
import uk.co.compendiumdev.javafortesters.domain.launcher.LauncherUrlSet;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class PhysicalUrlLauncher {

    public static boolean canLaunch(){
        return Desktop.isDesktopSupported();
    }

    public static boolean launch(String aUrl) {

        if(!canLaunch()) return false;

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

        if(!canLaunch()) {
            System.out.println("Cannot launch, desktop launching not supported by JVM");
            return;
        }

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
    }
}
