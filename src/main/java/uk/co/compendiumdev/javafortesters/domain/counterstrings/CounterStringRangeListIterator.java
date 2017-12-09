package uk.co.compendiumdev.javafortesters.domain.counterstrings;

import java.util.List;

/**
 * Created by Alan on 13/08/2015.
 */
public class CounterStringRangeListIterator {
    private final List<CounterStringRangeStruct> ranges;
    private CounterStringRangeStruct nextRange;
    private CounterStringRangeIterator ranger;
    private int nextRangeIndex;

    public CounterStringRangeListIterator(List<CounterStringRangeStruct> ranges) {
        this.ranges = ranges;
        this.nextRangeIndex = 0;
        this.nextRange = ranges.get(this.nextRangeIndex);

        this.ranger = new CounterStringRangeIterator(this.nextRange);
    }

    public boolean hasAnotherValueInRangeList() {
        // if anything in current range then there is another value
        if(this.ranger.hasAnotherValueInRange()) {
            return true;
        }else{
            // if nothing in this range then go to next range
            this.nextRangeIndex++;
            if(this.nextRangeIndex < this.ranges.size()){
                this.nextRange = ranges.get(this.nextRangeIndex);
                this.ranger = new CounterStringRangeIterator(this.nextRange);
                return true;
            }
        }

        // otherwise nothing left
        return false;
    }

    public int getNextCounterStringValue(){
        return this.ranger.getNextValueFromRange();
    }


}
