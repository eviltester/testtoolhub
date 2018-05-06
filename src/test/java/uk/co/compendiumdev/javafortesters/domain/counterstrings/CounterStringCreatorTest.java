package uk.co.compendiumdev.javafortesters.domain.counterstrings;

import org.junit.Assert;
import org.junit.Test;
import uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators.CounterStringCreator;
import uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators.StringCounterStringCreator;
import uk.co.compendiumdev.javafortesters.domain.counterstrings.Creators.SystemOutCounterStringCreator;

public class CounterStringCreatorTest {


    @Test
    public void haveACounterStringCreatorInterface() throws CounterStringCreationError {

        CounterStringCreator creator = new StringCounterStringCreator();

        new CounterString().createWith(35, "*", creator);

        Assert.assertEquals("2*4*6*8*11*14*17*20*23*26*29*32*35*", creator.toString());

    }

    @Test
    public void haveACounterStringSysOutCreator() throws CounterStringCreationError {

        CounterStringCreator creator = new SystemOutCounterStringCreator();

        new CounterString().createWith(35, "*", creator);

        Assert.assertEquals("2*4*6*8*11*14*17*20*23*26*29*32*35*", creator.toString());

    }
}
