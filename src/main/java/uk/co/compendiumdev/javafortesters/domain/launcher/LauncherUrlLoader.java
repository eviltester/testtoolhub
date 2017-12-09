package uk.co.compendiumdev.javafortesters.domain.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class LauncherUrlLoader {

    public UrlLauncher load() {

        File propertiesLocation = new File(System.getProperty("user.dir"),"config/launcher");

        File[] files = propertiesLocation.listFiles();
        UrlLauncher urlSets = new UrlLauncher();

        //if there are no files then load the default
        if(files==null || files.length == 0){

            Properties urls = new Properties();
            try {
                urls.load(UrlLauncher.class.getResourceAsStream("default.txt"));

                LauncherUrlSet urlSet = new LauncherUrlSet("default");
                addPropertiesToUrlSet(urls, urlSet);
                urlSets.add(urlSet);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not load default urls");
            }

        }else {

            for (File aUrlFile : files) {
                Properties urls = new Properties();

                try {
                    urls.load(new FileInputStream(aUrlFile));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error loading " + aUrlFile.getAbsolutePath());
                }

                LauncherUrlSet urlSet = new LauncherUrlSet(aUrlFile.getName().replace(".txt", "").replace(".properties", "").replace("_", " "));

                addPropertiesToUrlSet(urls, urlSet);

                urlSets.add(urlSet);
            }

        }
        return urlSets;
    }

    private void addPropertiesToUrlSet(Properties urls, LauncherUrlSet urlSet) {

        for (Map.Entry<Object, Object> url : urls.entrySet()) {

            LauncherUrl aUrl = new LauncherUrl(((String) url.getKey()).replace("_", " "), (String) url.getValue());
            urlSet.add(aUrl);
        }
    }
}
