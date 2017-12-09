package uk.co.compendiumdev.javafortesters.gui.javafx;


public class Config {

    public static int getDefaultWindowWidth(){
        return 600;
    }

    public static int getDefaultWindowHeight(){
        return 200;
    }

    // string of paired characters, first is the 'wanted' char, next is the 'shifted' char
    // escaped modifier in string representation are at the end
    private static String defaultShiftModifiers = "~`!1@2£3$4%5^6&7*8(9)0_-+={[}]:;<,>.?/" + '"' + "'" + "|" + '\\';

    public static String currentShiftModifiers = defaultShiftModifiers;

    public static String getCurrentShiftModifiers() {
        return currentShiftModifiers;
    }

    public static void setCurrentShiftModifiers(String currentShiftModifiers) {
        Config.currentShiftModifiers = currentShiftModifiers;
    }

    public static String getDefaultShiftModifiers() {
        return defaultShiftModifiers;
    }
}
