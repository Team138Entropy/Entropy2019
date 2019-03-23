package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class Manipulator extends Subsystem {

    public Solenoid translationSolenoid = new Solenoid(RobotMap.MANIPULATOR_TRANSLATION_PORT);
    public Solenoid rotationSolenoid = new Solenoid(RobotMap.MANIPULATOR_ROTATION_PORT);
    public Solenoid hatchPanelSolenoid = new Solenoid(RobotMap.HATCH_PANEL_PISTON_SOLENOID_CHANNEL);

    private boolean isTranslated = false;
    private boolean isRotated = false;
    private boolean isHatchPanelTranslated = false;

    // Control states
    public boolean readyToAcquire = false;
    public boolean deployed = false;

    public void initDefaultCommand() {
        // Obligatory initDefaultCommand() declaration so we don't get yelled at by WPILib
    }

    // Hardware state accessers
    public boolean getTranslated() {
        return isTranslated;
    }

    public boolean getRotated() {
        return isRotated;
    }

    // Hardware interface
    public void actuate(boolean translation, boolean rotation) {
        isTranslated = translation;
        translationSolenoid.set(translation);
        isRotated = rotation;
        rotationSolenoid.set(rotation);
    }

    public void translate(boolean translation) {
        isTranslated = translation;
        translationSolenoid.set(translation);
    }

    public void rotate(boolean rotation) {
        isRotated = rotation;
        rotationSolenoid.set(rotation);
    }

    public void translateHatchPanel (boolean translation) {
        isHatchPanelTranslated = translation;
        translationSolenoid.set(translation);
    }


    public void reset() {
        isTranslated = Constants.RETRACT;
        translationSolenoid.set(isTranslated);

        isRotated = Constants.HORIZONTAL;
        rotationSolenoid.set(isRotated);

        isHatchPanelTranslated = Constants.RETRACT;
        hatchPanelSolenoid.set(isHatchPanelTranslated);
    }
}