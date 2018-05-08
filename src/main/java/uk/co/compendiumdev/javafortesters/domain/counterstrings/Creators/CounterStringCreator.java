package uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators;

public interface CounterStringCreator {
    void append(String nextPart);
    void finished();
}
