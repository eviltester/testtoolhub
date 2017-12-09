package uk.co.compendiumdev.javafortesters.domain.launcher;

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


}
