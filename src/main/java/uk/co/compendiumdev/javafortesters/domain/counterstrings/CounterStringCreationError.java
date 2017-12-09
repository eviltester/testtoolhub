package uk.co.compendiumdev.javafortesters.domain.counterstrings;

public class CounterStringCreationError extends Throwable {
    public CounterStringCreationError(String msg, Exception e) {
        super(msg, e);
    }
}
