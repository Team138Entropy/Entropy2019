package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public final class OI {
	
	// Xbox Controller Map
	public static final int xboxController = 0;
	// Xbox Buttons
	public static final int xboxA = 1;
	public static final int xboxB = 2;
	public static final int xboxX = 3;
	public static final int xboxY = 4;
	public static final int xboxLeftBumper = 5;
	public static final int xboxRightBumper = 6;
	public static final int xboxLeftStick = 7;
	public static final int xboxRightStick = 8;
	public static final int xboxMenu = 9;
	public static final int xboxView = 10;
	public static final int xboxHome = 11;
	public static final int xboxDpadUp = 12;
	public static final int xboxDpadDown = 13;
	public static final int xboxDpadLeft = 14;
	public static final int xboxDpadRight = 15;
	
	//Xbox axes
	public static final int xboxLeftXAxis = 0;
	public static final int xboxLeftYAxis = 1;
	public static final int xboxLeftTriggerAxis = 2;
	public static final int xboxRightTriggerAxis = 3;
	public static final int xboxRightXAxis = 4;
	public static final int xboxRightYAxis = 5;
	
	// Nyko Air Flow Controller Map
	public static final int nykoController = 1;
	// Nyko buttons
	public static final int nykoButton1 = 1;
	public static final int nykoButton2 = 2;
	public static final int nykoButton3 = 3;
	public static final int nykoButton4 = 4;
	public static final int nykoLeftBumper = 5;
	public static final int nykoRightBumper = 6;
	public static final int nykoLeftTrigger = 7;
	public static final int nykoRightTrigger = 8;
	public static final int nykoMiddle9 = 9;
	public static final int nykoMiddle10 = 10;
	public static final int nykoMiddle11 = 11;
	public static final int nykoLeftStick = 12;
	public static final int nykoRightStick = 13;
	
	// Nyko axes
	public static final int nykoLeftXAxis = 0;		// X Axis on Driver Station
	public static final int nykoLeftYAxis = 1;		// Y Axis on Driver Station
	public static final int nykoRightYAxis = 2;	// Z Axis on Driver Station
	public static final int nykoRightXAxis = 3;	// Rotate Axis on Driver Station
	
    public static Joystick driverStick = new Joystick(xboxController);
    public static Joystick operatorStick = new Joystick(nykoController);
    
    static double lastX=0;
    static double LastY=0;
    
    public OI(){
    	
	}
    
	public static double getMoveSpeed()
	{
		// joystick values are opposite to robot directions
		double moveSpeed=-driverStick.getRawAxis(xboxLeftYAxis);
		// Apply thresholds to joystick positions to eliminate
		// creep motion due to non-zero joystick value when joysticks are 
		// "centered"
		if (Math.abs(moveSpeed) < Constants.CloseLoopJoystickDeadband)
			moveSpeed=0;
		return moveSpeed;
	}
	
	public static double getRotateSpeed()
	{
		double rotateSpeed=-driverStick.getRawAxis(xboxRightXAxis);
		if (Math.abs(rotateSpeed) < Constants.CloseLoopJoystickDeadband)
			rotateSpeed=0;
		return rotateSpeed;
	}
	
	public static boolean isReverse() {
		return driverStick.getRawButton(xboxB);
	}
	
	public static boolean isFullSpeed() {
		return driverStick.getRawAxis(xboxRightTriggerAxis) > Constants.highSpeedModeTriggerThreshold;
	}
	
	public static boolean isQuickturn() {
		return driverStick.getRawAxis(xboxLeftTriggerAxis) > Constants.highSpeedModeTriggerThreshold;
	}
	    
} // :D)))

