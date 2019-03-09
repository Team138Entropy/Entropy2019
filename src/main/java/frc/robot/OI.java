package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

import frc.robot.Constants;

import frc.robot.subsystems.Elevator.ElevatorTarget;
import frc.robot.commands.ExtendClimbPiston;
import frc.robot.commands.RetractClimbPiston;
import frc.robot.commands.Acquire;
import frc.robot.commands.ElevateToTarget;
import frc.robot.commands.HomeElevator;
import frc.robot.commands.Deploy;
import frc.robot.commands.ToggleManipRotation;
import frc.robot.commands.ToggleManipTranslation;
import frc.robot.commands.RotateTurretLeft;
import frc.robot.commands.RotateTurretRight;
import frc.robot.commands.ExtendRoller;
import frc.robot.commands.RetractRoller;
import frc.robot.commands.StartRoller;
import frc.robot.commands.StopRoller;
import frc.robot.commands.DefaultPosition;
import frc.robot.commands.ToggleRoller;
import frc.robot.commands.ToggleRollerPistons;

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
        public static final int leftXAxis = 0;		// X Axis on Driver Station
        public static final int leftYAxis = 1;		// Y Axis on Driver Station
        public static final int rightYAxis = 2;	// Z Axis on Driver Station
        public static final int rightXAxis = 3;	// Rotate Axis on Driver Station

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
    
    static double lastX=0;
    static double LastY=0;

	static Button homeElevatorButton = new JoystickButton(operatorStick, NykoController.middle11);
	static Button elevateToLevel1    = new JoystickButton(operatorStick, NykoController.button1);
	static Button elevateToLevel2    = new JoystickButton(operatorStick, NykoController.button2);
	static Button elevateToLevel3    = new JoystickButton(operatorStick, NykoController.button4);
	static Button rotateTurretLeft   = new JoystickButton(operatorStick, NykoController.leftBumper);
	static Button rotateTurretRight  = new JoystickButton(operatorStick, NykoController.rightBumper);
	static Button rollerTestButton   = new JoystickButton(operatorStick, NykoController.leftStick);
	static Button pistonTestButton   = new JoystickButton(operatorStick, NykoController.middle9);
	static Button climbPistonButton  = new JoystickButton(driverStick, XboxController.rightBumper);
	static Button defaultPositions   = new JoystickButton(operatorStick, NykoController.button3);

	static Button acquireButton = new JoystickButton(operatorStick, NykoController.leftTrigger);
	static Button deployButton = new JoystickButton(operatorStick, NykoController.rightTrigger);

	static Button rotateManipulator = new JoystickButton(operatorStick, NykoController.middle10);
	static Button extendManipulator = new JoystickButton(operatorStick, NykoController.rightStick);

    public OI(){
		homeElevatorButton.whileHeld(new HomeElevator());
		defaultPositions.whenPressed(new DefaultPosition());
		elevateToLevel1.whenPressed(new ElevateToTarget(ElevatorTarget.LEVEL_1));
		elevateToLevel2.whenPressed(new ElevateToTarget(ElevatorTarget.LEVEL_2));
		elevateToLevel3.whenPressed(new ElevateToTarget(ElevatorTarget.LEVEL_3));

		acquireButton.whenPressed(new Acquire());
		deployButton.whenPressed(new Deploy());
		rotateTurretLeft.whenPressed(new RotateTurretLeft());
		rotateTurretRight.whenPressed(new RotateTurretRight());
		
		// Testing / individual component operation
		rollerTestButton.whenPressed(new ToggleRoller());
		pistonTestButton.whenPressed(new ToggleRollerPistons());

		rotateManipulator.whenPressed(new ToggleManipTranslation());
		extendManipulator.whenPressed(new ToggleManipRotation());
		climbPistonButton.whenPressed(new ExtendClimbPiston());
		climbPistonButton.whenReleased(new RetractClimbPiston());
	}
    
	public static double getMoveSpeed()
	{
		// joystick values are opposite to robot directions
		double moveSpeed = -1 * driverStick.getRawAxis(XboxController.leftYAxis);
		// Apply thresholds to joystick positions to eliminate
		// creep motion due to non-zero joystick value when joysticks are 
		// "centered"
		if (Math.abs(moveSpeed) < Constants.CloseLoopJoystickDeadband)
			moveSpeed=0;
		return moveSpeed;
	}
	
	public static double getRotateSpeed()
	{
		double rotateSpeed= driverStick.getRawAxis(XboxController.rightXAxis);
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
	public static int getElevatorJogDirection()
	{
		// POV hat returns 0 for up
		if (operatorStick.getPOV() == 0)
		{
			return Constants.elevatorUp;
		}
		// POV hat returns 180 for down
		else if (operatorStick.getPOV() == 180)
		{
			return Constants.elevatorDown;
		}
		else
		{
			return 0;
		}
	}

	// Return the jog direction: 1 for up, -1 for down
	public static int getTurretJogDirection()
	{
		// POV hat returns 90 for right
		if (operatorStick.getPOV() == 90)
		{
			return Constants.TurretDirectionRight;
		}
		// POV hat returns 270 for left
		else if (operatorStick.getPOV() == 270)
		{
			return Constants.TurretDirectionLeft;
		}
		else
		{
			return 0;
		}
	}
	
	
} // :D)))

