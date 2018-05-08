package uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators;

public class StringCounterStringCreator implements CounterStringCreator {

    private final StringBuilder string;

    public StringCounterStringCreator(){
        string = new StringBuilder();

    }

    @Override
    public void append(String nextPart) {
        string.append(nextPart);
    }

    @Override
    public void finished() {
        // string is ready to output now
    }

    @Override
    public String toString() {
        return string.toString();
    }
}
