package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
//import frc.robot.subsystems.drivetrain.CheesyDrive;
import frc.robot.Robot;
import frc.robot.subsystems.drivetrain.DriveEngine;
import frc.robot.subsystems.drivetrain.StickDrive;

public class TeleopDrive extends Command {

//           *happy stalin*
	DriveEngine ourDrive = new StickDrive(OI.leftDriveStick, OI.rightDriveStick);
	
	public TeleopDrive(){
		requires(Robot.drivetrain);
	}

	protected void initialize() {
	//	Sensors.resetEncoders();
	}

	protected void execute() {
		Robot.drivetrain.drive(ourDrive.drive());
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
