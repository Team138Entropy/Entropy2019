package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.subsystems.AcquisitionRoller.RollerState;
import frc.robot.subsystems.Elevator.ElevatorTarget;


public class AcquireCargo extends CommandGroup {

    public AcquireCargo() {
        requires(Robot.manipulator);
        
        addSequential(new UnlatchCargo());
        addSequential(new ElevateToTarget(ElevatorTarget.FLOOR));
        addSequential(new ExtendRoller());
        // addSequential(new Wait(Constants.PNEUMATIC_DELAY));
        addSequential(new SetRoller(RollerState.INTAKE));
        addSequential(new DetectAndLatchCargo());
    }
}