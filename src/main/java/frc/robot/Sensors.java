package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import edu.wpi.first.wpilibj.Joystick;

public class Sensors {
	public static ADXRS450_Gyro gyro; 
	
    static Joystick driverStick = new Joystick(0);
	
	public static SensorCollection leftSensorCollection;
	public static SensorCollection rightSensorCollection;
	
	public static double gyroBias=0;

	public static DigitalInput practiceRobotJumperPin;
	
	public static void initialize() {
		Robot.drivetrain.bottomLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		Robot.drivetrain.bottomRightTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        gyro.reset();
	   
	   practiceRobotJumperPin = new DigitalInput(5);
	}
	
	public static double getLeftDistance() {
		// In METERS
		return -Robot.drivetrain.bottomLeftTalon.getSelectedSensorPosition(0)*Constants.MetersPerPulse;
	}
	
	public static double getRightDistance() {
		// In METERS
		return -Robot.drivetrain.bottomRightTalon.getSelectedSensorPosition(0)*Constants.MetersPerPulse;
	}
	
	public static void resetEncoders() {
		Robot.drivetrain.bottomLeftTalon.setSelectedSensorPosition(0, 0, 0);
		Robot.drivetrain.bottomRightTalon.setSelectedSensorPosition(0, 0, 0);
	}	
	
	// public static void updateSmartDashboard(){
	// 	SmartDashboard.putNumber("Left Pos(M)", getLeftDistance());
	// 	SmartDashboard.putNumber("Right Pos(M)", getRightDistance());
	// 	SmartDashboard.putNumber("Elev Position", Robot.elevator._elevatorMotor.getSelectedSensorPosition(0));     
	// 	SmartDashboard.putNumber("Elev Velocity", Robot.elevator._elevatorMotor.getSelectedSensorVelocity(0));
		
	// 	SmartDashboard.putNumber("Target Heading", Robot.accumulatedHeading);		
	// 	SmartDashboard.putNumber("Robot Heading", gyro.getAngle());
	// 	SmartDashboard.putNumber("Left Velocity",-Robot.drivetrain.frontLeftTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);
	// 	SmartDashboard.putNumber("Right Velocity",-Robot.drivetrain.frontRightTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);
	// }
}
