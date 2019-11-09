package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.drivetrain.CheesyDrive;
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
