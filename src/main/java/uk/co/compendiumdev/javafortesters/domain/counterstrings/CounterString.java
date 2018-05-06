package uk.co.compendiumdev.javafortesters.domain.counterstrings;

import uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators.CounterStringCreator;
import uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators.StringCounterStringCreator;

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

    public String create(int lengthOfCounterString, String limiter) throws CounterStringCreationError {

        return createWith(lengthOfCounterString, limiter, new StringCounterStringCreator()).toString();

    }

    public CounterStringCreator createWith(int lengthOfCounterString, String limiter, CounterStringCreator creator) throws CounterStringCreationError {
        String theSpacer = getSingleCharSpacer(limiter);

        List<CounterStringRangeStruct> ranges = createCounterStringRangesFor(lengthOfCounterString, theSpacer);

        try {

            // iterate through the ranges to build the string
            for(CounterStringRangeStruct range : ranges){
                CounterStringRangeIterator ranger = new CounterStringRangeIterator(range);
                while(ranger.hasAnotherValueInRange()){
                    creator.append(getCounterStringRepresentationOfNumber(ranger.getNextValueFromRange(), theSpacer));
                }

            }


        }catch(OutOfMemoryError e){
            e.printStackTrace();
            throw new CounterStringCreationError("Sorry, OutOfMemory error creating string to memory, probably heap space, close down the app and try with a smaller length", null);

        }

        return creator;
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


    public String getCounterStringRepresentationOfNumber(int outX, String theSpacer) {
        // if we are outputting the number 1 then output just the spacer
        if (outX == 1) {
            return theSpacer;
        }

        return String.valueOf(outX) + theSpacer;
    }


    public List<CounterStringRangeStruct> createCounterStringRangesFor(int lengthOfCounterString, String limiter) {

        return new CounterStringRangeCreator(lengthOfCounterString, limiter).getListOfRangeStructs();

    }



}
