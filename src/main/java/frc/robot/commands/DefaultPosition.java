package frc.robot.commands;

import frc.robot.subsystems.Elevator.ElevatorTarget;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
	
	public class DefaultPosition extends CommandGroup {
        public  DefaultPosition() {
            addSequential(new ElevateToTarget(ElevatorTarget.SAFE));
            addSequential(new Actuate(Constants.RETRACT, Constants.HORIZONTAL));
            addSequential(new TranslateHatchPanel(Constants.RETRACT));
            addSequential(new RotateTurretToTarget(Constants.TurretCenterPosition));
            addSequential(new RetractRoller());
            addSequential(new StopRoller());
            addSequential(new RetractClimbPiston());
        }
}