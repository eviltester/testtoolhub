package uk.co.compendiumdev.javafortesters.domain.http.linkchecker;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LinkQueue {

    List<LinkToCheck> queue = new ArrayList<>();

    public int numberInQueue() {
        return queue.size();
    }

    public void add(String aUrl) {
        queue.add(new LinkToCheck(aUrl));
    }

    public LinkToCheck getLink(int index) {
        return queue.get(index);
    }

    public Collection<LinkToCheck> getLinks() {
        return queue;
    }

    public void resetStates() {

        for(LinkToCheck link : queue){
            link.setUnknown();
        }

    }
}
