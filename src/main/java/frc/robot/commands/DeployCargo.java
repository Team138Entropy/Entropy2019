package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.AcquisitionRoller;
import frc.robot.Constants;

public class DeployCargo extends CommandGroup {

    public DeployCargo() {
        addSequential(new UnlatchCargo());
        addSequential(new RotateCargoManipulator(Constants.HORIZONTAL));
        addSequential(new Wait(Constants.CARGO_LOWER_DELAY));
        addSequential(new RetractRoller());
        addSequential(new Wait(Constants.PNEUMATIC_DELAY));
        addSequential(new SetRoller(AcquisitionRoller.RollerState.EJECT));
        addSequential(new Wait(Constants.CARGO_LOWER_DELAY));
        addSequential(new StopRoller());
    }
}