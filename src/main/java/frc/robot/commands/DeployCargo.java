package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator.ElevatorTarget;

public class DeployCargo extends CommandGroup {

    public DeployCargo() {
        requires(Robot.elevator);

        boolean isLevel1 = Robot.elevator.getTargetPosition() == ElevatorTarget.LEVEL_1;
        boolean manipulatorState = isLevel1 ? Constants.HORIZONTAL : Constants.VERTICAL;
        Command rollerState = isLevel1 ? new ExtendRoller() : new RetractRoller();

        addSequential(new RotateCargoManipulator(manipulatorState));
        addSequential(new Wait(Constants.CARGO_LOWER_DELAY));
        addSequential(rollerState);
        addSequential(new Wait(Constants.PNEUMATIC_DELAY));
    }
}