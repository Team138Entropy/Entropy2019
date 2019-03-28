package frc.robot;
/*
 * Constant values used throughout robot code.
 * In "C" this would be a header file, but alas, this is Java
 */
public class Constants {

	// System Constants
	public static double commandLoopIterationSeconds = 0.020;
		
	public static boolean practiceBot = false;

	public final static double CloseLoopJoystickDeadband = 0.2;
	
	// This is our encoder constant for distance (in METERS) per  encoder pulse
	// 6" Wheels, 15:45 chain drive; 256 encoder counts per drive sprocket rotation
	public final static double MetersPerPulse = Math.PI*6*.0254*15/45/256;
	public final static double SecondsTo100Milliseconds = 0.1;

	// TEST ONLY
	
	public final static int LeftDriveEncoderPolarity = -1;
	public final static int RightDriveEncoderPolarity = 1;
	
	// 2019 Drive Train constants
	public final static double FullSpeed = 0.75;
	public final static double ClimbSpeed = 0.75;

	// Elevator
	public final static double elevatorHomingSpeed = -0.2;
	public final static double elevatorJogSpeed = 0.5;
	public final static double elevatorMoveSpeed = 1.0;
	public final static double elevatorDownMoveSpeed = 0.7;
	public final static double elevatorHoldSpeed = 0.05;
	public final static double elevatorExchangeSpeed = 0.5;
	public final static int elevatorUp = 1; 
	public final static int elevatorDown = -1;

	
	// Turret
	public final static double TurretSpeed = 0.5; //TBD
	public final static double TurretJogSpeed = 0.25; //TBD
	public final static int TurretDirectionLeft = -1;
	public final static int TurretDirectionRight = 1;
	public final static int TurretCenterPosition = 0;
	public final static int TurretLeftPosition = -1;
	public final static int TurretRightPosition = 1;

		

	// Controller stuff
	public static final int nykoControllerPort = 0;
    public static final int xboxControllerPort = 1;
	
	// Stuff that was deleted that I had to paste back in from the 2018 code
	public final static int zeroDelay= 60; // Approx 40/sec;
	public final static double highSpeedModeTriggerThreshold = 0.3;

	// Temporary values I had to put in before compiling
	public static final boolean isSimulated = false;

	// Pneumatics
	public static final boolean EXTEND = true;
	public static final boolean RETRACT = false;

	public static final boolean VERTICAL = true;
	public static final boolean HORIZONTAL = false;

	public static final double ROLLER_SPEED = 0.65;

	// Manipulator
	public static final float CARGO_SENSOR_THRESHOLD = 375;
	public static final float OVERCURRENT_THRESHOLD = 12;
	public static final double OVERCURRENT_REVERSE_ROLLER_DELAY = 0.25;
	public static final double DEPLOY_DELAY = 0.25;
	public static final double PNEUMATIC_DELAY = 0.5;
	public static final int HATCH_PANEL_UP = 150; // TODO: Fine Tune
	public static final int HATCH_PANEL_DOWN = -100; // TODO: Fine Tune


	// Climber 
	public static final int CLIMB_PROXIMITY_THRESHOLD = 1000;

}