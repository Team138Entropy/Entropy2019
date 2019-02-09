package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.Sensors;

public class Turret extends Subsystem {
    
    final static int LEFT_POSITION = -1;
    final static int CENTER_POSITION = 0;
    final static int RIGHT_POSTITION = 1;

    
    private int _currentPosition = CENTER_POSITION;
    private int _targetPosition;
    private int _direction;

    private WPI_TalonSRX _turretMotor = new WPI_TalonSRX(RobotMap.TURRET_PORT);
 
   

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
        int difference = _targetPosition - _currentPosition;
        
        _targetPosition = target;

        if (difference == 0) {
            _direction = 0;
        }
        else if (difference < 0) {
            _direction = -1;    
        }
        else {
            _direction = 1;
        }

    }
    
    public void Execute()
    {
        _turretMotor.set(ControlMode.PercentOutput, Constants.TurretSpeed * _direction);

        if (Sensors.isTurretLeft()) {
            _currentPosition = LEFT_POSITION;
        }
        else if (Sensors.isTurretCenter()) {
            _currentPosition = CENTER_POSITION;
        }
        else if (Sensors.isTurretRight()){
            _currentPosition = RIGHT_POSTITION;
        }

        if (IsMoveComplete()) {
            StopMoving();
        }
    
    }

    public boolean IsMoveComplete()
    {
        return (_currentPosition == _targetPosition);
    }

    public void StopMoving()
    {
        _turretMotor.set(ControlMode.PercentOutput,0);
    }

    public void CancelMove()
    {
        _turretMotor.set(ControlMode.PercentOutput,0);
    }
}
