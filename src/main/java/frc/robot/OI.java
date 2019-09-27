package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

import frc.robot.Constants;
import frc.robot.events.EventWatcherThread;
import frc.robot.events.LeftOperatorStickForward;
import frc.robot.events.LeftOperatorStickBackward;
import frc.robot.events.RightOperatorStickBackward;
import frc.robot.events.OverCurrentDetected;
import frc.robot.subsystems.Elevator.ElevatorTarget;
import frc.robot.commands.*;

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

	static Button homeElevatorButton = new JoystickButton(operatorStick, NykoController.middle11);
	static Button elevateToLevel1    = new JoystickButton(operatorStick, NykoController.button1);
	static Button elevateToLevel2    = new JoystickButton(operatorStick, NykoController.button2);
    static Button elevateToLevel3    = new JoystickButton(operatorStick, NykoController.button4);
    
	// static Button rotateTurretLeft   = new JoystickButton(operatorStick, NykoController.leftBumper);
    // static Button rotateTurretRight  = new JoystickButton(operatorStick, NykoController.rightBumper);
    
	static Button pistonTestButton   = new JoystickButton(operatorStick, NykoController.middle9);
	static Button cargoRotateTestButton   = new JoystickButton(operatorStick, NykoController.middle10);
	static Button climbPistonButton  = new JoystickButton(driverStick, XboxController.rightBumper);
	static Button defaultPositions   = new JoystickButton(operatorStick, NykoController.button3);

	static Button acquireButton = new JoystickButton(operatorStick, NykoController.leftTrigger);
	static Button deployButton = new JoystickButton(operatorStick, NykoController.rightTrigger);

	static Button acquireCargoButton = new JoystickButton(operatorStick, NykoController.leftStick);
	static Button deployCargoButton = new JoystickButton(operatorStick, NykoController.rightStick);

    public OI(){
		homeElevatorButton.whileHeld(new HomeElevator());
		defaultPositions.whenPressed(new DefaultPosition());
		elevateToLevel1.whenPressed(new ElevateToTarget(ElevatorTarget.LEVEL_1));
		elevateToLevel2.whenPressed(new ElevateToTarget(ElevatorTarget.LEVEL_2));
		elevateToLevel3.whenPressed(new ElevateToTarget(ElevatorTarget.LEVEL_3));

		acquireButton.whenPressed(new AcquireHP());
        deployButton.whenPressed(new DeployHP());
        
		// rotateTurretLeft.whenPressed(new RotateTurretLeft());
		// rotateTurretRight.whenPressed(new RotateTurretRight());

		acquireCargoButton.whenPressed(new AcquireCargo());
        deployCargoButton.whenPressed(new DeployCargo());
		
		// Testing / individual component operation
        pistonTestButton.whenPressed(new ToggleRollerPistons());
        
        cargoRotateTestButton.whenPressed(new ToggleCargoManipulator());
        

		climbPistonButton.whenPressed(new ExtendClimbPiston());
        climbPistonButton.whenReleased(new RetractClimbPiston());
        
        EventWatcherThread.getInstance().addEvent(new LeftOperatorStickForward());
        EventWatcherThread.getInstance().addEvent(new LeftOperatorStickBackward());
        EventWatcherThread.getInstance().addEvent(new RightOperatorStickBackward());
        EventWatcherThread.getInstance().addEvent(new OverCurrentDetected());
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

    // Return the jog direction: 1 for up, -1 for down
    public static int getElevatorJogDirection() {
        // POV hat returns 0 for up
        if (operatorStick.getPOV() == 0) {
            return Constants.elevatorUp;
        }
        // POV hat returns 180 for down
        else if (operatorStick.getPOV() == 180) {
            return Constants.elevatorDown;
        } else {
            return 0;
        }
    }

    // Return the jog direction: 1 for up, -1 for down
    public static int getTurretJogDirection() {
        if (Constants.practiceBot) {
            // POV hat returns 90 for left on the practice bot
            if (operatorStick.getPOV() == 90) {
                return Constants.TurretDirectionLeft;
            }
            // POV hat returns 270 for right on the practice bot
            else if (operatorStick.getPOV() == 270) {
                return Constants.TurretDirectionRight;
            } else {
                return 0;
            }
        } else {
            // POV hat returns 90 for right
            if (operatorStick.getPOV() == 90) {
                return Constants.TurretDirectionRight;
            }
            // POV hat returns 270 for left
            else if (operatorStick.getPOV() == 270) {
                return Constants.TurretDirectionLeft;
            } else {
                return 0;
            }
        }
    }


} // :D)))

