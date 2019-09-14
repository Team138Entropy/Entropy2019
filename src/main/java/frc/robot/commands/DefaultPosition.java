package frc.robot.commands;

import frc.robot.subsystems.Elevator.ElevatorTarget;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
	
	public class DefaultPosition extends CommandGroup {
        public DefaultPosition() {
            addSequential(new RetractRoller());
            addSequential(new RotateCargoManipulator(Constants.VERTICAL));
            addSequential(new Wait(Constants.PNEUMATIC_DELAY));
            addSequential(new ElevateToTarget(ElevatorTarget.SAFE));
            addSequential(new TranslateHatchPanel(Constants.RETRACT));
            addSequential(new RotateTurretToTarget(Constants.TurretCenterPosition));
            addSequential(new RetractClimbPiston());
        }
}