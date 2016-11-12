package uk.co.compendiumdev.javafortesters.launcher;

import org.junit.Test;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

/**
 * Created by alan on 09/03/15.
 */
public class UrlLauncherTest {

    // Todo, need assertions and mocks etc. in these tests as url launching not tested by this

    @Test
    public void canLaunchUrlsFromPropertiesFileInConfigFolderToBrowser() throws URISyntaxException, IOException, InterruptedException {

        LauncherUrlLoader loader = new LauncherUrlLoader();
        UrlLauncher urls = loader.load();

        //urls.launch();

    }

    @Test
    public void canCreateAContainerForUrls(){

        List<LauncherUrlSet> urlsets = new ArrayList<LauncherUrlSet>();

        LauncherUrlSet urlset = new LauncherUrlSet("default");
        LauncherUrl eviltester = new LauncherUrl("eviltester","http://www.eviltester.com");
        urlset.add(eviltester);
        urlset.add(new LauncherUrl("javafortesters", "http://www.javafortesters.com"));

        //eviltester.launch();

        //urlset.launch();

    }
}
