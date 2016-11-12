package uk.co.compendiumdev.javafortesters.launcher;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by alan on 10/03/15.
 */
public class LauncherUrl {
    private final String name;
    private final String url;

    public LauncherUrl(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl(){
        return url;
    }

    public boolean launch() {

        if(!Desktop.isDesktopSupported()) return false;

        try {
            Desktop.getDesktop().browse(new URI(this.url));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
}
