package uk.co.compendiumdev.javafortesters.gui.javafx.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkChecker;


// https://www.safaribooksonline.com/library/view/learn-javafx-8/9781484211427/9781484211434_Ch27.xhtml
// based on PrimeFinderTask from Chapter 27 in Learn JavaFX 8
public class LinkCheckerNewTask extends Task<ObservableList<String>> {
    private LinkChecker linkChecker;
    private int count;

    public LinkCheckerNewTask(LinkChecker urlsToCheck) {
        count = urlsToCheck.getNumberInQueue();
        this.linkChecker = urlsToCheck;
    }

    @Override
    protected ObservableList<String> call() {
        // An observable list to represent the results
        final ObservableList<String> results =
                FXCollections.<String>observableArrayList();

// Update the title
        this.updateTitle("Link Checker Task");
        int counter=this.linkChecker.getCurrentLinkNumber();

        while(this.linkChecker.hasLinksToCheck()){

            if (this.isCancelled()) {
                break;
            }

            String msg = linkChecker.getAboutToCheckReport();
            results.add(msg);
            this.updateMessage(msg);

            msg = linkChecker.checkNextLink();
            //results.add(msg);
            this.updateMessage(msg);

            msg = linkChecker.getReportStateOfLinks();
            results.add(msg);
            //this.updateMessage(msg);

                updateValue(
                        FXCollections.<String>unmodifiableObservableList(
                                results));


            // Update the progress
            counter++;
            updateProgress(counter, count);

        }

        updateProgress(counter, count);
        return results;
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessage("Cancelled URL Checking");
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessage("The URL Checking failed.");
    }

    @Override
    public void succeeded() {
        super.succeeded();
        updateMessage("Checked All URLs - see report below.");
    }
}
