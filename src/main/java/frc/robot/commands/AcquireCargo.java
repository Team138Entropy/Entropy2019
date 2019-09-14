package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator.ElevatorTarget;


public class AcquireCargo extends CommandGroup {

    public AcquireCargo() {
        addSequential(new UnlatchCargo());
        addSequential(new ElevateToTarget(ElevatorTarget.FLOOR));
        addSequential(new StartRollerSequence());
        addSequential(new RotateCargoManipulator(Constants.HORIZONTAL));
        addSequential(new DetectAndLatchCargo());
    }
}