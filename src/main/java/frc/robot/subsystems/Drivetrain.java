package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.*;
import frc.robot.commands.TeleopDrive;

public class Drivetrain extends Subsystem {
	public double lastSpeed = 0;
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

	WPI_TalonSRX topLeftTalon            = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_CHANNEL_TOP);
	public WPI_TalonSRX bottomLeftTalon  = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_CHANNEL_BOTTOM);
	WPI_TalonSRX topRightTalon           = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_CHANNEL_TOP);
	public WPI_TalonSRX bottomRightTalon = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_CHANNEL_BOTTOM);

	protected void initDefaultCommand() {
		setDefaultCommand(new TeleopDrive());
	}


	public void DriveTrainInit()
	{
		/* choose the sensor and sensor direction */
		bottomLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,0);
		bottomLeftTalon.setSensorPhase(true);

		/* set the peak and nominal outputs, 12V means full */
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

		// Configure Talon gains
		bottomLeftTalon.config_kF(0, Drive_Kf,0);
		bottomLeftTalon.config_kP(0, Drive_Kp,0);
		bottomLeftTalon.config_kI(0, Drive_Ki,0);
		bottomLeftTalon.config_kD(0, Drive_Kd,0);
		bottomRightTalon.config_kF(0, Drive_Kf,0);
		bottomRightTalon.config_kP(0, Drive_Kp,0);
		bottomRightTalon.config_kI(0, Drive_Ki,0);
		bottomRightTalon.config_kD(0, Drive_Kd,0);

		// Configure slave Talons to follow masters
		topLeftTalon.follow(bottomLeftTalon);
		topRightTalon.follow(bottomRightTalon);
	}

	public double limitDriveAccel(double moveSpeed)
	{
		// Limit rate of change of move and rotate in order to control acceleration
		lastSpeed = Util.limitValue(moveSpeed, lastSpeed - Constants.MaxSpeedChange,
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
        bottomLeftTalon.set(ControlMode.PercentOutput, signal.getLeft() * Constants.tempWheelSpeed);
		bottomRightTalon.set(ControlMode.PercentOutput, signal.getRight() * Constants.tempWheelSpeed);
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
				bottomLeftTalon.set(ControlMode.Velocity, 0);
				bottomRightTalon.set(ControlMode.Velocity, 0);
			}
		}
		else {
			zeroCounter=0;
			left = moveSpeed - rotateSpeed; 
			right = moveSpeed + rotateSpeed;

			// Convert Meters / seconds to Encoder Counts per 100 milliseconds
			bottomLeftTalon.set(ControlMode.Velocity, left * Constants.SecondsTo100Milliseconds / Constants.MetersPerPulse);
			bottomRightTalon.set(ControlMode.Velocity, right * Constants.SecondsTo100Milliseconds / Constants.MetersPerPulse);
		}


		SmartDashboard.putNumber("L PWM", -bottomLeftTalon.getMotorOutputPercent());
		SmartDashboard.putNumber("R PWM", -bottomRightTalon.getMotorOutputPercent());

		SmartDashboard.putNumber("L Talon Vel (M/S)", -bottomLeftTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);
		SmartDashboard.putNumber("R Talon Vel (M/S)", -bottomRightTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);

	}

	public void Relax(){
		bottomLeftTalon.set(ControlMode.PercentOutput, 0);
		bottomRightTalon.set(ControlMode.PercentOutput, 0);
		SmartDashboard.putNumber("L PWM", -bottomLeftTalon.getMotorOutputPercent());
		SmartDashboard.putNumber("R PWM", -bottomRightTalon.getMotorOutputPercent());
	}


}
