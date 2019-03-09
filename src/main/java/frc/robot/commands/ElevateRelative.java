package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevateRelative extends Command {
	
	private int elevatorTarget;
	private final double commandTimeoutSeconds = 7;
	private double _currentCommandTime = 0;
	
	public ElevateRelative(int target)
	{
		requires(Robot.elevator);
		elevatorTarget = target;
	}
	

	protected void initialize() {
		Robot.elevator.ElevateRelative(elevatorTarget);
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
				System.out.println("ElevateTo <" + elevatorTarget + "> finished successfully");
			}
			return Robot.elevator.IsMoveComplete();
		}
	}

	protected void end() {
		Robot.elevator.StopMoving();
	}

	protected void interrupted() {
		
		Robot.elevator.CancelMove();
	}

}