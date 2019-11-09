package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotateTurretRight extends Command {
	
	// Never read
	//private int turretTarget;
	private final double commandTimeoutSeconds = 7;
	private double _currentCommandTime = 0;
	
	public RotateTurretRight(String target){
		requires(Robot.turret);
//		requires(Robot.grasper);

		// No need to update a value that's never read
		//turretTarget = Robot.turret.ConvertToTarget(target);
		}
	
	public RotateTurretRight()
	{
		requires(Robot.turret);
	}
	

	protected void initialize() {
		
		// Supports RotateTurret to scale with hook interference
		//if (turretTarget == TurretTarget.LOWER_SCALE || turretTarget == TurretTarget.UPPER_SCALE) {
//			Robot.grasper.lowerWrist();
	//	}
		
		Robot.turret.RotateRight();
		_currentCommandTime = 0;
		}

	protected void execute() {
		Robot.turret.Execute();
		_currentCommandTime += Constants.commandLoopIterationSeconds;
		SmartDashboard.putNumber("Timer", _currentCommandTime);
	}

	protected boolean isFinished() {
		if (_currentCommandTime >= commandTimeoutSeconds)
		{
			return true;
		}
		else
		{
			return Robot.turret.IsMoveComplete();
		}
	}

	protected void end() {
		Robot.turret.StopMoving();
		
		// Supports release from scale
	//	if (turretTarget == TurretTarget.LOWER_SCALE || turretTarget == TurretTarget.UPPER_SCALE) {
		//	Robot.grasper.raiseWrist();
	//	}		
	}

	protected void interrupted() {
		
		Robot.turret.CancelMove();
	}

}