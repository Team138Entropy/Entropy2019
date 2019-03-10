package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.subsystems.AcquisitionRoller.AcquisitionState;
import frc.robot.subsystems.AcquisitionRoller.RollerState;
import frc.robot.subsystems.Elevator.ElevatorTarget;


public class AcquireCargo extends CommandGroup {

    public AcquireCargo() {
        requires(Robot.manipulator);

        addSequential(new ElevateToTarget(ElevatorTarget.SAFE));
        addSequential(new ExtendRoller());
        addSequential(new ElevateToTarget(ElevatorTarget.FLOOR));
        addSequential(new SetRoller(RollerState.INTAKE));
        addSequential(new SetAcquireCargoState(AcquisitionState.ACQUIRE));
    }
}