package uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators;

public class SystemOutCounterStringCreator implements CounterStringCreator {

    private final StringBuilder string;

    public SystemOutCounterStringCreator(){
        string = new StringBuilder();
    }
    @Override
    public void append(String nextPart) {
        string.append(nextPart);
    }

    @Override
    public void finished() {
        System.out.print(string.toString());
    }

    @Override
    public String toString() {
        return string.toString();
    }


}
