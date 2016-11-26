package uk.co.compendiumdev.javafortesters.binarychopifier;

import org.junit.Test;

/**
 * Created by Alan on 26/11/2016.
 * Used to TDD the binary chopifier
 */
public class CreateBinaryChopifierTest {


    /*     iniital notes
    Tool idea: binary chopper!
    start: 1024 end: 2048
    result

    chop: value (inc)
-------------------
        01: 1536  (512)
        02: 1792 (256)
        03: 1920 (128)
        04: 1984 (64)
        05: 2016 (32)
        06: 2032 (16)
        07: 2040 (8)
        08: 2044 (4)
        09: 2046 (2)
        10: 2047 (1)
        11: 2048 (0)

- given a start and end output a list of value for the binary chop to std out
- calculate values and

*/


    @Test
    public void calculateBinaryChopForStartAndEndFromThoughtAlgorithm(){

        int start = 1024;
        int end = 2048;
        int choppoint=start;
        int inc = start;

        while(inc > 0){
            inc = (end-choppoint)/2;
            choppoint=choppoint+inc;
            System.out.println(String.format("%d (%d)", choppoint, inc));
        }

    }

    /* above results show it is the wrong algorithm */

    @Test
    public void calculateBinaryChopForStartAndEnd(){

        int start = 1024;
        int end = 2048;
        int choppoint=start;
        int diff = start;

        while(diff > 0){

            diff = (end-choppoint)/2;
            choppoint=end-diff;
            System.out.println(String.format("%d (%d)", choppoint, diff));
        }

    }

    /* above is better algorithm, but with this new algorithm I don't really need the choppoint */

    @Test
    public void calculateBinaryChopForStartAndEndHalfDifference(){

        int start = 1024;
        int end = 2048;
        int diff = end-start;

        while(diff > 0){
            diff = diff/2;
            System.out.println(String.format("%d (%d)", end-diff, diff));
        }
    }

    /* above has choppoint removed */
}
