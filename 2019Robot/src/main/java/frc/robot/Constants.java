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
		
		//Lift Jack
		public final static boolean jackSolenoidLowered = true;
}