package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class Manipulator extends Subsystem {

    // Rotation solenoid not currently present, but may come back
    //public Solenoid rotationSolenoid = new Solenoid(RobotMap.MANIPULATOR_ROTATION_PORT);
    public Solenoid hatchPanelSolenoid = new Solenoid(RobotMap.HATCH_PANEL_PISTON_SOLENOID_CHANNEL);

    private boolean isHatchPanelTranslated = false;

    // Control states
    public boolean readyToAcquire = false;
    public boolean deployed = false;

    public void initDefaultCommand() {
        // Obligatory initDefaultCommand() declaration so we don't get yelled at by WPILib
    }

    public void translateHatchPanel (boolean translation) {
        isHatchPanelTranslated = translation;
        hatchPanelSolenoid.set(translation);
    }

    public void reset() {
        isHatchPanelTranslated = Constants.RETRACT;
        hatchPanelSolenoid.set(isHatchPanelTranslated);
    }
}