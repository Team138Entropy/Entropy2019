package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator.ElevatorTarget;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevateToTarget extends Command {
	
	private ElevatorTarget elevatorTarget;
	private final double commandTimeoutSeconds = 7;
	private double _currentCommandTime = 0;
	
	public ElevateToTarget(ElevatorTarget target)
	{
		requires(Robot.elevator);
		elevatorTarget = target;
	}
	

	protected void initialize() {
		
		// Supports elevate to scale with hook interference
		//if (elevatorTarget == ElevatorTarget.LOWER_SCALE || elevatorTarget == ElevatorTarget.UPPER_SCALE) {
//			Robot.grasper.lowerWrist();
	//	}
		
		Robot.elevator.Elevate(elevatorTarget);
		_currentCommandTime = 0;
		}

	protected void execute() {
		Robot.elevator.Execute();
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
			if (Robot.elevator.IsMoveComplete()) {
				System.out.println("ElevateTo <" + elevatorTarget.toString() + "> finished successfully");
			}
			return Robot.elevator.IsMoveComplete();
		}
	}

	protected void end() {
		// Robot.elevator.StopMoving();
		
		// Supports release from scale
	//	if (elevatorTarget == ElevatorTarget.LOWER_SCALE || elevatorTarget == ElevatorTarget.UPPER_SCALE) {
		//	Robot.grasper.raiseWrist();
	//	}		
	}

	protected void interrupted() {
		
		Robot.elevator.CancelMove();
	}

}