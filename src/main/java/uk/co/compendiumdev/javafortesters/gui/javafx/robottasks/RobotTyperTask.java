package uk.co.compendiumdev.javafortesters.gui.javafx.robottasks;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import uk.co.compendiumdev.javafortesters.gui.awtbridge.RobotTyper;

public class RobotTyperTask extends AbstractRobotTask{

    RobotTyper roboTyper;
    private long millisecondsGap;
    private String textToType;

    public RobotTyperTask(Button buttonToControl) {

        configureAbstractRobotTask(buttonToControl);

        roboTyper = new RobotTyper();
    }



    boolean robotIsStillWorking() {
        return roboTyper.hasAnotherCharToType();
    }

    void robotDoTheWork() {

        String outputString = roboTyper.revealNextCharToType();
        robotTypeButton.setText("...  " + outputString + "  " + roboTyper.getCurrentCharCount() + "/" + roboTyper.getTotalCharCount());

        roboTyper.typeNextChar();
    }

    public void resetRobotButtonText(){
        robotTypeButton.setText("Robot - " + textToType.length() + " chars - " + millisecondsGap + "ms");
        robotTypeButton.setTooltip(new Tooltip("Have robot start typing"));
    }

    public  void resetRobot() {
        roboTyper.setMilliseconds(this.millisecondsGap);
        roboTyper.setTextToType(textToType);
        resetRobotButtonText();
    }

    public  void configureRobot(long millisecondsGap, String textToType) {
        this.millisecondsGap = millisecondsGap;
        this.textToType = textToType;
        resetRobot();
    }
}
