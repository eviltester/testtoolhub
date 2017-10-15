package uk.co.compendiumdev.javafortesters.counterstrings;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class CounterstringCreationTest {

    @Test
    public void createExampleFromSatisfice() throws CounterStringCreationError {

        String result = new CounterString().create(35);
        Assert.assertEquals("2*4*6*8*11*14*17*20*23*26*29*32*35*", result);

    }


    @Test
    public void preCalculated34() throws CounterStringCreationError {

        String result = new CounterString().create(34);
        Assert.assertEquals("*3*5*7*10*13*16*19*22*25*28*31*34*", result);

    }

    @Test
    public void preCalculated34WithSpace() throws CounterStringCreationError {

        String result = new CounterString().create(34, " ");
        Assert.assertEquals(" 3 5 7 10 13 16 19 22 25 28 31 34 ", result);

    }

    @Test
    public void preCalculated34WithDot() throws CounterStringCreationError {

        String result = new CounterString().create(34, ".");
        Assert.assertEquals(".3.5.7.10.13.16.19.22.25.28.31.34.", result);

    }

    @Test
    public void preCalculated34WithDotFromTruncation() throws CounterStringCreationError {

        String result = new CounterString().create(34, ".*&^%@:");
        Assert.assertEquals(".3.5.7.10.13.16.19.22.25.28.31.34.", result);

    }


    @Test
    public void preCalculated33() throws CounterStringCreationError {

        String result = new CounterString().create(33);
        Assert.assertEquals("*3*5*7*9*12*15*18*21*24*27*30*33*", result);

    }

    @Test
    public void preCalculated10() throws CounterStringCreationError {

        String result = new CounterString().create(10);
        Assert.assertEquals("*3*5*7*10*", result);

    }


    @Test
    public void preCalculated9() throws CounterStringCreationError {

        String result = new CounterString().create(9);
        Assert.assertEquals("*3*5*7*9*", result);

    }

    @Test
    public void preCalculated8() throws CounterStringCreationError {

        String result = new CounterString().create(8);
        Assert.assertEquals("2*4*6*8*", result);

    }

    @Test
    public void preCalculated3() throws CounterStringCreationError {

        String result = new CounterString().create(3);
        Assert.assertEquals("*3*", result);
    }

    @Test
    public void preCalculated2() throws CounterStringCreationError {

        String result = new CounterString().create(2);
        Assert.assertEquals("2*", result);
    }

    @Test
    public void preCalculated1() throws CounterStringCreationError {

        String result = new CounterString().create(1);
        Assert.assertEquals("*", result);
    }

    @Test
    public void checkAFew() throws CounterStringCreationError {

        int cslen = 3783;  // picked a largish odd number
        int csinc = 73; // increment by a largish odd number

        for(int x=1;x<399;x++){
            String cs = new CounterString().create(cslen);
            //System.out.println(cslen);
            Assert.assertTrue(cslen + "* should be the last strings", cs.endsWith(cslen + "*"));
            Assert.assertTrue(cslen + " should be the length", cs.length()==cslen);
            cslen += csinc;
        }
    }

    @Test
    public void visibleCheck() throws CounterStringCreationError {

        for(int x=2;x<399;x++){
            String cs = new CounterString().create(x);
            System.out.println(cs);
            Assert.assertTrue(cs.endsWith(x + "*"));
            Assert.assertTrue(cs.length()==x);
        }
    }

    @Ignore("This test is for adhoc manual creation of a counterstring")
    @Test
    public void useIt() throws CounterStringCreationError {
        System.out.println(new CounterString().create(60001));
    }

}
