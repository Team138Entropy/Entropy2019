package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CheesyDrive;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;
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
		targetMoveSpeed=OI.getMoveSpeed();
		rotateSpeed=OI.getRotateSpeed();

		currentSpeed = Robot.drivetrain.limitRateOfChange(currentSpeed, targetMoveSpeed, Constants.maxAccelerationDelta);

		Robot.drivetrain.drive(ourDrive.cheesyDrive(currentSpeed, rotateSpeed, OI.isQuickturn(), OI.isFullSpeed()));
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
