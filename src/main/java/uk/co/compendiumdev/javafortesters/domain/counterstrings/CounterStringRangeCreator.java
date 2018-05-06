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

        int maxNumberOfDigits = getLengthOfNumberInString(lengthOfCounterString, theSpacer);


        int lengthOfNumberInString = maxNumberOfDigits;

        int highestNextNumberOfDigitsNumberIs=lengthOfCounterString;


        do {
            int maxNumberWithXDigits = highestNextNumberOfDigitsNumberIs;


            int lowestXCharNumberIs = calculateLowestXDigitNumberForThisRange(highestNextNumberOfDigitsNumberIs, theSpacer);

            // TODO: consider using a STACK rather than a list where we keep adding at the front of the list
            ranges.add(0, new CounterStringRangeStruct(lengthOfNumberInString, lowestXCharNumberIs, maxNumberWithXDigits));

            highestNextNumberOfDigitsNumberIs = lowestXCharNumberIs - lengthOfNumberInString;

            lengthOfNumberInString--;



        } while (lengthOfNumberInString > 1);



        return ranges;
    }


    private int calculateLowestXDigitNumberForThisRange(int maxNumberWithXDigits, String spacer) {

        int displayLengthInStringWithLimiterAppended = getLengthOfNumberInString(maxNumberWithXDigits, spacer);

        //*3*5*7*10*13*16*19*22*25*28*31*34*37*40*43*46*49*52*55*58*61*64*67*70*73*76*79*82*85*88*91*94*97*101*105*109*113*117*121*125*
        // e.g. 125 has 3 chars
        // and display length with limiter would be 4 i.e. 125*
        int numberHasXChars = String.valueOf(maxNumberWithXDigits).length();

        // length - lowest x digit number
        // lowest 2 digit number is 10 to the power of (2-1) = 10
        // e.g. lowest 3 digit number is 10 to the power of (3-1) = 100
        int lowestXDigitNumber = (int) Math.pow(10, (numberHasXChars - 1));

        // what is the gap between our highest range number and the lowest in the range?
        // e.g. 125 - 100 = 25
        int differenceOfThisDigitValues = maxNumberWithXDigits - lowestXDigitNumber;

        // how many X digit numbers are in this range? (integer division)
        // e.g. 25/4 = 6  how many 4 character numbers fit in 25? == 6  125*
        int numberOfThisDigitValues = differenceOfThisDigitValues / displayLengthInStringWithLimiterAppended;

        // but we already displayed one
        numberOfThisDigitValues++;

        // 125 - (4*7) == 97
        int lowestXCharNumberIs = maxNumberWithXDigits - (displayLengthInStringWithLimiterAppended * numberOfThisDigitValues);
        if(lowestXCharNumberIs==0){
            // if it is 0 then it is really 2 because we can't have 0 as the lowest number
            lowestXCharNumberIs=2;
        }

        // length of 97 with spacer is 3
        int lengthOfLowestAsCounterString = getLengthOfNumberInString(lowestXCharNumberIs, spacer);

        // 3 != 4 so 97 is not lowest, lowest must be 97 + 4 = 101
        if (lengthOfLowestAsCounterString != displayLengthInStringWithLimiterAppended) {
            //it is not an X char number so next X char number is lowest + lengthOfNumberInString
            lowestXCharNumberIs = lowestXCharNumberIs + displayLengthInStringWithLimiterAppended;
        }

        return lowestXCharNumberIs;
    }

    private int getLengthOfNumberInString(int valueOfLastCounter, String theSpacer) {
        String numberOfDigitsExample = String.valueOf(valueOfLastCounter) + theSpacer;
        return numberOfDigitsExample.length();
    }
}
