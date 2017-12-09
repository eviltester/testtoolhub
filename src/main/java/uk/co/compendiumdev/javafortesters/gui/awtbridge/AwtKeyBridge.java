package uk.co.compendiumdev.javafortesters.gui.awtbridge;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class AwtKeyBridge {

    private final Robot robot;
    String shiftModifier="";
    private Set<Character> couldNotType;

    public AwtKeyBridge(Robot robot) {
        this.robot = robot;
        couldNotType = new HashSet<>();
    }

    public Set<Character> getCouldNotTypeChars(){
        return couldNotType;
    }

    public void resetCouldNotType() {
        couldNotType = new HashSet<>();
    }

    public void setShiftModifiers(String modifiers){

        if(modifiers.length()%2!=0){
            System.out.println("ERROR: the modifier String must be pairs of chars 'wanted' followed by 'shifted'");
            throw new IllegalArgumentException("The modifier String must be pairs of chars 'wanted' followed by 'shifted'");
        }

        shiftModifier = modifiers;
    }

    public AWTKeyStroke getCustomKeyStrokeMapping(char c) {


        // shiftModifiers are - to get this @ I have to do shift 2
        // i.e. want, unshifted
        String altModifier = "";

        //String altModifier = "€2#3";

        int wantedChar = shiftModifier.indexOf(c);
        if(wantedChar!=-1 && wantedChar%2==0){
            char theCharToModify = shiftModifier.charAt(wantedChar + 1);
            return AWTKeyStroke.getAWTKeyStroke(theCharToModify, InputEvent.SHIFT_DOWN_MASK );
        }

        // Unfortunately I can find no way to configure an Alt Right as opposed to an Alt Left key
        // not including the alt code at the moment
        /*
        wantedChar = altModifier.indexOf(c);
        if(wantedChar!=-1 && wantedChar%2==0){
            char theCharToModify = altModifier.charAt(wantedChar + 1);
            return AWTKeyStroke.getAWTKeyStroke(theCharToModify, InputEvent.ALT_DOWN_MASK );
        }
        */

        return AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_UNDEFINED,0);  // no modifiers
    }

    public void sendKey(char c) {

        AWTKeyStroke customMapping = getCustomKeyStrokeMapping(c);

        boolean shiftPressRequired = false;
        boolean altPressRequired = false;

        if (customMapping.getKeyCode() != KeyEvent.VK_UNDEFINED) {
            shiftPressRequired = customMapping.getModifiers() == (InputEvent.SHIFT_DOWN_MASK + 1);
            altPressRequired = customMapping.getModifiers() == (InputEvent.ALT_DOWN_MASK + 1);
            c = (char) customMapping.getKeyCode();
        } else {
            shiftPressRequired = Character.isUpperCase(c);
        }

        if (shiftPressRequired) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        }

        if (altPressRequired) {
            robot.keyPress(KeyEvent.VK_ALT + KeyEvent.VK_RIGHT);
        }

        try {

            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));

        } catch (IllegalArgumentException e) {

            System.out.println("could not type - " + c);
            System.out.println("Trying as special key");
            // assume an invalid key code
            //e.printStackTrace();

            try {
                int special = getSpecialKeyCodeForChar(c);
                robot.keyPress(special);
                robot.keyRelease(special);
            } catch (Exception se) {

                System.out.println("could not type as special key - " + c);
                couldNotType.add(c);
                //e.printStackTrace();
            }
        }


        if (altPressRequired) {
            robot.keyRelease(KeyEvent.VK_ALT + KeyEvent.VK_RIGHT);
        }

        if (shiftPressRequired) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }

    }

    private int getSpecialKeyCodeForChar(char c) {

        // this might help on some keyboards but didn't make much difference on mine

        switch(c){
            case ',': return KeyEvent.VK_COMMA;
            case '-': return KeyEvent.VK_MINUS;
            case '.': return KeyEvent.VK_PERIOD;
            case '/': return KeyEvent.VK_SLASH;
            case ';': return KeyEvent.VK_SEMICOLON;
            case '=': return KeyEvent.VK_EQUALS;
            case '[': return KeyEvent.VK_OPEN_BRACKET;
            case '\\': return KeyEvent.VK_BACK_SLASH;
            case ']': return KeyEvent.VK_CLOSE_BRACKET;
            case '!': return KeyEvent.VK_EXCLAMATION_MARK;
            case '@': return KeyEvent.VK_AT;
            case '$': return KeyEvent.VK_DOLLAR;
            case '€': return KeyEvent.VK_EURO_SIGN;
            case '#': return KeyEvent.VK_NUMBER_SIGN;
            //case '£':
            //case '%':
            case '^': return KeyEvent.VK_CIRCUMFLEX;
            case '&': return KeyEvent.VK_AMPERSAND;
            case '*': return KeyEvent.VK_ASTERISK;
            case '(': return KeyEvent.VK_LEFT_PARENTHESIS;
            case ')': return KeyEvent.VK_RIGHT_PARENTHESIS;
            case '_': return KeyEvent.VK_UNDERSCORE;
            case '+': return KeyEvent.VK_PLUS;
            case '<': return KeyEvent.VK_LESS;
            case '>': return KeyEvent.VK_GREATER;
            case '{': return KeyEvent.VK_BRACELEFT;
            case '}': return KeyEvent.VK_BRACERIGHT;
            case '"': return KeyEvent.VK_QUOTEDBL;
            case ':': return KeyEvent.VK_COLON;
            case '\'': return KeyEvent.VK_QUOTE;
            case '`': return KeyEvent.VK_BACK_QUOTE;
        }

        return KeyEvent.VK_UNDEFINED;
    }


}
