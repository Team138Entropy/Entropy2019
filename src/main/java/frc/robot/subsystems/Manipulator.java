package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.*;

public class Manipulator extends Subsystem {

    public Solenoid translationSolenoid = new Solenoid(RobotMap.MANIPULATOR_TRANSLATION_PORT);
    public Solenoid rotationSolenoid = new Solenoid(RobotMap.MANIPULATOR_ROTATION_PORT);

    // state machine control variables
    private boolean stayStateControl = false;
    private boolean changeStateControl = false;

    private boolean isTranslated = false;
    private boolean isRotated = false;

    public void initDefaultCommand() {
        setDefaultCommand(new ManipulatorStateMachine());
    }

    public void stayState() {
        stayStateControl = true;
    }

    public void changeState() {
        changeStateControl = true;
    }

    public boolean getStayState() {
        boolean tempStay = stayStateControl;
        stayStateControl = false;
        return tempStay;
    }

    public boolean getChangeState() {
        boolean tempChange = changeStateControl;
        changeStateControl = false;
        return tempChange;
    }

    public void actuate(boolean translation, boolean rotation) {
        isTranslated = translation;
        translationSolenoid.set(translation);
        isRotated = rotation;
        rotationSolenoid.set(rotation);
    }

    public void actuate(boolean translation, String str) {
        isTranslated = translation;
        translationSolenoid.set(translation);
    }

    public void actuate(String str, boolean rotation) {
        isRotated = rotation;
        rotationSolenoid.set(rotation);
    }

    public boolean getTranslated() {
        return isTranslated;
    }

    public boolean getRotated() {
        return isRotated;
    }

}