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

            readBufferedReaderAsLinkQueue(fileReader, linkQueueFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong reading file " + linkQueueFile.getAbsolutePath());
        }
    }

    public LinkQueueFileReader(InputStream fileToRead, String fileName) {

        queue = new LinkQueue();

        BufferedReader reader = new BufferedReader(new InputStreamReader(fileToRead));
        readBufferedReaderAsLinkQueue(reader, fileName);
    }

    private void readBufferedReaderAsLinkQueue(BufferedReader reader, String fileName) {
        try{
            String aURL = reader.readLine();
            while(aURL!=null){
                try{
                    URL anActualURL = new URL(aURL);
                    queue.add(aURL);
                }catch(MalformedURLException e){
                    System.out.println("Ignoring non-url line");
                    System.out.println(aURL);
                }
                aURL = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Something went wrong creating reader for file " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong reading file " + fileName);
        }
    }

    public LinkQueue getQueue() {
        return queue;
    }
}
