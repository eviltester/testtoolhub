package uk.co.compendiumdev.javafortesters.domain.html.comments;


import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HtmlCommentReaderTest {

    @Test
    public void givenAUrlWeCanReadContentsAndFindComments() throws IOException {

        String aURL = "https://developer.mozilla.org/en-US/docs/Web/Guide/HTML/The_Importance_of_Correct_HTML_Commenting";


        String pageBody = Request.Get(aURL)
                .execute().returnContent().asString();
        //String pageBody = get(aURL).andReturn().getBody().asString();

        HTMLCommentFinder comments = new HTMLCommentFinder(pageBody);
        comments.findAllComments();


    }

    @Test
    public void givenAUrlWeCanReadContentsAndFindCommentsViaReporter(){

        String aURL = "https://developer.mozilla.org/en-US/docs/Web/Guide/HTML/The_Importance_of_Correct_HTML_Commenting";

        HTMLCommentReporter report = new HTMLCommentReporter(aURL);

        System.out.println(report.commentsReport());


    }

    @Test
    public void givenAStringWithAnHTMLCommentWeRecogniseTheComment(){


        String hTMLCommentInString = "<!--Here is the comment-->";

        HTMLCommentFinder commentFinder = new HTMLCommentFinder(hTMLCommentInString);

        commentFinder.findAllComments();

        assertEquals(1, commentFinder.foundCount());
        assertEquals("Here is the comment", commentFinder.found(0));

    }

    @Test
    public void givenAMultiLineStringWithAnHTMLCommentWeRecogniseTheComment(){


        String hTMLCommentInString = "<!--Here \nis " +  "\n the \ncomment-->";

        HTMLCommentFinder commentFinder = new HTMLCommentFinder(hTMLCommentInString);

        commentFinder.findAllComments();

        assertEquals(1, commentFinder.foundCount());
        assertEquals("Here \nis \n the \ncomment", commentFinder.found(0));

    }
}
