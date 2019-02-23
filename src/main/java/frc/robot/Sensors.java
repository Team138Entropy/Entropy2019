package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors {
	public static ADXRS450_Gyro gyro; 
	
    static Joystick driverStick = new Joystick(0);
	
	public static SensorCollection leftSensorCollection;
	public static SensorCollection rightSensorCollection;
	
	public static double gyroBias=0;

	public static DigitalInput practiceRobotJumperPin;
	public static DigitalInput cargosensor;

	public static DigitalInput leftLimitSwitch;
    public static DigitalInput centerLimitSwitch;
	public static DigitalInput rightLimitSwitch;
	
	public static AnalogInput climbProximitySensor;
	
	static {
		Robot.drivetrain.bottomLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		Robot.drivetrain.bottomRightTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        gyro.reset();
	   
		practiceRobotJumperPin = new DigitalInput(RobotMap.PRACTICE_ROBOT_JUMPER);
		cargosensor = new DigitalInput(RobotMap.CARGO_SENSOR);
		leftLimitSwitch = new DigitalInput(RobotMap.LEFT_TURRET_LIMIT_SWITCH);
    	centerLimitSwitch = new DigitalInput(RobotMap.CENTER_TURRET_LIMIT_SWITCH);
		rightLimitSwitch = new DigitalInput(RobotMap.RIGHT_TURRET_LIMIT_SWITCH);
		climbProximitySensor = new AnalogInput(RobotMap.CLIMB_PROXIMiTY_SENOR);
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
  
	 public static void updateSmartDashboard(){
		SmartDashboard.putBoolean("Practice Bot", isPracticeBot());
		SmartDashboard.putBoolean("Cargo Present", isCargoPresent());
		SmartDashboard.putBoolean("Turret Left", isTurretLeft());
		SmartDashboard.putBoolean("Turret Right", isTurretRight());
		SmartDashboard.putBoolean("Turret Center", isTurretCenter());
		SmartDashboard.putNumber("Proximity Value", climbProximitySensor.getValue());
		SmartDashboard.putBoolean("Proximity", isClimbProximityThresholdReached());
	// 	SmartDashboard.putNumber("Left Pos(M)", getLeftDistance());
	// 	SmartDashboard.putNumber("Right Pos(M)", getRightDistance());
	// 	SmartDashboard.putNumber("Elev Position", Robot.elevator._elevatorMotor.getSelectedSensorPosition(0));     
	//	SmartDashboard.putNumber("Elev Velocity", Robot.elevator._elevatorMotor.getSelectedSensorVelocity(0));
		
	// 	SmartDashboard.putNumber("Target Heading", Robot.accumulatedHeading);		
	// 	SmartDashboard.putNumber("Robot Heading", gyro.getAngle());
	// 	SmartDashboard.putNumber("Left Velocity",-Robot.drivetrain.frontLeftTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);
	// 	SmartDashboard.putNumber("Right Velocity",-Robot.drivetrain.frontRightTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);
	 }

	public static boolean isCargoPresent(){
		return !cargosensor.get();
	}

	public static boolean isTurretLeft (){
		return !leftLimitSwitch.get();
	}

	public static boolean isTurretCenter (){
		return !centerLimitSwitch.get();
	}

	public static boolean isTurretRight (){
		return !rightLimitSwitch.get();
	}

	public static boolean isPracticeBot() {
		return !practiceRobotJumperPin.get();
	}

	public static boolean isClimbProximityThresholdReached () {
		return (climbProximitySensor.getValue() > Constants.CLIMB_PROXIMITY_THRESHOLD);
	}
}
