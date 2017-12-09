package uk.co.compendiumdev.javafortesters.gui.awtbridge;

import uk.co.compendiumdev.javafortesters.gui.javafx.Config;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class RobotTyper {

    private long millisecondsPause;
    private Robot robot;
    private String textToType;
    private int currentChar;
    private long waitTime;
    private int totalChars;
    AwtKeyBridge awtKeys;
    private Set<Character> couldNotType;
    private String couldNotTypeKeysAsString;

    public void setMilliseconds(long millisecondsPause) {
        this.millisecondsPause = millisecondsPause;
    }

    public RobotTyper(){
        couldNotType = new HashSet<>();
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
        this.totalChars = textToType.length();

        collateCouldNotTypeKeys();

        awtKeys = new AwtKeyBridge(getRobot());
        awtKeys.setShiftModifiers(Config.getCurrentShiftModifiers());
    }

    private void collateCouldNotTypeKeys() {
        if(awtKeys!=null){
            couldNotType.addAll(awtKeys.getCouldNotTypeChars());
        }
    }

    public Set<Character> getCouldNotTypeKeys(){
        collateCouldNotTypeKeys();
        return couldNotType;
    }

    public String getCouldNotTypeKeysAsString() {
        collateCouldNotTypeKeys();

        StringBuilder keys = new StringBuilder();

        for(Character c : couldNotType){
            keys.append(c);
        }
        return keys.toString();
    }

    public void resetCouldNotType() {
        awtKeys.resetCouldNotType();
        couldNotType = new HashSet();
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

            awtKeys.sendKey(c);
        }

        try {
            Thread.sleep(getwaitTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getCurrentCharCount() {
        return currentChar;
    }

    public int getTotalCharCount() {
        return totalChars;
    }



}
