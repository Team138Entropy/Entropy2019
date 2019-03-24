package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;

public class ReacquireCargo extends CommandGroup {

    public ReacquireCargo() {
        addSequential(new ReverseRoller());
        addSequential(new Wait(Constants.OVERCURRENT_REVERSE_ROLLER_DELAY));
        addSequential(new ReverseRoller());
        addSequential(new ResetOvercurrentDetected());
    }
}