package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Sensors;
import edu.wpi.first.wpilibj.command.Command;

public class ExtendClimbPiston extends Command {
	
	private boolean isFinished = false;
	public ExtendClimbPiston()
	{
		requires(Robot.climber);
	}

	protected void initialize() {
		isFinished = false;
		Robot.drivetrain.setDriveSpeed(Constants.ClimbSpeed);
		Robot.climber.extendClimbPiston();
	}

	protected void execute() {
		if (Sensors.isClimbProximityThresholdReached()) {
			isFinished = true;
		}
	}

	protected boolean isFinished() {
		return isFinished;
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