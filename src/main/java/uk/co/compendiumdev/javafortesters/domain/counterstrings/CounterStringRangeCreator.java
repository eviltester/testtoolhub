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


        // arrays to create the maximum and minimum numbers for the digit ranges
        // we ignore 0 because no number has 0 digits
//        int[] maxNumberForDigits = new int[maxNumberOfDigits+1]; // ignore 0 indexing
//        int[] minNumberForDigits = new int[maxNumberOfDigits+1];

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

            // add directly into the list rather than collate in an array
            ranges.add(0, new CounterStringRangeStruct(lengthOfNumberInString, lowestXCharNumberIs, maxNumberWithXDigits));

//            maxNumberForDigits[lengthOfNumberInString] = maxNumberWithXDigits;
//            minNumberForDigits[lengthOfNumberInString] = lowestXCharNumberIs;
            lengthOfNumberInString--;



        } while (lengthOfNumberInString > 2);


        // for numbers 1-9, if we started with more than one digit number
        if(lengthOfCounterString>9) {
            //maxNumberForDigits[lengthOfNumberInString] = highestNextNumberOfDigitsNumberIs;
            int minimumNumberIs=1;

            if ((highestNextNumberOfDigitsNumberIs % 2) == 1) { // odd
//                minNumberForDigits[lengthOfNumberInString] = 1;
                minimumNumberIs=1;
            } else {
//                minNumberForDigits[lengthOfNumberInString] = 2;
                minimumNumberIs=2;
            }

            ranges.add(0, new CounterStringRangeStruct(lengthOfNumberInString, minimumNumberIs, highestNextNumberOfDigitsNumberIs));
        }

        // tidy up the array of ranges into a list

//        List<CounterStringRangeStruct> ranges2 = new ArrayList<CounterStringRangeStruct>();
//        for (int x = 2; x <= maxNumberOfDigits; x++) {
//            int minX = minNumberForDigits[x];
//            int maxX = maxNumberForDigits[x];
//            int space = x;
//            ranges2.add(new CounterStringRangeStruct(space, minX, maxX));
//        }

        return ranges;
    }

    private int getLengthOfFinalCounterInString(int valueOfLastCounter, String theSpacer) {
        String numberOfDigitsExample = String.valueOf(valueOfLastCounter) + theSpacer;
        return numberOfDigitsExample.length();
    }
}
