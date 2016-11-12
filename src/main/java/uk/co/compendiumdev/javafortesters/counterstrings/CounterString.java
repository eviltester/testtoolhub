package uk.co.compendiumdev.javafortesters.counterstrings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan on 08/12/2014.
 */
public class CounterString {

    /*
        forward counterstring algorithm
        assumptions - blocks of numbers are lengths of strings
            35*  is length 3
                so 2 digit numbers are represented as 3 chars
            2*   is length 2
                so 1 digit numbers are represented as 2 chars


        if 35* then how many 3 digit strings are there?
                9
            why?
2*4*6*8*11*14*17*20*23*26*29*32*35*
            35 - 10 = 25
            25/3 = 8.33

            int(8.3) = 8 + 1 = 9 * 3 char numbers

            9 * 3 = 27
            35-27 = 8

            7 is not a 3 char number so lowest 3 char number is 8 + 3 = 11

            lowest 3 char number is 11
            so highest 2 char number is
            8 (11 - 3)

            with 1-9, when odd start with 1
                    , when even start with 2


        if 34* then
*3*5*7*10*13*16*19*22*25*28*31*34*
            9 3 chars
            34-10 - 24
            24/3 = 8

            int(8) = 8 + 1 = 9 * 3 char numbers
            3*9 = 27
            34-27 = 7
            7 is not a 3 char number so lowest 3 char number is 7 + 3 = 10


            lowest 3 char number is 10
            so highest 2 char number is
            7 (10 - 3)

            with 1-9, when odd start with 1
                    , when even start with 2


        if 33* then
*3*5*7*9*12*15*18*21*24*27*30*33*
            8 3 chars
            33-10 - 23
            23/3 = 7.

            int(7.) = 7 + 1 = 8 * 3 char numbers

            lowest 3 char number is 12
            3*8 - 24
            33 - 24 = 9
            9 is not a 3 char number so next 3 char number is 9+3 = 12


        if 10* then
            1 3 chars
            10-10 = 0
            0/3 = 0

        how many 2 chars?

        lowest 3 char number is 10
            so highest 2 char number is
            7 (10 - 3)

            with 1-9, when odd start with 1
                    , when even start with 2



     */

    public String create(int length) throws CounterStringCreationError {
        return create(length,"*");
    }

    public String getSingleCharSpacer(String limiter){
        // if we have been given a limiter then use the first character of it
        if(limiter!=null){
            if(limiter.length()>0) {
                return limiter.substring(0, 1);
            }
        }

        return "*";
    }

    public String create(int lengthOfCounterString, String limiter) throws CounterStringCreationError {

        String theSpacer = getSingleCharSpacer(limiter);

        StringBuilder theCounterString = new StringBuilder();
        List<CounterStringRangeStruct> ranges = createCounterStringRangesFor(lengthOfCounterString, theSpacer);

        try {

            // we know the size, so we could output to a a char array


            // iterate through the ranges to build the string
            for(CounterStringRangeStruct range : ranges){
                CounterStringRangeIterator ranger = new CounterStringRangeIterator(range);
                while(ranger.hasAnotherValueInRange()){
                    theCounterString.append(getCounterStringRepresentationOfNumber(ranger.getNextValueFromRange(), theSpacer));
                }

            }


        }catch(OutOfMemoryError e){
            throw new CounterStringCreationError("Sorry, OutOfMemory error creating string to memory, probably heap space, close down the app and try with a smaller length", null);
        }

        return theCounterString.toString();
    }

    public String getCounterStringRepresentationOfNumber(int outX, String theSpacer) {
        // if we are outputting the number 1 then output just the spacer
        if (outX == 1) {
            return theSpacer;
        }

        return String.valueOf(outX) + theSpacer;
    }

    // todo refactor below into CounterStringRangeCreator

    public List<CounterStringRangeStruct> createCounterStringRangesFor(int lengthOfCounterString, String limiter) {

        String theSpacer = getSingleCharSpacer(limiter);

        List<CounterStringRangeStruct> ranges = new ArrayList<CounterStringRangeStruct>();

        int maxNumberOfDigits = getLengthOfFinalCounterInString(lengthOfCounterString, theSpacer);


        // arrays to create the maximum and minimum numbers for the digit ranges
        // we ignore 0 because no number has 0 digits
        int[] maxNumberForDigits = new int[maxNumberOfDigits+1]; // ignore 0 indexing
        int[] minNumberForDigits = new int[maxNumberOfDigits+1];

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

            maxNumberForDigits[lengthOfNumberInString] = maxNumberWithXDigits;
            minNumberForDigits[lengthOfNumberInString] = lowestXCharNumberIs;
            lengthOfNumberInString--;

        } while (lengthOfNumberInString > 2);


        // for numbers 1-9, if we started with more than one digit number
        if(lengthOfCounterString>9) {
            maxNumberForDigits[lengthOfNumberInString] = highestNextNumberOfDigitsNumberIs;

            if ((highestNextNumberOfDigitsNumberIs % 2) == 1) { // odd
                minNumberForDigits[lengthOfNumberInString] = 1;
            } else {
                minNumberForDigits[lengthOfNumberInString] = 2;
            }
        }

        // tidy up the array of ranges into a list

        for (int x = 2; x <= maxNumberOfDigits; x++) {
            int minX = minNumberForDigits[x];
            int maxX = maxNumberForDigits[x];
            int space = x;
            ranges.add(new CounterStringRangeStruct(space, minX, maxX));
        }

        return ranges;
    }



    private int getLengthOfFinalCounterInString(int valueOfLastCounter, String theSpacer) {
        String numberOfDigitsExample = String.valueOf(valueOfLastCounter) + theSpacer;
        return numberOfDigitsExample.length();
    }
}
