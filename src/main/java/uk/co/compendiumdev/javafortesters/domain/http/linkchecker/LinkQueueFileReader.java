package uk.co.compendiumdev.javafortesters.domain.http.linkchecker;

import java.io.*;

public class LinkQueueFileReader {

    LinkQueue queue;

    public LinkQueueFileReader(File linkQueueFile) {

        queue = new LinkQueue();

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(linkQueueFile));

            String aURL = fileReader.readLine();
            while(aURL!=null){
                queue.add(aURL);
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
