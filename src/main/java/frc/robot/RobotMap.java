package frc.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	// CAN Bus Assignments
	public final static int LEFT_MOTOR_CHANNEL_BOTTOM     = 1;
	public final static int LEFT_MOTOR_CHANNEL_TOP        = 2;
	public final static int RIGHT_MOTOR_CHANNEL_TOP       = 3;
	public final static int RIGHT_MOTOR_CHANNEL_BOTTOM    = 4;

	public final static int TURRET_PORT					= 5; 
	public final static int ROLLER_TALON_ID             = 6;
	public final static int ELEVATOR_PORT 				= 7; 
									   
	// Digital I/O Sensors
	public final static int LEFT_TURRET_LIMIT_SWITCH  	= 4; 
	public final static int CENTER_TURRET_LIMIT_SWITCH  = 3; 
	public final static int RIGHT_TURRET_LIMIT_SWITCH   = 2; 
	public final static int PRACTICE_ROBOT_JUMPER		= 6;

	// Analog I/O Sebsors
	public final static int CLIMB_PROXIMiTY_SENOR 		= 0;
	public final static int CARGO_SENSOR                = 1; 

	// Pneumatics
	public final static int ACQUISITION_PISTON_SOLENOID_CHANNEL = 2;
	public final static int CLIMB_PISTON_SOLENOID_CHANNEL = 3;
	// Rotates the cargo manipulator
	public final static int MANIPULATOR_ROTATION_PORT = 1;
	public final static int HATCH_PANEL_PISTON_SOLENOID_CHANNEL = 4;
	
}
