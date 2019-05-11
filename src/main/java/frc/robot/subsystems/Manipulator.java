package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class Manipulator extends Subsystem {

    public Solenoid translationSolenoid = new Solenoid(RobotMap.MANIPULATOR_TRANSLATION_PORT);
    public Solenoid rotationSolenoid = new Solenoid(RobotMap.MANIPULATOR_ROTATION_PORT);
    public Solenoid hatchPanelSolenoid = new Solenoid(RobotMap.HATCH_PANEL_PISTON_SOLENOID_CHANNEL);

    private boolean isWristDown = false;
    private boolean isHatchPanelTranslated = false;

    // Control states
    public boolean readyToAcquire = false;
    public boolean deployed = false;

    public void initDefaultCommand() {
        // Obligatory initDefaultCommand() declaration so we don't get yelled at by WPILib
    }

    // Hardware state accessors
    public boolean getWristDown() {
        return isWristDown;
    }

    // Hardware interface
    public void actuate(boolean wrist) {
        isWristDown = wrist;
        translationSolenoid.set(wrist);
    }

    public void translateHatchPanel (boolean translation) {
        isHatchPanelTranslated = translation;
        hatchPanelSolenoid.set(translation);
    }


    public void reset() {
        isWristDown = Constants.RETRACT;
        translationSolenoid.set(isWristDown);

        isHatchPanelTranslated = Constants.RETRACT;
        hatchPanelSolenoid.set(isHatchPanelTranslated);
    }
}