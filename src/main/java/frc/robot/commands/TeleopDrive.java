package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.CheesyDrive;
import frc.robot.Constants;
import frc.robot.DriveSignal;

//import org.usfirst.frc.team138.robot.subsystems.Claw;

public class TeleopDrive extends Command {
	
	//       *happy stalin*
	CheesyDrive ourDrive = new CheesyDrive();
	boolean headless = false;

	double accumulatedGyroError = 0;
	
	public TeleopDrive(){
		requires(Robot.drivetrain);
	}

	protected void initialize() {
	//	Sensors.resetEncoders();
	}

	protected void execute() {
		double moveSpeed = OI.getMoveSpeed();
		double rotateSpeed = OI.getRotateSpeed();

		double headlessStickX = OI.getStickX();
		double headlessStickY = OI.getStickY();

		Robot.drivetrain.difference(rotateSpeed);

		accumulatedGyroError += Constants.gyroDrift;

		if (headless) {
			Robot.drivetrain.drive(headlessStickX, headlessStickY, accumulatedGyroError);
		} else {
			Robot.drivetrain.drive(ourDrive.cheesyDrive(moveSpeed, rotateSpeed, OI.isQuickturn(), OI.isFullSpeed()), rotateSpeed);
		}
	}

	public DriveSignal driveCheeze(double moveSpeed, double rotateSpeed, boolean quickTurn) {
		return ourDrive.cheesyDrive(moveSpeed, rotateSpeed, quickTurn, false);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
