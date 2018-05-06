package uk.co.compendiumdev.javafortesters.domain.counterstrings;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CounterStringRangeCreatorTest {

    /*
    This is the heart of the counterstring creation
    A range of start and end numbers that we iterate through
     */

    @Test
    public void canCalculateTwoCharacterDisplayDigitRange() {

        List<CounterStringRangeStruct> ranges = new CounterStringRangeCreator(9, "*").getListOfRangeStructs();

        Assert.assertNotNull(ranges);
        Assert.assertEquals(1, ranges.size());
        Assert.assertEquals(9, ranges.get(0).maxValInRange);
        Assert.assertEquals(1, ranges.get(0).minValInRange);
    }

    @Test
    public void canCalculateThreeCharacterDisplayDigitRange(){

        // 3 chars 32*35*
        List<CounterStringRangeStruct> ranges = new CounterStringRangeCreator(35, "*").getListOfRangeStructs();

        Assert.assertNotNull(ranges);
        Assert.assertEquals(2, ranges.size());
        Assert.assertEquals(35, ranges.get(1).maxValInRange);
        Assert.assertEquals(11, ranges.get(1).minValInRange);
        Assert.assertEquals(8, ranges.get(0).maxValInRange);
        Assert.assertEquals(2, ranges.get(0).minValInRange);

    }
}
