package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CheesyDrive;
import frc.robot.OI;
import frc.robot.Robot;

public class TeleopDrive extends Command {

//           *happy stalin*
	CheesyDrive ourDrive = new CheesyDrive();
	
	public TeleopDrive(){
		requires(Robot.drivetrain);
	}

	protected void initialize() {
	//	Sensors.resetEncoders();
	}


	//Constantly called
	protected void execute() {

		//Check if Vision Mode is held down
		//if vision mode is held down attempt to use tracking
		boolean VisionTracking = false;

		if(VisionTracking == false){
			//Not Vision Tracking.. Operator Control
			double moveSpeed,rotateSpeed;
			moveSpeed=OI.getMoveSpeed();
			rotateSpeed=OI.getRotateSpeed();

			Robot.drivetrain.drive(ourDrive.cheesyDrive(moveSpeed, rotateSpeed, OI.isQuickturn(), OI.isFullSpeed()));
		}else{
			//Vision Tracking.. Auto Control
			//call network table parameters
			//use simple pid turn to lineup (proof of concept for now)
		}

	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
