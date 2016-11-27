package uk.co.compendiumdev.javafortesters.binarychopifier;

import org.junit.Assert;
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


    @Test
    public void binaryChop_1024_to_2048(){

        BinaryChopifier binaryChopper = new BinaryChopifier();
        BinaryChopResults binaryChop = binaryChopper.chop(1024, 2048);

        Assert.assertEquals(binaryChop.getStart(), 1024);
        Assert.assertEquals(binaryChop.getEnd(), 2048);

        Assert.assertEquals(binaryChop.countChopPoints(), 11);

        Assert.assertEquals(binaryChop.getChopPoint(1), 1536);
        Assert.assertEquals(binaryChop.getChopPoint(2), 1792);
        Assert.assertEquals(binaryChop.getChopPoint(3), 1920);
        Assert.assertEquals(binaryChop.getChopPoint(4), 1984);
        Assert.assertEquals(binaryChop.getChopPoint(5), 2016);
        Assert.assertEquals(binaryChop.getChopPoint(6), 2032);
        Assert.assertEquals(binaryChop.getChopPoint(7), 2040);
        Assert.assertEquals(binaryChop.getChopPoint(8), 2044);
        Assert.assertEquals(binaryChop.getChopPoint(9), 2046);
        Assert.assertEquals(binaryChop.getChopPoint(10), 2047);
        Assert.assertEquals(binaryChop.getChopPoint(11), 2048);

        Assert.assertEquals(binaryChop.getChopPointDiff(1), 512);
        Assert.assertEquals(binaryChop.getChopPointDiff(2), 256);
        Assert.assertEquals(binaryChop.getChopPointDiff(3), 128);
        Assert.assertEquals(binaryChop.getChopPointDiff(4), 64);
        Assert.assertEquals(binaryChop.getChopPointDiff(5), 32);
        Assert.assertEquals(binaryChop.getChopPointDiff(6), 16);
        Assert.assertEquals(binaryChop.getChopPointDiff(7), 8);
        Assert.assertEquals(binaryChop.getChopPointDiff(8), 4);
        Assert.assertEquals(binaryChop.getChopPointDiff(9), 2);
        Assert.assertEquals(binaryChop.getChopPointDiff(10), 1);
        Assert.assertEquals(binaryChop.getChopPointDiff(11), 0);
    }


    @Test
    public void binaryChopEndValueOnly(){

        BinaryChopifier binaryChopper = new BinaryChopifier();
        BinaryChopResults binaryChop = binaryChopper.chop(2048);

        Assert.assertEquals(binaryChop.getStart(), 1024);
        Assert.assertEquals(binaryChop.getEnd(), 2048);

        Assert.assertEquals(binaryChop.countChopPoints(), 11);

        Assert.assertEquals(binaryChop.getChopPoint(1), 1536);
        Assert.assertEquals(binaryChop.getChopPoint(11), 2048);
    }


    @Test
    public void binaryChop128(){

        BinaryChopifier binaryChopper = new BinaryChopifier();
        BinaryChopResults binaryChop = binaryChopper.chop(128);

        Assert.assertEquals(binaryChop.getStart(), 64);
        Assert.assertEquals(binaryChop.getEnd(), 128);

        Assert.assertEquals(binaryChop.countChopPoints(), 7);

        int[] expectedValues = {96, 112, 120, 124, 126, 127, 128};
        int[] diffValues = {32, 16, 8, 4, 2, 1, 0};

        for(int x=1; x <= binaryChop.countChopPoints(); x++){
            System.out.println(String.format("%d (%d)", binaryChop.getChopPoint(x), binaryChop.getChopPointDiff(x)));
            Assert.assertEquals(binaryChop.getChopPoint(x), expectedValues[x-1]);
            Assert.assertEquals(binaryChop.getChopPointDiff(x), diffValues[x-1]);
        }

    }

    @Test
    public void binaryChop57(){

        BinaryChopifier binaryChopper = new BinaryChopifier();
        BinaryChopResults binaryChop = binaryChopper.chop(57);

        Assert.assertEquals(binaryChop.getStart(), 28);
        Assert.assertEquals(binaryChop.getEnd(), 57);

        Assert.assertEquals(binaryChop.countChopPoints(), 5);

        int[] expectedValues = {43, 50, 54, 56, 57};
        int[] diffValues = {14, 7, 3, 1, 0};

        for(int x=1; x <= binaryChop.countChopPoints(); x++){
            System.out.println(String.format("%d (%d)", binaryChop.getChopPoint(x), binaryChop.getChopPointDiff(x)));
            Assert.assertEquals(binaryChop.getChopPoint(x), expectedValues[x-1]);
            Assert.assertEquals(binaryChop.getChopPointDiff(x), diffValues[x-1]);
        }

    }


    @Test
    public void binaryChopReporter57(){

        BinaryChopifier binaryChopper = new BinaryChopifier();
        BinaryChopResults binaryChop = binaryChopper.chop(57);
        BinaryChopReporter reporter = new BinaryChopReporter(binaryChop);
        String report = reporter.getStringReport();

        String[] lines = report.split(System.lineSeparator());

        Assert.assertEquals("Start: 28", lines[0]);
        Assert.assertEquals("End: 57", lines[1]);
        Assert.assertEquals("", lines[2]);
        Assert.assertEquals("Chop: value (diff)", lines[3]);
        Assert.assertEquals("------------------", lines[4]);
        Assert.assertEquals("1: 43 (14)", lines[5]);
        Assert.assertEquals("2: 50 (7)", lines[6]);
        Assert.assertEquals("3: 54 (3)", lines[7]);
        Assert.assertEquals("4: 56 (1)", lines[8]);
        Assert.assertEquals("5: 57 (0)", lines[9]);

    }

    @Test
    public void binaryChopTool(){
        int endValue=2000;
        System.out.println(new BinaryChopReporter(new BinaryChopifier().chop(endValue)).getStringReport());
    }
}
