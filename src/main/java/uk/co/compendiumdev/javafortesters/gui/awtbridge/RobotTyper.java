package uk.co.compendiumdev.javafortesters.gui.awtbridge;

import java.awt.*;

public class RobotTyper {

    private long millisecondsPause;
    private Robot robot;
    private String textToType;
    private int currentChar;
    private long waitTime;

    public void setMilliseconds(long millisecondsPause) {
        this.millisecondsPause = millisecondsPause;
    }

    public Robot getRobot(){

        if(this.robot==null) {
            try {
                this.robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }

        return this.robot;
    }

    public boolean hasAnotherCharToType(){
        if(this.textToType!=null && this.currentChar<this.textToType.length()){
            return true;
        }
        return false;
    }

    public void setTextToType(String textToType) {
        this.textToType = textToType;
        this.currentChar = 0;
    }

    private String getNextCharToType() {
        int nextChar = this.currentChar+1;
        String retString =  this.textToType.substring(this.currentChar,nextChar);
        this.currentChar = nextChar;
        return retString;
    }

    public long getwaitTime() {
        return waitTime;
    }

    // return but do not advance to next char
    public String revealNextCharToType() {
        int nextChar = this.currentChar+1;
        return this.textToType.substring(this.currentChar,nextChar);
    }

    public void typeNextChar() {

        String nextCharToType = getNextCharToType();

        //System.out.println(outputString);
        for (char ct : nextCharToType.toCharArray()) {
            //System.out.println(""+c);
            char c = ct;

            AwtKeyBridge awtKeys = new AwtKeyBridge(getRobot());
            awtKeys.sendKey(c);
        }

        try {
            Thread.sleep(getwaitTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
