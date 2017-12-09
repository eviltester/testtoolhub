package uk.co.compendiumdev.javafortesters.domain.counterstrings;

import java.util.List;

public class County {

    private List<CounterStringRangeStruct> range;
    private CounterStringRangeListIterator ranger;
    private CounterString counterstring;
    private String spacer;

    public void createCounterStringRangesFor(int counterStringLength, String spacer) {
        this.counterstring = new CounterString();
        this.spacer = counterstring.getSingleCharSpacer(spacer);
        this.range = counterstring.createCounterStringRangesFor(counterStringLength, spacer);
        this.ranger = new CounterStringRangeListIterator(range);
    }

    public boolean hasAnotherValueInRangeList() {
        return this.ranger.hasAnotherValueInRangeList();
    }

    public String getNextCounterStringEntry() {
        return counterstring.getCounterStringRepresentationOfNumber(ranger.getNextCounterStringValue(), this.spacer);
    }

}
