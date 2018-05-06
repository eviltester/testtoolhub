package uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators;

import uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators.CounterStringCreator;

public class SystemOutCounterStringCreator implements CounterStringCreator {

    private final StringBuilder string;

    public SystemOutCounterStringCreator(){
        string = new StringBuilder();
    }
    @Override
    public void append(String nextPart) {
        System.out.print(nextPart);
        string.append(nextPart);
    }

    @Override
    public String toString() {
        return string.toString();
    }
}
