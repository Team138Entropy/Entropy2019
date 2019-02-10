package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public final class OI {
	
	// Xbox Controller Map
	static final int xboxController = 0;
	// Xbox Buttons
	static final int xboxA = 1;
	static final int xboxB = 2;
	static final int xboxX = 3;
	static final int xboxY = 4;
	static final int xboxLeftBumper = 5;
	static final int xboxRightBumper = 6;
	static final int xboxLeftStick = 7;
	static final int xboxRightStick = 8;
	static final int xboxMenu = 9;
	static final int xboxView = 10;
	static final int xboxHome = 11;
	static final int xboxDpadUp = 12;
	static final int xboxDpadDown = 13;
	static final int xboxDpadLeft = 14;
	static final int xboxDpadRigt = 15;
	
	//Xbox axes
	static final int xboxLeftXAxis = 0;
	static final int xboxLeftYAxis = 1;
	static final int xboxLeftTriggerAxis = 2;
	static final int xboxRightTriggerAxis = 3;
	static final int xboxRightXAxis = 4;
	static final int xboxRightYAxis = 5;
	
	// Nyko Air Flow Controller Map
	static final int nykoController = 1;
	// Nyko buttons
	static final int nykoButton1 = 1;
	static final int nykoButton2 = 2;
	static final int nykoButton3 = 3;
	static final int nykoButton4 = 4;
	static final int nykoLeftBumper = 5;
	static final int nykoRightBumper = 6;
	static final int nykoLeftTrigger = 7;
	static final int nykoRightTrigger = 8;
	static final int nykoMiddle9 = 9;
	static final int nykoMiddle10 = 10;
	static final int nykoMiddle11 = 11;
	static final int nykoLeftStick = 12;
	static final int nykoRightStick = 13;
	
	// Nyko axes
	static final int nykoLeftXAxis = 0;		// X Axis on Driver Station
	static final int nykoLeftYAxis = 1;		// Y Axis on Driver Station
	static final int nykoRightYAxis = 2;	// Z Axis on Driver Station
	static final int nykoRightXAxis = 3;	// Rotate Axis on Driver Station
	
    static Joystick driverStick = new Joystick(xboxController);
    static Joystick operatorStick = new Joystick(nykoController);
    
    static double lastX=0;
    static double LastY=0;
	
	static Button headlessButton = new JoystickButton(driverStick, xboxLeftBumper);

    public OI(){
    	
	}
    
	public static double getMoveSpeed()
	{
		// joystick values are opposite to robot directions
		double moveSpeed=driverStick.getRawAxis(xboxLeftYAxis);
		// Apply thresholds to joystick positions to eliminate
		// creep motion due to non-zero joystick value when joysticks are 
		// "centered"
		if (Math.abs(moveSpeed) < Constants.JoystickDeadband)
			moveSpeed=0;
		return moveSpeed;
	}
	
	public static double getRotateSpeed()
	{
		double rotateSpeed=driverStick.getRawAxis(xboxRightXAxis);
		if (Math.abs(rotateSpeed) < Constants.JoystickDeadband)
			rotateSpeed=0;
		return rotateSpeed;
	}

	public static double getStickX() {
		double x = driverStick.getRawAxis(xboxLeftXAxis);

		if (Math.abs(x) < Constants.joystickDeadband) {
			x = 0;
		}

		return x;
	}

	public static double getStickY() {
		double y = driverStick.getRawAxis(xboxLeftYAxis);

		if (Math.abs(y) < Constants.joystickDeadband) {
			y = 0;
		}
		
		return y;
	}

	public static boolean isHeadless() {
		return headlessButton.get();
	}
	
	public static boolean isReverse() {
		return driverStick.getRawButton(xboxB);
	}
	
	public static boolean isFullSpeed() {
		// We don't use a freaking transmission, so just return false
		return false;

		// But if we did...
		// return driverStick.getRawAxis(xboxRightTriggerAxis) > Constants.highSpeedModeTriggerThreshold;
	}
	
	public static boolean isQuickturn() {
		return driverStick.getRawAxis(xboxLeftTriggerAxis) > Constants.highSpeedModeTriggerThreshold;
	}
	    
} // :D)))

