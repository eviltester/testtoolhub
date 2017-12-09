package uk.co.compendiumdev.javafortesters.domain.counterstrings;

/**
 * Created by Alan on 12/08/2015.
 */
public class CounterStringRangeStruct {
    public final int spaceValueInRangeTakes;
    public final int minValInRange;
    public final int maxValInRange;

    public CounterStringRangeStruct(int space, int minX, int maxX) {
        this.spaceValueInRangeTakes = space;
        this.minValInRange = minX;
        this.maxValInRange = maxX;
    }

    //To iterate through a range without using the iterator
//                int outputValue = range.minValInRange;
//                while(outputValue<=range.maxValInRange){
//                    String entry = getCounterStringRepresentationOfNumber(outputValue, theSpacer);
//                    theCounterString.append(entry);
//                    outputValue+=range.spaceValueInRangeTakes;
//                }
}
