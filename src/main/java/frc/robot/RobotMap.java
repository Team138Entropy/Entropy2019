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
	
	public final static int ROLLER_TALON_ID = 6;

	// Other mappings
	public static final int PISTON_SOLENDTID_CHANNEL = 0;

	public final static int ELEVATOR_PORT 				= 5; //TBD
	public final static int TURRET_PORT					= 0; //TBD
									   
	public final static int CARGO_SENSOR                = 0; //TBD
	public final static int LEFT_TURRET_LIMIT_SWITCH  	= 7; //TBD
	public final static int CENTER_TURRET_LIMIT_SWITCH  = 8; //TBD
	public final static int RIGHT_TURRET_LIMIT_SWITCH   = 9; //TBD

}
