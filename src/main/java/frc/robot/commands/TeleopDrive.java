package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CheesyDrive;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Sensors;

import static frc.robot.Robot.networkTableInstance;

public class TeleopDrive extends Command {

//           *happy stalin*
	CheesyDrive ourDrive = new CheesyDrive();


	private boolean driverVision, tapeVision, cargoVision, cargoSeen, tapeSeen;
	private NetworkTableEntry tapeDetected, cargoDetected, tapeYaw, cargoYaw,
			videoTimestamp, driveWanted, tapeWanted, cargoWanted;
	private NetworkTable entropyVision;

	public TeleopDrive(){
		requires(Robot.drivetrain);
	}

	protected void initialize() {
	//	Sensors.resetEncoders();


		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		NetworkTable table = networkTableInstance.getTable("SmartDashboard");
		entropyVision = inst.getTable("SmartDashboard");

		tapeDetected = entropyVision.getEntry("tapeDetected");
		cargoDetected = entropyVision.getEntry("cargoDetected");
		tapeYaw = entropyVision.getEntry("tapeYaw");
		cargoYaw = entropyVision.getEntry("cargoYaw");

		driveWanted = entropyVision.getEntry("Driver");
		tapeWanted = entropyVision.getEntry("Tape");
		cargoWanted = entropyVision.getEntry("Cargo");
	}


	//Constantly called
	protected void execute() {

		//Check if Vision Mode is held down
		//if vision mode is held down attempt to use tracking
		boolean VisionTracking = OI.isVision();

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


			//We are relying off of the drives forward speed
			//angle is controlled by vision
			double kP = 1.2;
			double forward = OI.getMoveSpeed();
			double turn = OI.getRotateSpeed();
			boolean tapeSeen  = false;
			double targetAngle = 0;

			driveWanted.setBoolean(false);
			tapeWanted.setBoolean(true);
			cargoWanted.setBoolean(false);
			// Checks if vision sees cargo or vision targets. This may not get called unless
			// cargo vision detected
			tapeSeen = tapeDetected.getBoolean(false);

			if (tapeSeen){
				targetAngle = tapeYaw.getDouble(0);
			}
			else{
				targetAngle = 0;
			}


			// Limit output to 0.3
			double output = limitOutput(-kP * targetAngle, 0.3);

			Robot.drivetrain.drive(ourDrive.cheesyDrive(forward, output, OI.isQuickturn(), OI.isFullSpeed()));


		}

	}

	public double limitOutput(double number, double maxOutput) {
		if (number > 1.0) {
			number = 1.0;
		}
		if (number < -1.0) {
			number = -1.0;
		}

		if (number > maxOutput) {
			return maxOutput;
		}
		if (number < -maxOutput) {
			return -maxOutput;
		}

		return number;
	}
	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}

}
