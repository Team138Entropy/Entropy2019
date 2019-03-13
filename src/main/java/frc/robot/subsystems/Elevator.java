package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.Sensors;
import frc.robot.commands.JogElevator;

public class Elevator extends Subsystem{

	public WPI_TalonSRX _elevatorMotor = new WPI_TalonSRX(RobotMap.ELEVATOR_PORT);
	
	// Servo Loop Gains
	double _liftKf = 0.2;
	double _liftKp = 1;
	double _liftKi = 0;
	double _liftKd = 5; 
	
	// Talon SRX/ Victor SPX will support multiple (cascaded) PID loops
	// For now we just want the primary one.
	public static final int kElevatorPIDLoopIndex = 0;
	
	// Elevator motion command timeout
	public static final int kElevatorTimeoutMs = 10;



	public enum ElevatorTarget{
		FLOOR(            // Panels↓  ↓Cargo
				new TargetEnumData(0, 0), // <- Practice
				new TargetEnumData(0, 0)  // <- Competition
		),
		LOADING_STATION(
				new TargetEnumData(100, 100),
				new TargetEnumData(100, 100)
		),
		LEVEL_1(
				new TargetEnumData(200, 280),
				new TargetEnumData(300, 200)
		),
		SAFE(
				new TargetEnumData(350, 350),
				new TargetEnumData(100, 100)
		),
		LEVEL_2(
				new TargetEnumData(830, 1350),
				new TargetEnumData(500, 400)
		),
		LEVEL_3(
				new TargetEnumData(1870, 2300),
				new TargetEnumData(700, 600)
		);

		private static class TargetEnumData {

			private double panelHeight, cargoHeight;

			public TargetEnumData(final double panelHeight, final double cargoHeight) {
				this.panelHeight = panelHeight;
				this.cargoHeight = cargoHeight;
			}

			public double get() {
				return Sensors.isCargoPresent() ? this.cargoHeight : this.panelHeight;
			}
		}

		public TargetEnumData practice, competition;

		ElevatorTarget(TargetEnumData practice, TargetEnumData competition) {
			this.practice = practice;
			this.competition = competition;
		}
	}

	private static int _count = 0;

	private int _direction = 0;		// 0: not moving to target, -1 or 1 moving to target in that direction

	private double _targetPosition = 0.0;
	private double _currentPosition = 0.0;
	private boolean _isAtFloor = true;
	private ElevatorTarget _targetNamedPosition = null;
	private int _currentJogDirection = 0;
	
	public void ElevatorInit() {
		// initial direction is 0, since elevator is not moving
		_direction = 0;
		
		/* set the peak and nominal outputs, 12V means full */
		_elevatorMotor.configNominalOutputForward(0, kElevatorTimeoutMs);
		_elevatorMotor.configNominalOutputReverse(0, kElevatorTimeoutMs);
		_elevatorMotor.configPeakOutputForward(1, kElevatorTimeoutMs);
		_elevatorMotor.configPeakOutputReverse(-1, kElevatorTimeoutMs);
		
		_elevatorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, kElevatorPIDLoopIndex, kElevatorTimeoutMs);
		_elevatorMotor.setSensorPhase(false);
		
		/* set the allowable closed-loop error,
		 * Closed-Loop output will be neutral within this range.
		 * See Table in Section 17.2.1 for native units per rotation. 
		 */
		_elevatorMotor.configAllowableClosedloopError(0, kElevatorPIDLoopIndex, kElevatorTimeoutMs); /* always servo */
		
		_elevatorMotor.config_kF(kElevatorPIDLoopIndex, _liftKf, kElevatorTimeoutMs);
		_elevatorMotor.config_kP(kElevatorPIDLoopIndex, _liftKp, kElevatorTimeoutMs);
		_elevatorMotor.config_kI(kElevatorPIDLoopIndex, _liftKi, kElevatorTimeoutMs);
		_elevatorMotor.config_kD(kElevatorPIDLoopIndex, _liftKd, kElevatorTimeoutMs);

		// Set current limit on elevator motor Talon
		// current limit applies to current drawn from battery
		// limit of 20 amps implies 12*20 = 240 Watts max power drawn from
		// battery.  Actual motor current at stall is sqrt(Watts/R)
		// 775 Motor resistance ~0.15 Ohms.  So 20 Amps input equates to 40 Amps in motor
		// at Stall.
// Current limiting disabled as it causes the moves to operate at reduced power
//		_elevatorMotor.configContinuousCurrentLimit(20, 5000);
//		_elevatorMotor.configPeakCurrentLimit(30, 2000);
//		_elevatorMotor.enableCurrentLimit(true);
		
		// Set brake mode to hold at position
		_elevatorMotor.setNeutralMode(NeutralMode.Brake);		
		
