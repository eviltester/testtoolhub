package uk.co.compendiumdev.javafortesters.gui.javafx.robottasks;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import uk.co.compendiumdev.javafortesters.counterstrings.County;

public class CounterStringRobotTask extends AbstractRobotTask{

    private County county;
    private int counterLength;
    private String spacerText;

    public CounterStringRobotTask(Button buttonToControl) {

        configureAbstractRobotTask(buttonToControl);
    }

    public void configureCounterStringRobotTyper( County county, int counterLength, String spacerText){
        this.county = county;
        configureRobot(counterLength, spacerText);
    }


    /* specific Robot config */

    void robotDoTheWork() {
        String outputString = county.getNextCounterStringEntry();
        robotTypeButton.setText("..."+outputString);
        typer.setTextToType(outputString);

        while(typer.hasAnotherCharToType()){

            typer.typeNextChar();
        }
    }

    boolean robotIsStillWorking() {
        return county.hasAnotherValueInRangeList();
    }

    public void resetRobotButtonText(){
        robotTypeButton.setText("Robot - " + this.counterLength + " " + this.spacerText);
        robotTypeButton.setTooltip(new Tooltip("Have robot type counterstring into field"));
    }

    public  void resetRobot() {
        county.createCounterStringRangesFor(this.counterLength, this.spacerText);
        resetRobotButtonText();
    }

    public  void configureRobot(int counterLength, String spacerText) {
        this.counterLength = counterLength;
        this.spacerText = spacerText;
        resetRobot();
    }
}
