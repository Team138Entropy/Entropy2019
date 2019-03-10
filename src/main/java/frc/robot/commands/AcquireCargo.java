package frc.robot.commands;

import frc.robot.subsystems.Elevator.ElevatorTarget;

import frc.robot.commands.ElevateToTarget;
import frc.robot.commands.StartRollerSequence;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AcquireCargo extends CommandGroup {
    public AcquireCargo() {
        addSequential(new ElevateToTarget(ElevatorTarget.LEVEL_1_ACQUIRE));
        addParallel(new StartRollerSequence());
        addSequential(new ElevateToTarget(ElevatorTarget.FLOOR));
    }
}