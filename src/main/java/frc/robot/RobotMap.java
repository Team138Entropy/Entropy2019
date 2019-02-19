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

	public final static int ELEVATOR_PORT 				= 7; 
	public final static int ROLLER_TALON_ID             = 6;
	public final static int TURRET_PORT					= 5; 
									   
	// Sensors
	public final static int CARGO_SENSOR                = 5; 
	public final static int LEFT_TURRET_LIMIT_SWITCH  	= 4; 
	public final static int CENTER_TURRET_LIMIT_SWITCH  = 3; 
	public final static int RIGHT_TURRET_LIMIT_SWITCH   = 2; 
	public final static int PRACTICE_ROBOT_JUMPER		= 6;

	// Pneumatics
	public final static int PISTON_SOLENOID_CHANNEL = 0;
}
