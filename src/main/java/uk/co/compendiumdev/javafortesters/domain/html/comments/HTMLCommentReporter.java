package uk.co.compendiumdev.javafortesters.domain.html.comments;


import org.apache.http.client.fluent.Request;

import java.io.IOException;


public class HTMLCommentReporter {

    HTMLCommentFinder comments;

    public HTMLCommentReporter(String aURL) {

        String pageBody ="";

        try {
            pageBody = Request.Get(aURL)
                    .execute().returnContent().asString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //String pageBody = get(aURL).andReturn().getBody().asString();

        comments = new HTMLCommentFinder(pageBody);
        comments.findAllComments();

    }

    public String commentsReport() {

        String report = "";

        if (comments.foundCount() > 1){
            for (int getComment = 0; getComment < comments.foundCount(); getComment++) {
                report += comments.found(getComment) + "\n";
                report += "===========================\n";
            }
        }
            return report;
    }
}
