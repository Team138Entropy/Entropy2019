package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
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

	private static UsbCamera frontCamera;

	public static DigitalInput practiceRobotJumperPin;
	public static AnalogInput cargoSensor;

	public static DigitalInput leftLimitSwitch;
    public static DigitalInput centerLimitSwitch;
	public static DigitalInput rightLimitSwitch;
	
	public static AnalogInput climbProximitySensor;

	private static final int requiredDebounceCount = 50;
	private static int currentDebounceCount = 0;
	private static final int requiredOverCurrentDebounceCount = 50;
	private static int currentOverCurrentDebounceCount = 0;
	public static boolean isCargoDetectionEnabled = false;
	private static boolean isCargoDetected = false;
	private static boolean isOverCurrentDetected = false;
	
	static {
		Robot.drivetrain.bottomLeftTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		Robot.drivetrain.bottomRightTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

		try {
			CameraServer.getInstance().addAxisCamera(Constants.rearCameraLabel, Constants.rearCameraHostname);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		frontCamera = CameraServer.getInstance().startAutomaticCapture("frontCamera", 0);
		frontCamera.setVideoMode(PixelFormat.kMJPEG, 320, 240, 30);

        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        gyro.reset();
	   
		practiceRobotJumperPin = new DigitalInput(RobotMap.PRACTICE_ROBOT_JUMPER);
		cargoSensor = new AnalogInput(RobotMap.CARGO_SENSOR);
		leftLimitSwitch = new DigitalInput(RobotMap.LEFT_TURRET_LIMIT_SWITCH);
    	centerLimitSwitch = new DigitalInput(RobotMap.CENTER_TURRET_LIMIT_SWITCH);
		rightLimitSwitch = new DigitalInput(RobotMap.RIGHT_TURRET_LIMIT_SWITCH);
		climbProximitySensor = new AnalogInput(RobotMap.CLIMB_PROXIMiTY_SENOR);
	}
	
	public static void debounce()
	{
		debounceCargoSensor();
		debounceOverCurrent();
	}

	private static void debounceCargoSensor() {
		if (isCargoDetectionEnabled)
		{
			if (cargoSensor.getValue() > Constants.CARGO_SENSOR_THRESHOLD)
			{
				currentDebounceCount++;
				System.out.println("debounce " + currentDebounceCount);
			}
			else{
				currentDebounceCount = 0;
			}

			if (currentDebounceCount > requiredDebounceCount)
			{
				System.out.println("Cargo!");
				isCargoDetectionEnabled = false;	
				isCargoDetected = true;
			}
		}
		else
		{
			currentDebounceCount = 0;
		}
	}

	private static void debounceOverCurrent() {
		if (isCargoDetectionEnabled)
		{
			if (Robot.roller.getRollerCurrent() > Constants.OVERCURRENT_THRESHOLD)
			{
				currentOverCurrentDebounceCount++;
				System.out.println("debounce overcurrent " + currentOverCurrentDebounceCount);
			}
			else{
				currentOverCurrentDebounceCount = 0;
			}

			if (currentOverCurrentDebounceCount > requiredOverCurrentDebounceCount)
			{
				System.out.println("Overcurrent!");
				isOverCurrentDetected = true;
			}
		}
		else {
			currentOverCurrentDebounceCount = 0;
		}
	}

	public static double getLeftDistance() {
		// In METERS
		return -Robot.drivetrain.bottomLeftTalon.getSelectedSensorPosition(0) * Constants.MetersPerPulse;
	}

	public static double getRightDistance() {
		// In METERS
		return -Robot.drivetrain.bottomRightTalon.getSelectedSensorPosition(0) * Constants.MetersPerPulse;
	}

	public static void resetEncoders() {
		Robot.drivetrain.bottomLeftTalon.setSelectedSensorPosition(0, 0, 0);
		Robot.drivetrain.bottomRightTalon.setSelectedSensorPosition(0, 0, 0);
	}

	public static void updateSmartDashboard() {
		SmartDashboard.putBoolean("Practice Bot", isPracticeBot());
		SmartDashboard.putBoolean("Raw Cargo Present", cargoSensor.getValue() > Constants.CARGO_SENSOR_THRESHOLD);
		SmartDashboard.putBoolean("Cargo Present", isCargoPresent());
		SmartDashboard.putNumber("Cargo Proximity Value", cargoSensor.getValue());
		SmartDashboard.putBoolean("Turret Left", isTurretLeft());
		SmartDashboard.putBoolean("Turret Right", isTurretRight());
		SmartDashboard.putBoolean("Turret Center", isTurretCenter());
		SmartDashboard.putNumber("Proximity Value", climbProximitySensor.getValue());
		SmartDashboard.putBoolean("Proximity", isClimbProximityThresholdReached());
		SmartDashboard.putNumber("Roller Current", Robot.roller.getRollerCurrent()); 
		SmartDashboard.putBoolean("Roller Overcurrent", isOverCurrent());
	// 	SmartDashboard.putNumber("Left Pos(M)", getLeftDistance());
	// 	SmartDashboard.putNumber("Right Pos(M)", getRightDistance());
	// 	SmartDashboard.putNumber("Elev Position", Robot.elevator._elevatorMotor.getSelectedSensorPosition(0));     
	//	SmartDashboard.putNumber("Elev Velocity", Robot.elevator._elevatorMotor.getSelectedSensorVelocity(0));
		
	// 	SmartDashboard.putNumber("Target Heading", Robot.accumulatedHeading);		
	// 	SmartDashboard.putNumber("Robot Heading", gyro.getAngle());
	// 	SmartDashboard.putNumber("Left Velocity",-Robot.drivetrain.frontLeftTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);
	// 	SmartDashboard.putNumber("Right Velocity",-Robot.drivetrain.frontRightTalon.getSelectedSensorVelocity(0)*10*Constants.MetersPerPulse);
	 }

	 public static void detectAndLatchCargo()
	 {
		isCargoDetectionEnabled = true;
	 }

	 public static void unlatchCargo()
	 {
		isCargoDetected = false;
	 }

	public static boolean isCargoPresent (){
		return (isCargoDetected);
	}

	public static boolean isOverCurrent (){
		return (isOverCurrentDetected);
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
