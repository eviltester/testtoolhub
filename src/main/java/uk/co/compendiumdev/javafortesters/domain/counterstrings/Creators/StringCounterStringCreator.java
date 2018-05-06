package uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators;

import uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators.CounterStringCreator;

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
    public String toString() {
        return string.toString();
    }
}
