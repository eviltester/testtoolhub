package uk.co.compendiumdev.javafortesters.domain.html.comments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alan on 24/03/2015.
 */
public class HTMLCommentFinder {
    private final String commentedSource;
    private int foundCount;
    List<String> found = new ArrayList<String>();

    public HTMLCommentFinder(String stringToParse) {
        this.commentedSource = stringToParse;
        foundCount = 0;
    }

    public HTMLCommentFinder findAllComments() {

        // http://blog.ostermiller.org/find-comments-html

        String commentRegex = "\\<![ \\r\\n\\t]*(--(([^\\-]|[\\r\\n]|-[^\\-])*)--[ \\r\\n\\t]*)\\>";

        Pattern commentRegexPattern = Pattern.compile(commentRegex);

        Matcher matcher = commentRegexPattern.matcher(commentedSource);

        foundCount = 0;
        while(matcher.find()){

            System.out.println("found: " + matcher.group(2));
            found.add(matcher.group(2));

            foundCount++;
        }

        return this;

    }

    public int foundCount() {
        return this.foundCount;
    }

    public String found(int index) {
        if(index<this.foundCount)
            return found.get(index);
        else
            return "";
    }
}
