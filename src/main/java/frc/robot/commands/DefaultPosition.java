package frc.robot.commands;

import frc.robot.subsystems.Elevator.ElevatorTarget;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.AcquisitionRoller.AcquisitionState;
	
	public class DefaultPosition extends CommandGroup {
        public  DefaultPosition() {
            addSequential(new SetAcquireCargoState(AcquisitionState.IDLE));
            addSequential(new ElevateToTarget(ElevatorTarget.SAFE));
            addSequential(new Actuate(Constants.RETRACT, Constants.HORIZONTAL));
            addSequential(new RotateTurretToTarget(Constants.TurretCenterPosition));
            addSequential(new RetractRoller());
            addSequential(new StopRoller());
            addSequential(new RetractClimbPiston());
        }
}