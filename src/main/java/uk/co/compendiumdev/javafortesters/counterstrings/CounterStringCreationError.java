package uk.co.compendiumdev.javafortesters.counterstrings;

public class CounterStringCreationError extends Throwable {
    public CounterStringCreationError(String msg, Exception e) {
        super(msg, e);
    }
}
