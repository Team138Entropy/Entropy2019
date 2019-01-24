package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.CheesyDrive;

//import org.usfirst.frc.team138.robot.subsystems.Claw;

public class TeleopDrive extends Command{
	
	CheesyDrive ourDrive = new CheesyDrive();
	
	public TeleopDrive(){
		requires(Robot.drivetrain);
	}

	protected void initialize() {
	//	Sensors.resetEncoders();
	}

	protected void execute() {
		double moveSpeed,rotateSpeed;
		moveSpeed=OI.getMoveSpeed();
		rotateSpeed= OI.getRotateSpeed();
		moveSpeed = -moveSpeed;

		System.out.println(moveSpeed);
		System.out.println(rotateSpeed);

		// // Full speed or slow speed?
		// if (OI.isFullSpeed()) {
		// 	// Full speed
		// 	moveSpeed=moveSpeed*Constants.ClosedLoopCruiseVelocity;
		// 	rotateSpeed=rotateSpeed*Constants.ClosedLoopTurnSpeed;
		// }
		// else { 
		// 	// Slow speed
		// 	moveSpeed=moveSpeed*Constants.ClosedLoopSlowVelocity;
		// 	rotateSpeed=rotateSpeed*Constants.ClosedLoopSlowRotateSpeed;
		// }
		// // Limit rate of change of moveSpeed
		// moveSpeed=Robot.drivetrain.limitDriveAccel(moveSpeed);
		// rotateSpeed=Robot.drivetrain.limitRotateAccel(rotateSpeed);
		// Robot.drivetrain.drive(moveSpeed,rotateSpeed);

		Robot.drivetrain.drive(ourDrive.cheesyDrive(moveSpeed, rotateSpeed, OI.isQuickturn(), OI.isFullSpeed()));
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
