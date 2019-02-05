package frc.robot;
/*
 * Constant values used throughout robot code.
 * In "C" this would be a header file, but alas, this is Java
 */
public class Constants {

	// System Constants
		public static boolean AutoEnable=true;
	
		public static double commandLoopIterationSeconds = 0.020;
			
		public static boolean practiceBot = false;
		
		public static boolean isSimulated = false;
		
		// Deadband applied to Joystick, when
		// magnitude is less than deadBand, then set Magnitude to 0
		public final static double joystickDeadband = 0.09;
		// joystick must be "zero" for zeroDelay iterations before
		// Talon is commanded to 0 volts.  Priot to that, drivetrain Talons
		// remain in closed loop speed control.
		public final static int zeroDelay= 60; // Approx 40/sec;
		
		// Threshold beyond which high speed mode is enabled for trigger
		public final static double highSpeedModeTriggerThreshold = 0.3;
		
		// Low pass filter on joystick heading - 
		// Filter eqn:  heading(i+1) =joystickDir(i)*Alpha + (1-Alpha)*(heading(i)
		// where Alpha = Ts*2*pi*Freq
		//   Ts is sample period (20 mSec for FRC)
		//   Freq is location of filter pole in Hz
		public static double rotateAlpha = .02 * 6.28 * 1;

		//
		// These are autonomous constants useful for the autonomous commands
		//
		
	
		
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
		
		public final static int LeftDriveEncoderPolarity = -1;
		public final static int RightDriveEncoderPolarity = 1;

		public static double IntegralError=0;

		// Dashboard input constants
		public final static String practiceRobot = "practice robot";
		public final static String competitionRobot = "comp robot";

}