		// Integral control only applies when the error is small; this avoids integral windup
		_elevatorMotor.config_IntegralZone(0, 200, kElevatorTimeoutMs);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new JogElevator());
	}

	public ElevatorTarget getTargetPosition() {
		return _targetNamedPosition;
	}
	
	// Convert the target string to an ElevatorTarget
	public ElevatorTarget ConvertToTarget(String target) {
		ElevatorTarget elevatorTarget;
		
		switch (target) {
		case "Loading Station":
		elevatorTarget = ElevatorTarget.LOADING_STATION;
		break;

		case "Floor":
			elevatorTarget = ElevatorTarget.FLOOR;
			break;

		case "Level 1":
				elevatorTarget = ElevatorTarget.LEVEL_1;		
			break;
		case "Level 1 Acquire":
				elevatorTarget = ElevatorTarget.SAFE;
				break;
		case "Level 2":
				elevatorTarget = ElevatorTarget.LEVEL_2;
			break;
		case "Level 3":
				elevatorTarget = ElevatorTarget.LEVEL_3;
			break;
		default:
			elevatorTarget = ElevatorTarget.LEVEL_2;
			break;
		}
		return elevatorTarget;
	}
	
	// Start jogging the elevator
	public void JogElevator(int jogDirection, double jogSpeed)
	{
		_currentJogDirection = jogDirection;
		_elevatorMotor.set(ControlMode.PercentOutput, jogSpeed * jogDirection);
	}
	
	// Start homing the elevator
	public void HomeElevator()
	{
		_elevatorMotor.set(ControlMode.PercentOutput, Constants.elevatorHomingSpeed);
	}

	public void ElevateRelative (int distance) {
		_targetPosition = GetElevatorPosition() + distance;
		initiateMove();
	}
	
	// Elevate to a specific target position
	public void Elevate (ElevatorTarget target) {
		_targetNamedPosition = target;
		_isAtFloor = false;
		if (target == null) {
			StopMoving();
		}
		else
		{
			if (target == ElevatorTarget.FLOOR) _isAtFloor = true;

			_targetPosition = Sensors.isPracticeBot() ?
					target.practice.get()
					:
					target.competition.get();

			initiateMove();
		}
	}

	private void initiateMove() {
		double elevatorSpeed;
			
		_currentPosition = GetElevatorPosition();
		
		if (_targetPosition > _currentPosition) {
			_direction = Constants.elevatorUp;
			elevatorSpeed = Constants.elevatorMoveSpeed;
			_elevatorMotor.set(ControlMode.PercentOutput , _direction * elevatorSpeed);
		}
		else {
			_direction = Constants.elevatorDown;
			elevatorSpeed =  Constants.elevatorDownMoveSpeed;
			_elevatorMotor.set(ControlMode.PercentOutput , _direction * elevatorSpeed);
		}
	}
	
	// Return the elevator position in encoder counts
	public double GetElevatorPosition() {
		if (Constants.isSimulated)
		{
			return (_targetPosition);
		}
		else
		{
			return _elevatorMotor.getSelectedSensorPosition(kElevatorPIDLoopIndex);
		}
	}
	
	public boolean IsAtFloor() {
		return _isAtFloor;
	}
	
	// Execute to move
	public void Execute() {
		_currentPosition = GetElevatorPosition();
		
		if (_direction != 0) {
			// Monitor distance to Goal
			if ( (_direction > 0 && (_currentPosition > _targetPosition) ||
			     (_direction < 0 && (_currentPosition < _targetPosition) )))
			{
				_elevatorMotor.set(ControlMode.MotionMagic, _targetPosition);
				System.out.println("Setting jog direction to 0...");
				_direction = 0;
			}
		}
	}
	
	public void updateSmartDashboard()
	{                  
		SmartDashboard.putNumber("Current Position", GetElevatorPosition());
		SmartDashboard.putString("Target Named Position", convertTargetPositionToString(_targetNamedPosition));
		SmartDashboard.putNumber("Target Position", _targetPosition);
		SmartDashboard.putNumber("Direction", _direction);
		SmartDashboard.putNumber("Jog Direction", _currentJogDirection);
		SmartDashboard.putNumber("Elevate Output:",_elevatorMotor.getMotorOutputPercent());
		SmartDashboard.putNumber("Count", _count);
	}
	
	private String convertDirectionToString (int direction) {
		if (_direction == -1) {
			return "Down";
		}	
		else if (_direction == 1) {
			return "Up";
		}
		else {
			return "";
		}
	}

	private String convertTargetPositionToString (ElevatorTarget targetNamedPosition) {
		String targetString = "Error!";

		if (targetNamedPosition == null) {
			targetString =  "None";
		}
		else {

			switch (targetNamedPosition){
				case FLOOR:
					targetString = "Floor";
					break;
				case LEVEL_1:
					targetString = "Level 1";
					break;
				case SAFE:
					targetString = "Level 1 Acquire";
					break;
				case LEVEL_2:
					targetString = "Level 2";
					break;
				case LEVEL_3:
					targetString = "Level 3";
					break;
				case LOADING_STATION:
					targetString = "Loading Station";
					break;
				default:
					targetString = "Error!";
					break;
			}
		}
		return targetString; 
	}


	// Stop the homing move, reset the encoder position 
	public void StopHoming()
	{
		_elevatorMotor.set(ControlMode.PercentOutput, 0);
		_elevatorMotor.setSelectedSensorPosition(0, 0, 0);
	}
	
	// Interface to let command know it's done
	public boolean IsMoveComplete() {
		return (_direction == 0);
	}
	
	// Cancel the current elevator move, but don't stop the motion immediately
	// This occurs when another move command is about to start with a new target position
	public void CancelMove() {
		_targetPosition = _currentPosition;
	}
	
	// Stop the current elevator move immediately
	public void StopMoving()
	{
		if (!_isAtFloor)
		{
			_elevatorMotor.set(ControlMode.PercentOutput, Constants.elevatorHoldSpeed);
			_direction = 0;
		}
		else
		{
			_elevatorMotor.set(ControlMode.PercentOutput, Constants.elevatorHoldSpeed);
			_direction = 0;			
		}
	}
}
