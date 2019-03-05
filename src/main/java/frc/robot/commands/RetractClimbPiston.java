package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class RetractClimbPiston extends Command {
	
	public RetractClimbPiston()
	{
		requires(Robot.climber);
	}

	protected void initialize() {
		Robot.drivetrain.setDriveSpeed(Constants.FullSpeed);
		Robot.climber.retractClimbPiston();
	}

	protected void execute() {

	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}

	protected void interrupted() {

	}

}