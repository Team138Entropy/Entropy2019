package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Sensors;
import edu.wpi.first.wpilibj.command.Command;

public class ClimbPiston extends Command {
	

	public ClimbPiston()
	{
		requires(Robot.climber);
	}

	protected void initialize() {
		Robot.drivetrain.setDriveSpeed(Constants.ClimbSpeed);
		Robot.climber.extendClimbPiston();
	}

	protected void execute() {

	}

	protected boolean isFinished() {
		return Sensors.isClimbProximityThresholdReached();
	}

	protected void end() {
		Robot.drivetrain.setDriveSpeed(Constants.FullSpeed);
		Robot.climber.retractClimbPiston();
	}

	protected void interrupted() {
		Robot.drivetrain.setDriveSpeed(Constants.FullSpeed);
		Robot.climber.retractClimbPiston();

	}

}