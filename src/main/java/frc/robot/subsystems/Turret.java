package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.Sensors;
import frc.robot.commands.JogTurret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends Subsystem {
    
    final static int LEFT_POSITION = -1;
    final static int CENTER_POSITION = 0;
    final static int RIGHT_POSITION = 1;

    
    private int _currentPosition = CENTER_POSITION;
    private int _targetPosition;
    private int _direction;
    private int _currentJogDirection = 0;

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
        setDefaultCommand(new JogTurret());
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
				turretTarget = RIGHT_POSITION;		
			break;
		default:
			turretTarget = CENTER_POSITION;
			break;
		}
		return turretTarget;
    }

    public String ConvertTargetToString (int target){
        String targetString = "Error!!";

        switch(target){
            case LEFT_POSITION:
                targetString = "Left";
                break;
            case CENTER_POSITION:
                targetString = "Center";
                break;
            case RIGHT_POSITION:
                targetString = "Right";
                break;
            default:
                targetString = "Error!";
                break;
        } 
        return targetString;
    }


    public void RotateTurret(int target)
    {
        int difference = _targetPosition - _currentPosition;
        
        _targetPosition = target;

        if (difference == 0) {
            _direction = 0;
        }
        else if (difference < 0) {
            _direction = Constants.TurretDirectionLeft;    
        }
        else {
            _direction = Constants.TurretDirectionRight;
        }

    }

    public void RotateLeft() {
        if (_currentPosition > LEFT_POSITION) {
            _targetPosition = _currentPosition - 1;
            _direction = Constants.TurretDirectionLeft;
        }
        else {
            _targetPosition = _currentPosition;
            _direction = 0;
        }

    }

    public void RotateRight() {
        if (_currentPosition < RIGHT_POSITION) {
            _targetPosition = _currentPosition + 1;
            _direction = Constants.TurretDirectionRight;
        }
        else {
            _targetPosition = _currentPosition;
            _direction = 0;
        }

    }
   
    // Start jogging the Turret
	public void JogTurret(int jogDirection, double jogSpeed)
	{
		_currentJogDirection = jogDirection;
		//_turretMotor.set(ControlMode.PercentOutput, jogSpeed * jogDirection);
	}
    
    public void Execute()
    {
        //_turretMotor.set(ControlMode.PercentOutput, Constants.TurretSpeed * _direction);

        if (Sensors.isTurretLeft()) {
            _currentPosition = LEFT_POSITION;
        }
        else if (Sensors.isTurretCenter()) {
            _currentPosition = CENTER_POSITION;
        }
        else if (Sensors.isTurretRight()){
            _currentPosition = RIGHT_POSITION;
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

    public void updateSmartDashboard()
	{                  
        SmartDashboard.putString("Turret Current Position", ConvertTargetToString(_currentPosition));
        //SmartDashboard.putNumber("Turret Current Position", _currentPosition);
		SmartDashboard.putString("Turret Target Position", ConvertTargetToString(_targetPosition));
		SmartDashboard.putNumber("Turret Direction", _direction);
		SmartDashboard.putNumber("Turret Jog Direction", _currentJogDirection);
        SmartDashboard.putNumber("Turret Output:",_turretMotor.getMotorOutputPercent());
        
	}
}
