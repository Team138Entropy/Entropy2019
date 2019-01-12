package frc.robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.TeleopDrive;
import frc.robot.Utility;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.RobotMap;
import frc.robot.Constants;
import frc.robot.Robot;


public class Drivetrain extends Subsystem{
	public double lastSpeed=0;
	double _speedFactor = 1;
	double _rotateFactor = 1;

	// Servo Loop Gains
	double Drive_Kf = 1.7;
	double Drive_Kp = 5;
	double Drive_Ki = 0.02; //
	double Drive_Kd = 30;

	// Filter state for joystick movements
	double _lastMoveSpeed = 0;
	double lastRotateSpeed=0;

	int zeroCounter=0;

	public WPI_TalonSRX frontLeftTalon = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_CHANNEL_FRONT);
	WPI_TalonSRX backLeftTalon = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_CHANNEL_BACK);
	public WPI_TalonSRX frontRightTalon = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_CHANNEL_FRONT);
	WPI_TalonSRX backRightTalon = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_CHANNEL_BACK);

	protected void initDefaultCommand() {
		setDefaultCommand(new TeleopDrive());
	}


	public void DriveTrainInit()
	{
		/* choose the sensor and sensor direction */
		frontLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
		frontLeftTalon.setSensorPhase(true);
		frontLeftTalon.setInverted(true);
		backLeftTalon.setInverted(true);
		/* set the peak and nominal outputs, 12V means full */
		frontLeftTalon.configNominalOutputForward(0.,0);
		frontLeftTalon.configNominalOutputReverse(0.,0);
		frontLeftTalon.configPeakOutputForward(1,0);
		frontLeftTalon.configPeakOutputReverse(-1,0);
		frontLeftTalon.setNeutralMode(NeutralMode.Coast);
		backLeftTalon.setNeutralMode(NeutralMode.Coast);

		/* choose the sensor and sensor direction */
		frontRightTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
		frontRightTalon.setSensorPhase(true);
		frontRightTalon.configNominalOutputForward(0.,0);
		frontRightTalon.configNominalOutputReverse(-0.,0);
		frontRightTalon.configPeakOutputForward(1,0);
		frontRightTalon.configPeakOutputReverse(-1,0);
		frontRightTalon.setNeutralMode(NeutralMode.Coast);
		backRightTalon.setNeutralMode(NeutralMode.Coast);

		// Configure Talon gains
		frontLeftTalon.config_kF(0, Drive_Kf,0);
		frontLeftTalon.config_kP(0, Drive_Kp,0);
		frontLeftTalon.config_kI(0, Drive_Ki,0);
		frontLeftTalon.config_kD(0, Drive_Kd,0);
		frontRightTalon.config_kF(0, Drive_Kf,0);
		frontRightTalon.config_kP(0, Drive_Kp,0);
		frontRightTalon.config_kI(0, Drive_Ki,0);
		frontRightTalon.config_kD(0, Drive_Kd,0);

		// Configure slave Talons to follow masters
		backLeftTalon.follow(frontLeftTalon);
		backRightTalon.follow(frontRightTalon);
	}

	public double limitDriveAccel(double moveSpeed)
	{
		// Limit rate of change of move and rotate in order to control acceleration
		lastSpeed = Utility.limitValue(moveSpeed, lastSpeed - Constants.MaxSpeedChange,
				lastSpeed + Constants.MaxSpeedChange);
		return lastSpeed;
	}

	public double limitRotateAccel(double rotateSpeed)
	{
		// Limit rate of change of move and rotate in order to control acceleration
		lastRotateSpeed = Util.limit(rotateSpeed, lastRotateSpeed - Constants.MaxRotateSpeedChange,
				lastRotateSpeed + Constants.MaxRotateSpeedChange);
		return lastRotateSpeed;
	}

	public void drive(double moveSpeed, double rotateSpeed)
	{
        Robot.drivetrain.driveCloseLoopControl(moveSpeed, rotateSpeed);
    }
    
	public void drive(DriveSignal signal)
    {	
		driveCheezy(signal);
    }

    public void driveCheezy(DriveSignal signal) {
        frontLeftTalon.set(ControlMode.PercentOutput, signal.getLeft() * Constants.tempWheelSpeed);
		frontRightTalon.set(ControlMode.PercentOutput, signal.getRight() * Constants.tempWheelSpeed);

    }

	public void driveCloseLoopControl(double moveSpeed, double rotateSpeed)
	{
		/*
		 * moveSpeed and rotateSpeed in Meters/second.
		 */
		double left  = 0;   
		double right = 0;
		/*
		 * Robot motors move opposite to joystick and autonomous directions
		 */
		moveSpeed=-moveSpeed;
		rotateSpeed=-rotateSpeed;
		// Case where commands are exactly NULL
		if (moveSpeed==0 && rotateSpeed==0) {
			zeroCounter+=1;
			if (zeroCounter>Constants.zeroDelay) 
				Relax(); // set Talon VOLTAGE to 0
			else {
				// set Talon SPEED to 0
				frontLeftTalon.set(ControlMode.Velocity, 0);
				frontRightTalon.set(ControlMode.Velocity, 0);
			}
		}
		else {
			zeroCounter=0;
			left = moveSpeed - rotateSpeed; 
			right = moveSpeed + rotateSpeed;

			// Convert Meters / seconds to Encoder Counts per 100 milliseconds
			frontLeftTalon.set(ControlMode.Velocity, left * Constants.SecondsTo100Milliseconds / Constants.MetersPerPulse);
			frontRightTalon.set(ControlMode.Velocity, right * Constants.SecondsTo100Milliseconds / Constants.MetersPerPulse);
		}


		SmartDashboard.putNumber("L PWM", -frontLeftTalon.getMotorOutputPercent());
		SmartDashboard.putNumber("R PWM", -frontRightTalon.getMotorOutputPercent());

		SmartDashboard.putNumber("L Talon Vel (M/S)", -frontLeftTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);
		SmartDashboard.putNumber("R Talon Vel (M/S)", -frontRightTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);

	}

	public void Relax(){
		frontLeftTalon.set(ControlMode.PercentOutput, 0);
		frontRightTalon.set(ControlMode.PercentOutput, 0);
		SmartDashboard.putNumber("L PWM", -frontLeftTalon.getMotorOutputPercent());
		SmartDashboard.putNumber("R PWM", -frontRightTalon.getMotorOutputPercent());
	}


}
