package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Sensors;
import edu.wpi.first.wpilibj.command.Command;

public class ExtendClimbPiston extends Command {
	
	private boolean isLifted = false;
	private boolean isOnStep = false;
	public ExtendClimbPiston()
	{
		requires(Robot.climber);
	}

	protected void initialize() {
		isLifted = false;
		isOnStep = false;
		Robot.drivetrain.setDriveSpeed(Constants.ClimbSpeed);
		Robot.climber.extendClimbPiston();
	}

	protected void execute() {
		
		// Wait for the pistons to lift the robot off the floor
		if (!isLifted && !Sensors.isClimbProximityThresholdReached()) {
			isLifted = true;
		}

		// Now that the robot is lifted off the floor, wait for the step to be reached
		if (isLifted && Sensors.isClimbProximityThresholdReached()) {
			isOnStep = true;
		}
	}

	protected boolean isFinished() {
		return isOnStep;
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