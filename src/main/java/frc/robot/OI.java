package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import frc.robot.Constants;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public final class OI {

    public static class NykoController extends Joystick {

        // Buttons
        public static final int button1 = 1;
        public static final int button2 = 2;
        public static final int button3 = 3;
        public static final int button4 = 4;
        public static final int leftBumper = 5;
        public static final int rightBumper = 6;
        public static final int leftTrigger = 7;
        public static final int rightTrigger = 8;
        public static final int middle9 = 9;
        public static final int middle10 = 10;
        public static final int middle11 = 11;
        public static final int leftStick = 12;
        public static final int rightStick = 13;

        // Axes
        public static final int leftXAxis = 0;        // X Axis on Driver Station
        public static final int leftYAxis = 1;        // Y Axis on Driver Station
        public static final int rightYAxis = 2;    // Z Axis on Driver Station
        public static final int rightXAxis = 3;    // Rotate Axis on Driver Station

        public NykoController(int port) {
            super(port);
        }

    }

    public static class XboxController extends Joystick {
        // Buttons
        static final int a = 1;
        static final int b = 2;
        static final int x = 3;
        static final int y = 4;
        static final int leftBumper = 5;
        static final int rightBumper = 6;
        static final int leftStick = 7;
        static final int rightStick = 8;
        static final int menu = 9;
        static final int view = 10;
        static final int home = 11;
        static final int dPadUp = 12;
        static final int dPadDown = 13;
        static final int dPadLeft = 14;
        static final int dPadRigt = 15;

        // Axes
        static final int leftXAxis = 0;
        static final int leftYAxis = 1;
        static final int leftTriggerAxis = 2;
        static final int rightTriggerAxis = 3;
        static final int rightXAxis = 4;
        static final int rightYAxis = 5;

        public XboxController(int port) {
            super(port);
        }
    }

    public static Joystick driverStick = new Joystick(Constants.xboxControllerPort);
    public static Joystick operatorStick = new Joystick(Constants.nykoControllerPort);

    static double lastX = 0;
    static double LastY = 0;

    public OI(){

    }
    
	public static double getMoveSpeed()
	{
		// joystick values are opposite to robot directions
		double moveSpeed = driverStick.getRawAxis(XboxController.leftYAxis);
		// Apply thresholds to joystick positions to eliminate
		// creep motion due to non-zero joystick value when joysticks are 
		// "centered"
		if (Math.abs(moveSpeed) < Constants.CloseLoopJoystickDeadband)
			moveSpeed=0;
		return moveSpeed;
	}
	
	public static double getRotateSpeed()
	{
        double rotateSpeed;
        
        if (Constants.practiceBot) {
            rotateSpeed = driverStick.getRawAxis(XboxController.rightXAxis);
        }
        else {
            rotateSpeed = -1 * driverStick.getRawAxis(XboxController.rightXAxis);
        }
        
		if (Math.abs(rotateSpeed) < Constants.CloseLoopJoystickDeadband)
			rotateSpeed=0;
		return rotateSpeed;
	}
	
	public static boolean isReverse() {
		return driverStick.getRawButton(XboxController.b);
	}
	
	public static boolean isFullSpeed() {
		// We don't use a freaking transmission, so just return false
		return false;

        // But if we did...
        // return driverStick.getRawAxis(xboxRightTriggerAxis) > Constants.highSpeedModeTriggerThreshold;
    }

    public static boolean isQuickturn() {
        return driverStick.getRawAxis(XboxController.leftTriggerAxis) > Constants.highSpeedModeTriggerThreshold;
    }

} // :D)))

