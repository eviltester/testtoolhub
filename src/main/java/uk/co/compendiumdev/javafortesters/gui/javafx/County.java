package uk.co.compendiumdev.javafortesters.gui.javafx;

import uk.co.compendiumdev.javafortesters.counterstrings.CounterString;
import uk.co.compendiumdev.javafortesters.counterstrings.CounterStringRangeListIterator;
import uk.co.compendiumdev.javafortesters.counterstrings.CounterStringRangeStruct;

import java.awt.*;
import java.util.List;

public class County {

    private List<CounterStringRangeStruct> range;
    private CounterStringRangeListIterator ranger;
    private CounterString counterstring;
    private String spacer;
    private Robot robot;

    public void createCounterStringRangesFor(int counterStringLength, String spacer) {
        this.counterstring = new CounterString();
        this.spacer = counterstring.getSingleCharSpacer(spacer);
        this.range = counterstring.createCounterStringRangesFor(counterStringLength, spacer);
        this.ranger = new CounterStringRangeListIterator(range);
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public boolean hasAnotherValueInRangeList() {
        return this.ranger.hasAnotherValueInRangeList();
    }

    public String getNextCounterStringEntry() {
        return counterstring.getCounterStringRepresentationOfNumber(ranger.getNextCounterStringValue(), this.spacer);
    }

    public Robot getRobot(){
        return this.robot;
    }
}
