package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CheesyDrive;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Constants;

public class TeleopDrive extends Command {

//           *happy stalin*
	CheesyDrive ourDrive = new CheesyDrive();

	double currentSpeed = 0;
	
	public TeleopDrive(){
		requires(Robot.drivetrain);
	}

	protected void initialize() {
	//	Sensors.resetEncoders();
	}

	protected void execute() {
		double targetMoveSpeed,rotateSpeed;
		boolean quickturn = false;
		targetMoveSpeed=OI.getMoveSpeed();
		rotateSpeed=OI.getRotateSpeed();

		if (Math.abs(targetMoveSpeed) > 0) {
			// We're trying to move, so don't quickturn
			quickturn = false;
		} else {
			quickturn = true;
		}

		currentSpeed = Robot.drivetrain.limitRateOfChange(currentSpeed, targetMoveSpeed, Constants.maxAccelerationDelta);

		Robot.drivetrain.drive(ourDrive.cheesyDrive(currentSpeed, rotateSpeed, quickturn, false));
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
