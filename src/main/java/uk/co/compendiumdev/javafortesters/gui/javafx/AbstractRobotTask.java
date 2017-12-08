package uk.co.compendiumdev.javafortesters.gui.javafx;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import uk.co.compendiumdev.javafortesters.gui.awtbridge.RobotTyper;

public abstract class AbstractRobotTask {
    Button robotTypeButton;
    RobotTyper typer;
    Service task;


    public void configureAbstractRobotTask(Button buttonToControl) {
        this.robotTypeButton = buttonToControl;
        setActionForButton();
        typer = new RobotTyper();
        assignTask();
    }

    private void assignTask() {
        //robot typing into field- never stop thread - once robot is used a thread ticks over in the background
        // ready to be re-used
        task = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>(){
                    @Override
                    public Void call() throws Exception {
                        int x=5;

                        while(!isCancelled()) {
                            if(robotTypeButton.getText().startsWith("Robot")){
                                x=5;
                            }
                            final int finalX = x--;

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {



                                    // This needs to be a state machine
                                    // Robot means stop don't do anything
                                    // Start means start counting down
                                    // In [ means continue counting
                                    // GO means calculate the counterstring
                                    // ... means iterate through and send the keys
                                    if (robotTypeButton.getText().startsWith("Start") || robotTypeButton.getText().startsWith("In [")) {
                                        robotTypeButton.setText("In [" + finalX + "] secs");
                                    }
                                    if (finalX <= 0) {
                                        robotTypeButton.setText("GO");
                                        // calculate counterstring and iterator here

                                        robotTypeButton.setText("...");
                                    }
                                    if(robotTypeButton.getText().startsWith("...")){
                                        if(robotIsStillWorking()){

                                            robotDoTheWork();

                                        }else{
                                            // we are finished
                                            resetRobotButtonText();
                                            cancel();
                                            return;
                                        }
                                    }
                                }
                            });

                            if(robotTypeButton.getText().startsWith("Robot")||robotTypeButton.getText().startsWith("In [")) {
                                Thread.sleep(1000);
                            }else{
                                Thread.sleep(10);
                                x=5;
                            }
                            System.out.println("Thread " + x);
                        }
                        return null;
                    }
                };
            }
        };
    }

    private void setActionForButton() {
        robotTypeButton.setOnAction(
                new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent e) {


                        System.out.println("clicked robot");

                        try {

                            if(task.isRunning()){
                                robotTypeButton.setText("Cancelling");
                                task.cancel();
                                resetRobotButtonText();

                                return;
                            }


                            if(robotTypeButton.getText().startsWith("Robot")){

                                if(!task.isRunning()){
                                    task.reset();

                                    resetRobot();

                                    robotTypeButton.setText("Start");
                                    robotTypeButton.setTooltip(new Tooltip("Click Button again to cancel Robot"));
                                    task.start();
                                }

                            }else{
                                resetRobotButtonText();
                            }

                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }




    public void stopTheTask() {
        if(task!=null) {
            if (task.isRunning()) {
                task.cancel();
                resetRobotButtonText();
            }
        }
    }

    /* specific Robot config */

    abstract void robotDoTheWork();
    abstract boolean robotIsStillWorking();

    abstract public void resetRobotButtonText();
    abstract public void resetRobot();



}
