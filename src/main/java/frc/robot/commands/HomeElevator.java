package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HomeElevator extends Command {
	
	public HomeElevator()
	{
		requires(Robot.elevator);
	}

	protected void initialize() {
		Robot.elevator.HomeElevator();
	}

	protected void execute() {
		Robot.elevator.Execute();
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.elevator.StopHoming();
		
	}

	protected void interrupted() {
		
		Robot.elevator.StopHoming();
	}

}