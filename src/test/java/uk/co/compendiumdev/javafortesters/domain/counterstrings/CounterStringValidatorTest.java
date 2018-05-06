package uk.co.compendiumdev.javafortesters.domain.counterstrings;

import org.junit.Assert;
import org.junit.Test;

public class CounterStringValidatorTest {


    @Test
    public void haveACounterStringValidator(){


        Assert.assertTrue(new CounterStringValidator("*3*").isValid());
        Assert.assertTrue(new CounterStringValidator("2*4*").isValid());
        Assert.assertFalse(new CounterStringValidator("*2*").isValid());

        Assert.assertFalse(new CounterStringValidator("*3*5*8*10*13*16*19*22*25*28*31*34*37*40*43*46*49*52*55*58*61*64*67*70*73*76*").isValid());
    }
}
