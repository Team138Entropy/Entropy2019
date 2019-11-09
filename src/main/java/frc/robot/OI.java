package frc.robot;

import java.util.Optional;

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
        // These are package-private because we should only use them in OI to map them to commands
        static final int button1 = 1;
        static final int button2 = 2;
        static final int button3 = 3;
        static final int button4 = 4;
        static final int leftBumper = 5;
        static final int rightBumper = 6;
        static final int leftTrigger = 7;
        static final int rightTrigger = 8;
        static final int middle9 = 9;
        static final int middle10 = 10;
        static final int middle11 = 11;
        static final int leftStick = 12;
        static final int rightStick = 13;

        // Axes
        // These are public because they need to be accessible to events
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
        // These are package-private because we should only use them in OI to map them to commands
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
        // These are public because they need to be accessible to events
        public static final int leftXAxis = 0;
        public static final int leftYAxis = 1;
        public static final int leftTriggerAxis = 2;
        public static final int rightTriggerAxis = 3;
        public static final int rightXAxis = 4;
        public static final int rightYAxis = 5;

        public XboxController(int port) {
            super(port);
        }
    }

    public static class FlightStick extends Joystick {
        // Flight Stick-specific constants
        static final double deadband = 0.1;

        // Buttons
        // These are package-private because we should only use them in OI to map them to commands
        static final int trigger = 0;
        static final int lowerThumb = 1;
        static final int topMiddle = 2;
        static final int topLeft = 3;
        static final int topRight = 4;
        static final int farLeft = 5;
        static final int nearLeft = 6;
        static final int middleLeft = 7;
        static final int middleRight = 8;
        static final int nearRight = 9;
        static final int farRight = 10;

        // Axes
        // These are public because they need to be accessible to events
        public static final int xAxis = 0;
        public static final int yAxis = 1;
        public static final int bottomWheel = 2;

        public FlightStick(int port) {
            super(port);
        }
    }

    public static FlightStick leftDriveStick = new FlightStick(Constants.leftFlightStickPort);
    public static FlightStick rightDriveStick = new FlightStick(Constants.rightFlightStickPort);
    public static NykoController operatorStick = new NykoController(Constants.nykoControllerPort);

    static double lastX = 0;
    static double LastY = 0;
    
    // Buttons are private because we should only use them once to map them to commands.
    // Driver
    private static Button climbPistonButton  = new JoystickButton(rightDriveStick, FlightStick.topMiddle);
    
    // Operator
	private static Button homeElevatorButton = new JoystickButton(operatorStick, NykoController.middle11);
    private static Button elevateToLevel1    = new JoystickButton(operatorStick, NykoController.button1);
    private static Button elevateToLevel2    = new JoystickButton(operatorStick, NykoController.button2);
    private static Button elevateToLevel3    = new JoystickButton(operatorStick, NykoController.button4);
    
	private static Button pistonTestButton   = new JoystickButton(operatorStick, NykoController.middle9);
	private static Button cargoRotateTestButton   = new JoystickButton(operatorStick, NykoController.middle10);
	private static Button defaultPositions   = new JoystickButton(operatorStick, NykoController.button3);

	private static Button acquireButton = new JoystickButton(operatorStick, NykoController.leftTrigger);
	private static Button deployButton = new JoystickButton(operatorStick, NykoController.rightTrigger);

	private static Button acquireCargoButton = new JoystickButton(operatorStick, NykoController.leftBumper);
	private static Button deployCargoButton = new JoystickButton(operatorStick, NykoController.rightBumper);

    OI(){
		homeElevatorButton.whileHeld(new HomeElevator());
		defaultPositions.whenPressed(new DefaultPosition());
		elevateToLevel1.whenPressed(new ElevateToTarget(ElevatorTarget.LEVEL_1));
		elevateToLevel2.whenPressed(new ElevateToTarget(ElevatorTarget.LEVEL_2));
		elevateToLevel3.whenPressed(new ElevateToTarget(ElevatorTarget.LEVEL_3));

		acquireButton.whenPressed(new AcquireHP());
        deployButton.whenPressed(new DeployHP());

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
    
    /*
	public static Double getMoveSpeed()
	{
        if (driverStick.isPresent()) {
            // joystick values are opposite to robot directions
            double moveSpeed = driverStick.get().getRawAxis(XboxController.leftYAxis);
            // Apply thresholds to joystick positions to eliminate
            // creep motion due to non-zero joystick value when joysticks are 
            // "centered"
            if (Math.abs(moveSpeed) < Constants.CloseLoopJoystickDeadband)
                moveSpeed=0;
            return moveSpeed;
        } else {
            return null;
        }
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
    */

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

