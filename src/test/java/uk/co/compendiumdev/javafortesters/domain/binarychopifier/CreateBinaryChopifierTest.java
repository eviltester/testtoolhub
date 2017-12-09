package uk.co.compendiumdev.javafortesters.domain.binarychopifier;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alan on 26/11/2016.
 * Used to TDD the binary chopifier
 */
public class CreateBinaryChopifierTest {


    /*     inital notes
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


*/


    @Test
    public void binaryChop_1024_to_2048() {

        BinaryChopifier binaryChopper = new BinaryChopifier();
        BinaryChopResults binaryChop = binaryChopper.chop(1024, 2048);

        Assert.assertEquals(1024, binaryChop.getStart());
        Assert.assertEquals(2048, binaryChop.getEnd());

        Assert.assertEquals(11, binaryChop.countChopPoints());

        Assert.assertEquals(1536, binaryChop.getChopPoint(1));
        Assert.assertEquals(1792, binaryChop.getChopPoint(2));
        Assert.assertEquals(1920, binaryChop.getChopPoint(3));
        Assert.assertEquals(1984, binaryChop.getChopPoint(4));
        Assert.assertEquals(2016, binaryChop.getChopPoint(5));
        Assert.assertEquals(2032, binaryChop.getChopPoint(6));
        Assert.assertEquals(2040, binaryChop.getChopPoint(7));
        Assert.assertEquals(2044, binaryChop.getChopPoint(8));
        Assert.assertEquals(2046, binaryChop.getChopPoint(9));
        Assert.assertEquals(2047, binaryChop.getChopPoint(10));
        Assert.assertEquals(2048, binaryChop.getChopPoint(11));

        Assert.assertEquals(512, binaryChop.getChopPointDiff(1));
        Assert.assertEquals(256, binaryChop.getChopPointDiff(2));
        Assert.assertEquals(128, binaryChop.getChopPointDiff(3));
        Assert.assertEquals(64, binaryChop.getChopPointDiff(4));
        Assert.assertEquals(32, binaryChop.getChopPointDiff(5));
        Assert.assertEquals(16, binaryChop.getChopPointDiff(6));
        Assert.assertEquals(8, binaryChop.getChopPointDiff(7));
        Assert.assertEquals(4, binaryChop.getChopPointDiff(8));
        Assert.assertEquals(2, binaryChop.getChopPointDiff(9));
        Assert.assertEquals(1, binaryChop.getChopPointDiff(10));
        Assert.assertEquals(0, binaryChop.getChopPointDiff(11));
    }


    @Test
    public void binaryChopEndValueOnly() {

        BinaryChopifier binaryChopper = new BinaryChopifier();
        BinaryChopResults binaryChop = binaryChopper.chop(2048);

        Assert.assertEquals(1024, binaryChop.getStart());
        Assert.assertEquals(2048, binaryChop.getEnd());

        Assert.assertEquals(11, binaryChop.countChopPoints());

        Assert.assertEquals(1536, binaryChop.getChopPoint(1));
        Assert.assertEquals(2048, binaryChop.getChopPoint(11));

        Assert.assertEquals(512, binaryChop.getChopPointDiff(1));
        Assert.assertEquals(0, binaryChop.getChopPointDiff(11));
    }


    @Test
    public void binaryChop128() {

        BinaryChopifier binaryChopper = new BinaryChopifier();
        BinaryChopResults binaryChop = binaryChopper.chop(128);

        Assert.assertEquals(64, binaryChop.getStart());
        Assert.assertEquals(128, binaryChop.getEnd());

        Assert.assertEquals(binaryChop.countChopPoints(), 7);

        int[] expectedValues = {96, 112, 120, 124, 126, 127, 128};
        int[] diffValues = {32, 16, 8, 4, 2, 1, 0};

        for (int x = 1; x <= binaryChop.countChopPoints(); x++) {
            System.out.println(String.format("%d (%d)", binaryChop.getChopPoint(x), binaryChop.getChopPointDiff(x)));
            Assert.assertEquals(expectedValues[x - 1], binaryChop.getChopPoint(x));
            Assert.assertEquals(diffValues[x - 1], binaryChop.getChopPointDiff(x));
        }

    }

    @Test
    public void binaryChop57() {

        BinaryChopifier binaryChopper = new BinaryChopifier();
        BinaryChopResults binaryChop = binaryChopper.chop(57);

        Assert.assertEquals(28, binaryChop.getStart());
        Assert.assertEquals(57, binaryChop.getEnd());

        Assert.assertEquals(binaryChop.countChopPoints(), 5);

        int[] expectedValues = {43, 50, 54, 56, 57};
        int[] diffValues = {14, 7, 3, 1, 0};

        for (int x = 1; x <= binaryChop.countChopPoints(); x++) {
            System.out.println(String.format("%d (%d)", binaryChop.getChopPoint(x), binaryChop.getChopPointDiff(x)));
            Assert.assertEquals(expectedValues[x - 1], binaryChop.getChopPoint(x));
            Assert.assertEquals(diffValues[x - 1], binaryChop.getChopPointDiff(x));
        }

    }


    @Test
    public void binaryChopReporter57() {

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
    public void binaryChopTool() {
        int endValue = 2000;
        System.out.println(new BinaryChopReporter(new BinaryChopifier().chop(endValue)).getStringReport());
    }
}
