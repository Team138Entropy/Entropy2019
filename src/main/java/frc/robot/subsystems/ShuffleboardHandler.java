package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;

public class ShuffleboardHandler extends Subsystem {

    private NetworkTableEntry rollerSpeedDouble;
    private NetworkTableEntry pistonRotateEnabled;

    public void initDefaultCommand() {

    }

    public void init() {
        rollerSpeedDouble = Robot.main.add("Roller Speed", Constants.ROLLER_SPEED).getEntry();
        pistonRotateEnabled = Robot.main.add("Piston Rotate Enabled", true).getEntry();
    }

    public void execute() {
        
    }

    public double getRollerSpeed() {
        return rollerSpeedDouble.getDouble(Constants.ROLLER_SPEED);
    }

    public boolean isPistonRotateEnabled() {
        return pistonRotateEnabled.getBoolean(true);
    }
}