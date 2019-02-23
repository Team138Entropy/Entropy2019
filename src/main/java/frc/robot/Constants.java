package frc.robot;
/*
 * Constant values used throughout robot code.
 * In "C" this would be a header file, but alas, this is Java
 */
public class Constants {
	// TODO Clean this up for the 2019 robot.

	// System Constants
	public static boolean AutoEnable=true;

	public static double commandLoopIterationSeconds = 0.020;
		
	public static boolean practiceBot = false;
	
		
	public final static double releaseDelay = 0.5;				 // Seconds
	public final static double softReleaseDelay = 0.25;			 // Seconds
	public final static double acquireDelay = 0.25;				 // Seconds
	public final static double wristDelay = 0.75;
	
	// Drivetrain
	public final static boolean useClosedLoopDrivetrain = true;
	public final static double driveWheelSpacing = (23.65 / 39.37) * 100; // Centimeters (from 22 inches)
	public final static double tempWheelSpeed = 1;
	// Full joystick motion equates to following actual move speeds:
	public final static double ClosedLoopCruiseVelocity = 2.4; // meters / second
	public final static double ClosedLoopSlowVelocity = 1.2; // M/sec
	// Wheel spacing ~0.5 Meters;  For zero Turn, each wheel travels
	// on a circle of circumference of pi*0.5 or 1.57 Meters.
	// For 180 Degree turn in 1 second (180 Degrees/sec), each
	// wheel travels 1/2 Circumference of .785 Meters in 1 second
	public final static double ClosedLoopTurnSpeed = 1; // Meters/sec
	// Allow for slower turn speed when in slow mode,
	public final static double ClosedLoopSlowRotateSpeed = 0.5;
	
	public final static double MaxSpeedChange = 3 * 0.025; // Meters/sec2 * .025 seconds
	public final static double MaxRotateSpeedChange = 3 * 0.025; // Meters/sec2 * .025 seconds
	public final static double MaxSlowSpeedChange = 2 * 0.025;
	public final static double CloseLoopJoystickDeadband = 0.2;
	
	// This is our encoder constant for distance (in METERS) per  encoder pulse
	// 6" Wheels, 15:45 chain drive; 256 encoder counts per drive sprocket rotation
	public final static double MetersPerPulse = Math.PI*6*.0254*15/45/256;
	public final static double SecondsTo100Milliseconds = 0.1;
	
	// public static double Meters2CM = 100.0; // convert distance in Meters to Centimeters
	public static double Meters2CM = 100.0; // convert distance in Meters to Centimeters
	// TEST ONLY
	
	public final static int LeftDriveEncoderPolarity = -1;
	public final static int RightDriveEncoderPolarity = 1;
	
	// 2019 Drive Train constants
	public final static double FullSpeed = 0.75;
	public final static double ClimbSpeed = 0.25;

	// Elevator
	public final static double elevatorHomingSpeed = -0.2;
	public final static double elevatorJogSpeed = 0.5;
	public final static double elevatorMoveSpeed = 1.0;
	public final static double elevatorDownMoveSpeed = 0.7;
	public final static double elevatorHoldSpeed = 0.1;
	public final static double elevatorExchangeSpeed = 0.5;
	public final static int elevatorUp = 1; 
	public final static int elevatorDown = -1;

	
	// Turret
	public final static double TurretSpeed = 0.5; //TBD
	public final static double TurretJogSpeed = 0.25; //TBD
	public final static int TurretDirectionLeft = -1;
	public final static int TurretDirectionRight = 1;
		

	// ***** Autonomous drive parameters *******
	public final static double AutoStraighLineSpeedFactor = 1.0; 				 // Fraction of full autonomous speed
	public static double AutoDriveSpeed = 2.25; //was 2.0 M/sec
	public static double AutoDriveAccel = 1.0; // M/sec2 (1 ~.1G)
	public static double AutoDriveRotateRate = 0.5; // Meters/second
	public static double IntegralError=0;

	// PID gains to control rotation (measured by Gyro)
	public static double kPRotate = .2;
	public static double kIRotate = .2;
	public static double kDRotate = 0.2;
	
	public static double kPDrive = 0.15;
	public static double kIDrive = 0.0;	
	public static double kDDrive = 0.0;
	// Insert delay after each autonomous move to allow
	// mechanism to settle (before sensors are reset at start of next move)
	public static double AutoDrivePause = 2; // 50=1second
	public static double AutoDriveStopTolerance = 2; // CM
	public static double AutoDriveAngleCompensation = .01;

	// Controller stuff
	public static final int nykoControllerPort = 0;
    public static final int xboxControllerPort = 1;

	// Camera stuff
    public static final String frontCameraHostname = "10.1.38.90";
    public static final String rearCameraHostname = "10.1.38.91";
    public static final String frontCameraLabel = "Front Camera";
	public static final String rearCameraLabel = "Rear Camera";
	
	// Stuff that was deleted that I had to paste back in from the 2018 code
	public final static int zeroDelay= 60; // Approx 40/sec;
	public final static double highSpeedModeTriggerThreshold = 0.3;

	// Temporary values I had to put in before compiling
	public static final boolean isSimulated = false;

	// Pneumatics
	public static final boolean EXTEND = true;
	public static final boolean RETRACT = false;

	public static final double ROLLER_SPEED = 0.3;

	// Climber 
	public static final int CLIMB_PROXIMITY_THRESHOLD = 2048;
	
}