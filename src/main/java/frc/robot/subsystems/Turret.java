package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;

public class Turret extends Subsystem {
    
    
    final static int LEFT_POSITION = 0;
    final static int CENTER_POSITION = 1;
    final static int RIGHT_POSTITION = 2;

    
    public WPI_TalonSRX _turretMotor = new WPI_TalonSRX(RobotMap.TURRET_PORT);
 
    public DigitalInput _leftLimitSwitch = new DigitalInput(RobotMap.LEFT_TURRET_LIMIT_SWITCH);
    public DigitalInput _centerLimitSwitch = new DigitalInput(RobotMap.CENTER_TURRET_LIMIT_SWITCH);
    public DigitalInput _rightLimitSwitch = new DigitalInput(RobotMap.RIGHT_MOTOR_CHANNEL_BOTTOM);

    // Servo Loop Gains
	double _turretKf = 0.2;
	double _turretKp = 1;
	double _turretKi = 0;
	double _turretKd = 5; 
	
	// Talon SRX/ Victor SPX will support multiple (cascaded) PID loops
	// For now we just want the primary one.
	public static final int kTurretPIDLoopIndex = 0;
	
	// Turret motion command timeout
	public static final int kTurretTimeoutMs = 10;
    protected void initDefaultCommand() {
	
    }

    public void TurretInit() {
		// initial direction is 0, since Turret is not moving
		//_direction = 0;
		
		// Set brake mode to hold at position
		_turretMotor.setNeutralMode(NeutralMode.Brake);		
    }

    // Convert the target string to an TurretTarget
	public int ConvertToTarget(String target) {
		int turretTarget;
		
		switch (target) {
		case "Left":
		turretTarget = LEFT_POSITION;
		break;

		case "Center":
			turretTarget = CENTER_POSITION;
			break;

		case "Right":
				turretTarget = RIGHT_POSTITION;		
			break;
		default:
			turretTarget = CENTER_POSITION;
			break;
		}
		return turretTarget;
    }

    public void RotateTurret(int target)
    {

    }
    
    public void Execute()
    {

    }

    public boolean IsMoveComplete()
    {
        //TODO
        return false;
    }

    public void StopMoving()
    {

    }

    public void CancelMove()
    {

    }
}
