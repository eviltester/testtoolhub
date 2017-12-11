package uk.co.compendiumdev.javafortesters.domain.http.linkchecker;

public class SystemOutOutputTo implements OutputTo {
    @Override
    public void append(String text) {
        System.out.println(text);
    }

    @Override
    public void output(String text) {
        // should really clear
        System.out.println(text);
    }
}
