package uk.co.compendiumdev.javafortesters.domain.http.linkchecker;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class LinkQueueFileReader {

    LinkQueue queue;

    public LinkQueueFileReader(File linkQueueFile) {

        queue = new LinkQueue();

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(linkQueueFile));

            String aURL = fileReader.readLine();
            while(aURL!=null){
                try{
                    URL anActualURL = new URL(aURL);
                    queue.add(aURL);
                }catch(MalformedURLException e){
                    System.out.println("Ignoring non-url line");
                    System.out.println(aURL);
                }
                aURL = fileReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Something went wrong creating reader for file " + linkQueueFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong reading file " + linkQueueFile.getAbsolutePath());
        }
    }

    public LinkQueue getQueue() {
        return queue;
    }
}
