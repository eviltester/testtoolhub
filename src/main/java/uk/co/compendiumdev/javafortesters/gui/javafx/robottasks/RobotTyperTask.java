package uk.co.compendiumdev.javafortesters.gui.javafx.robottasks;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class RobotTyperTask extends AbstractRobotTask{


    private long millisecondsGap;
    private String textToType;

    public RobotTyperTask(Button buttonToControl) {

        configureAbstractRobotTask(buttonToControl);

    }

    boolean robotIsStillWorking() {
        return typer.hasAnotherCharToType();
    }

    void robotDoTheWork() {

        String outputString = typer.revealNextCharToType();
        robotTypeButton.setText("...  " + outputString + "  " + typer.getCurrentCharCount() + "/" + typer.getTotalCharCount());

        typer.typeNextChar();
    }

    public void resetRobotButtonText(){
        robotTypeButton.setText("Robot - " + textToType.length() + " chars - " + millisecondsGap + "ms");
        robotTypeButton.setTooltip(new Tooltip("Have robot start typing"));
    }

    public  void resetRobot() {
        typer.setMilliseconds(this.millisecondsGap);
        typer.setTextToType(textToType);
        resetRobotButtonText();
    }

    public  void configureRobot(long millisecondsGap, String textToType) {
        this.millisecondsGap = millisecondsGap;
        this.textToType = textToType;
        resetRobot();
    }

}
