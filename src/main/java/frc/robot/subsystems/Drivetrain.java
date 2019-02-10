package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;

import frc.robot.commands.TeleopDrive;
import frc.robot.RobotMap;
import frc.robot.Sensors;
import frc.robot.Constants;
import frc.robot.DriveSignal;
import frc.robot.Util;

public class Drivetrain extends Subsystem {
	public double lastSpeed = 0;
	double _speedFactor = 1;
	double _rotateFactor = 1;

	// Filter state for joystick movements
	double _lastMoveSpeed = 0;
	double _lastRotateSpeed = 0;

	// Variables for the headless drive
	double heading = 0;

	int zeroCounter = 0;

	TeleopDrive ourOtherDrive;

	WPI_TalonSRX topLeftTalon            = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_CHANNEL_TOP);
	public WPI_TalonSRX bottomLeftTalon  = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_CHANNEL_BOTTOM);
	WPI_TalonSRX topRightTalon           = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_CHANNEL_TOP);
	public WPI_TalonSRX bottomRightTalon = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_CHANNEL_BOTTOM);

	protected void initDefaultCommand() {
		ourOtherDrive = new TeleopDrive();
		setDefaultCommand(ourOtherDrive);
	}

	public void DriveTrainInit()
	{
		/* choose the sensor and sensor direction */
		bottomLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
		bottomLeftTalon.setSensorPhase(true);

		// Reverse the right talons because they're facing opposite directions
		topRightTalon.setInverted(true);
		bottomRightTalon.setInverted(true);

		/* set the peak and nominal outputs, 12V means full */
		bottomLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
		bottomLeftTalon.configNominalOutputForward(0.,0);
		bottomLeftTalon.configNominalOutputReverse(0.,0);
		bottomLeftTalon.configPeakOutputForward(1,0);
		bottomLeftTalon.configPeakOutputReverse(-1,0);
		bottomLeftTalon.setNeutralMode(NeutralMode.Coast);
		topLeftTalon.setNeutralMode(NeutralMode.Coast);

		/* choose thebttomnsor and sensor direction */
		bottomRightTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
		bottomRightTalon.setSensorPhase(true);
		bottomRightTalon.configNominalOutputForward(0.,0);
		bottomRightTalon.configNominalOutputReverse(-0.,0);
		bottomRightTalon.configPeakOutputForward(1,0);
		bottomRightTalon.configPeakOutputReverse(-1,0);
		bottomRightTalon.setNeutralMode(NeutralMode.Coast);
		topRightTalon.setNeutralMode(NeutralMode.Coast);
		
		// Configure slave Talons to follow masters
		topLeftTalon.follow(bottomLeftTalon);
		topRightTalon.follow(bottomRightTalon);
	}

	public double difference(double turnSpeed) {
		double left = bottomLeftTalon.getSelectedSensorVelocity();
		double right = bottomRightTalon.getSelectedSensorVelocity();

		double directionChange = 0;

		if (left == 0 && right == 0) {
			directionChange = 0;
		} else {
			directionChange = (left / right) - 1;
		}

		double difference = turnSpeed - directionChange;

		System.out.println(left + " / " + right + " | side difference " + difference);

		return difference;
	}

	public double limitDriveAccel(double moveSpeed)
	{
		// Limit rate of change of move in order to control acceleration
		lastSpeed = Util.limitValue(moveSpeed, lastSpeed - Constants.MaxSpeedChange,
				lastSpeed + Constants.MaxSpeedChange);
		return lastSpeed;
	}

	public double limitRotateAccel(double rotateSpeed)
	{
		// Limit rate of change of rotate in order to control acceleration
		_lastRotateSpeed = Util.limit(rotateSpeed, _lastRotateSpeed - Constants.MaxRotateSpeedChange,
				_lastRotateSpeed + Constants.MaxRotateSpeedChange);
		return _lastRotateSpeed;
	}
    
	public void drive(DriveSignal signal, double turnSpeed)
    {	
		driveCheezy(signal, turnSpeed);
	}

	public void drive(double stickX, double stickY, double error) {
		driveHeadless(stickX, stickY, error);
	}

    private void driveCheezy(DriveSignal signal, double turnSpeed) {
		double diff = difference(turnSpeed);

		double left = (signal.getLeft() * Constants.driveModifier);
		double right = -(signal.getRight() * Constants.driveModifier);

        bottomLeftTalon.set(ControlMode.PercentOutput, left);
		bottomRightTalon.set(ControlMode.PercentOutput, right);
	}

	private void driveHeadless(double stickX, double stickY, double error) {
		// Headless Drive functions entirely seperate from standard Arcade Drive
		// It cross-references the gyro and encoders to determine the direction the robot is facing
		// and the way we want to go, outputting (using Cheezy Drive) the movements necessary to
		// get there.

		heading = Sensors.gyro.getAngle();
		double adjustedHeading = heading - error;
		double requestedAngle = Math.atan(stickX/stickY);
		double difference = adjustedHeading + requestedAngle + 90;
		int direction = 1;

		// a squared plus b squared equals c squared
		double speed = Math.sqrt((stickX * stickX) + (stickY * stickY));
		speed = speed * Constants.driveModifier;

		double turn = difference / 90;

		if (Math.abs(turn) < Constants.headlessTolerance) {
			turn = 0;
		}

		if (difference > 90 || difference < 90) {
			direction = -1;
		}

		boolean requiresQuickturn = false;
		if (difference > 45 || difference < 45) {
			// If the rotational difference we have to cover is greater than 45 degrees...
			requiresQuickturn = true;
			speed = 0;
		}

		ourOtherDrive.driveCheeze(speed * direction, turn, requiresQuickturn);
	}
	
	public void Relax() {
		bottomLeftTalon.set(ControlMode.PercentOutput, 0);
		bottomRightTalon.set(ControlMode.PercentOutput, 0);
	}
}
