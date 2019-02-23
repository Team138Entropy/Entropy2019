package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbPiston extends Command {
	
	public ClimbPiston()
	{
		requires(Robot.climber);
	}

	protected void initialize() {
		Robot.climber.extendClimbPiston();
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.climber.retractClimbPiston();
	}

	protected void interrupted() {
		
	}

}