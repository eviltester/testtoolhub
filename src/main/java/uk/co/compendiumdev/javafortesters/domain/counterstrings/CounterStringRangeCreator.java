package uk.co.compendiumdev.javafortesters.domain.counterstrings;

import java.util.ArrayList;
import java.util.List;

public class CounterStringRangeCreator {
    private final int lengthOfCounterString;
    private final String limiter;

    public CounterStringRangeCreator(int lengthOfCounterString, String limiter) {
        this.lengthOfCounterString = lengthOfCounterString;
        this.limiter = limiter;
    }

    public List<CounterStringRangeStruct> getListOfRangeStructs() {

        // assumes limiter is a single char
        String theSpacer = limiter; //getSingleCharSpacer(limiter);

        List<CounterStringRangeStruct> ranges = new ArrayList<CounterStringRangeStruct>();

        int maxNumberOfDigits = getLengthOfFinalCounterInString(lengthOfCounterString, theSpacer);


        int lengthOfNumberInString = maxNumberOfDigits;

        int highestNextNumberOfDigitsNumberIs=lengthOfCounterString;


        do {
            int maxNumberWithXDigits = highestNextNumberOfDigitsNumberIs;
            int numberHasXChars = String.valueOf(maxNumberWithXDigits).length();

            // length - lowest x digit number
            int differenceOfThisDigitValues = maxNumberWithXDigits - (int) Math.pow(10, (numberHasXChars - 1));
            int numberOfThisDigitValues = differenceOfThisDigitValues / lengthOfNumberInString;
            numberOfThisDigitValues++;

            int lowestXCharNumberIs = maxNumberWithXDigits - (lengthOfNumberInString * numberOfThisDigitValues);
            if(lowestXCharNumberIs==0){
                // if it is 0 then it is really 2 because we can't have 0 as the lowest number
                lowestXCharNumberIs=2;
            }

            int lengthOfLowestAsCounterString = (String.valueOf(lowestXCharNumberIs) + theSpacer).length();

            if (lengthOfLowestAsCounterString != lengthOfNumberInString) {
                //it is not an X char number so next X char number is lowest + lengthOfNumberInString
                lowestXCharNumberIs = lowestXCharNumberIs + lengthOfNumberInString;
            }

            highestNextNumberOfDigitsNumberIs = lowestXCharNumberIs - lengthOfNumberInString;

            // TODO: consider using a STACK rather than a list where we keep adding at the front of the list
            ranges.add(0, new CounterStringRangeStruct(lengthOfNumberInString, lowestXCharNumberIs, maxNumberWithXDigits));

            lengthOfNumberInString--;



        } while (lengthOfNumberInString > 2);


        // for numbers 1-9, if we started with more than one digit number
        if(lengthOfCounterString>9) {
            int minimumNumberIs=1;

            if ((highestNextNumberOfDigitsNumberIs % 2) == 1) { // odd
                minimumNumberIs=1;
            } else {
                minimumNumberIs=2;
            }

            ranges.add(0, new CounterStringRangeStruct(lengthOfNumberInString, minimumNumberIs, highestNextNumberOfDigitsNumberIs));
        }


        return ranges;
    }

    private int getLengthOfFinalCounterInString(int valueOfLastCounter, String theSpacer) {
        String numberOfDigitsExample = String.valueOf(valueOfLastCounter) + theSpacer;
        return numberOfDigitsExample.length();
    }
}
