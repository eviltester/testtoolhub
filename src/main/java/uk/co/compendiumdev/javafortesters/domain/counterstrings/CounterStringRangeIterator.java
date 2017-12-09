package uk.co.compendiumdev.javafortesters.domain.counterstrings;

public class CounterStringRangeIterator {
    private final CounterStringRangeStruct range;
    private int nextValueFromRange;

    public CounterStringRangeIterator(CounterStringRangeStruct range) {
        this.range = range;
        this.nextValueFromRange = range.minValInRange;
    }

    public boolean hasAnotherValueInRange() {
        return (this.nextValueFromRange <=range.maxValInRange);
    }

    public int getNextValueFromRange() {

        int retVal = this.nextValueFromRange;

        if(this.nextValueFromRange > range.maxValInRange){
            throw new IndexOutOfBoundsException("Tried to exceed range limit of " + range.maxValInRange + " by using " + this.nextValueFromRange);
        }

        this.nextValueFromRange +=range.spaceValueInRangeTakes;

        return retVal;
    }
}
