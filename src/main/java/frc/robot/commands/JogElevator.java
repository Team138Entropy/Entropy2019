package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.DTI;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class JogElevator extends Command {
	
	private int _jogDirection = 0;
	
	public JogElevator(int direction)
	{
		requires(Robot.elevator);
		_jogDirection = direction;
	}
	
	public JogElevator()
	{
		requires(Robot.elevator);
	}
	

	protected void initialize() {
		// Command was invoked with specific direction
		if (_jogDirection != 0)
		{
			Robot.elevator.JogElevator(_jogDirection, Constants.elevatorJogSpeed);
		}
		// Command was not invoked with specific direction - use D Pad
		else
		{
			Robot.elevator.JogElevator(DTI.getElevatorJogDirection(), Constants.elevatorJogSpeed);
		}
	}

	protected void execute() {
		// Command was invoked with specific direction
		if (_jogDirection != 0)
		{
			Robot.elevator.JogElevator(_jogDirection, Constants.elevatorJogSpeed);
		}
		// Command was not invoked with specific direction - use D Pad
		else
		{
			if (DTI.getElevatorJogDirection() == 0) // && !Robot.elevator.IsAtFloor())
			{
				Robot.elevator.JogElevator(1, Constants.elevatorHoldSpeed);
			}
			else
			{
				Robot.elevator.JogElevator(DTI.getElevatorJogDirection(), Constants.elevatorJogSpeed);
			}
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.elevator.StopMoving();
		
	}

	protected void interrupted() {
		
		Robot.elevator.StopMoving();
	}

}