package uk.co.compendiumdev.javafortesters.gui.javafx.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkChecker;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkQueue;


// https://www.safaribooksonline.com/library/view/learn-javafx-8/9781484211427/9781484211434_Ch27.xhtml
// based on PrimeFinderTask from Chapter 27 in Learn JavaFX 8
public class LinkCheckerNewTask extends Task<ObservableList<String>> {
    private LinkChecker linkChecker;
    private int count;

    public LinkCheckerNewTask(LinkQueue urlsToCheck) {
        count = urlsToCheck.numberInQueue();
        this.linkChecker = new LinkChecker(urlsToCheck);
    }

    public LinkCheckerNewTask() {

    }

    // The task implementation
    @Override
    protected ObservableList<String> call() {
// An observable list to represent the results
        final ObservableList<String> results =
                FXCollections.<String>observableArrayList();

// Update the title
        this.updateTitle("Link Checker Task");
        int counter=0;

        while(this.linkChecker.hasLinksToCheck()){

            if (this.isCancelled()) {
                break;
            }

            String msg = linkChecker.getAboutToCheckReport();
            results.add(msg);
            this.updateMessage(msg);

            msg = linkChecker.checkNextLink();
            results.add(msg);
            this.updateMessage(msg);

            msg = linkChecker.getReportStateOfLinks();
            results.add(msg);
            this.updateMessage(msg);

                updateValue(
                        FXCollections.<String>unmodifiableObservableList(
                                results));


// Update the progress
            updateProgress(counter, count);
        }

        return results;
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessage("The task was cancelled.");
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessage("The task failed.");
    }

    @Override
    public void succeeded() {
        super.succeeded();
        updateMessage("The task finished successfully.");
    }
}
