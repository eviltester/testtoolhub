package uk.co.compendiumdev.javafortesters;

import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;

public class robot {

    @Ignore("adhoc test for experimenting with keycodes")
    @Test
    public void whatDoesRobotPrint() throws AWTException, InterruptedException {

        Thread.sleep(2000);
        Robot robot = new Robot();

        int keyCode = KeyEvent.VK_CIRCUMFLEX;
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }
}
