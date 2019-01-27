package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator.ElevatorTarget;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevateToAlternateTarget extends Command {
	
	private final double commandTimeoutSeconds = 7;
	private double _currentCommandTime = 0;
	
	public ElevateToAlternateTarget()
	{
		requires(Robot.elevator);
	}
	
	protected void initialize() {
		// Supports elevate to scale with hook interference
		//if (Robot.elevator.getAlternateTarget() == ElevatorTarget.LOWER_SCALE || Robot.elevator.getAlternateTarget() == ElevatorTarget.UPPER_SCALE) {
			//Robot.grasper.lowerWrist();
		//}
		Robot.elevator.ElevateToAlternateTarget();
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
			return Robot.elevator.IsMoveComplete();
		}
	}

	protected void end() {
		Robot.elevator.StopMoving();
		
		// Supports release from scale
		//if (Robot.elevator.getAlternateTarget() == ElevatorTarget.LOWER_SCALE || Robot.elevator.getAlternateTarget() == ElevatorTarget.UPPER_SCALE) {
		//	Robot.grasper.raiseWrist();
		//}	
	}

	protected void interrupted() {
		
		Robot.elevator.CancelMove();
	}

}