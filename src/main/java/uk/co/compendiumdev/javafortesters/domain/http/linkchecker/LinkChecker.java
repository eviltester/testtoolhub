package uk.co.compendiumdev.javafortesters.domain.http.linkchecker;

import uk.co.compendiumdev.javafortesters.domain.http.HttpRequestSender;
import uk.co.compendiumdev.javafortesters.domain.http.HttpResponse;

import java.net.MalformedURLException;
import java.net.URL;

public class LinkChecker {
    private final LinkQueue links;

    public LinkChecker(LinkQueue links) {
        this.links = links;
    }

    public void reportStateOfLinks() {

        System.out.println("Link Checker Report" + System.lineSeparator());
        System.out.println("-------------------"  + System.lineSeparator());

        for(LinkToCheck aLink : links.queue){
            System.out.println(String.format("- %s %s %n", aLink.getUrl(), aLink.exists())) ;
        }
    }


    public String getReportStateOfLinks() {

        StringBuilder report = new StringBuilder();

        report.append(String.format("Link Checker Report%n"));
        report.append(String.format("-------------------%n"));

        for(LinkToCheck aLink : links.queue){
            report.append(String.format("- %s %s%n", aLink.getUrl(), aLink.exists())) ;
        }

        return report.toString();
    }



    private int checkLink(String aUrl) {

        URL url = null;
        try {
            url = new URL(aUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("The URL String is not valid " + aUrl);
            return 0;
        }

        HttpRequestSender request = HttpRequestSender.getSender();


        HttpResponse response = request.send(url,"GET");

       return response.statusCode;
    }


    public void checkLinksReportingAsWeGo(OutputTo anOutputter) {

                /*
        - make the `checkLinks()` method output to the console (System.out.println) as it executes
        e.g.

        ~~~~~~~~

                Link Checker Report
        -------------------
                - http://javafortesters.com UNKNOWN
        - http://eviltester.com UNKNOWN
        - http://eviltester.com/thispagedoesnotexist UNKNOWN
        - http://compendiumdev.co.uk UNKNOWN

            ** CHECKING ** http://javafortesters.com
            ** RESULT ** EXISTS

        Link Checker Report
                -------------------
                - http://javafortesters.com EXISTS
        - http://eviltester.com UNKNOWN
        - http://eviltester.com/thispagedoesnotexist UNKNOWN
        - http://compendiumdev.co.uk UNKNOWN

            ** CHECKING ** http://eviltester.com
            ... etc.

        ~~~~~~~~
                */

        for(LinkToCheck aLink : links.queue){
            anOutputter.append(String.format("** CHECKING ** %s", aLink.getUrl()));
            aLink.setExistsFromStatus(checkLink(aLink.getUrl()));
            anOutputter.append(String.format("** RESULT ** %s", aLink.getState()));
            anOutputter.append(getReportStateOfLinks());

        }


    }
    public void checkLinksReportingAsWeGo() {
        checkLinksReportingAsWeGo(new SystemOutOutputTo());
    }
}
