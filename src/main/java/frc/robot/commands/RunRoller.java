package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;

import frc.robot.subsystems.AquisitionRoller.PistonState;

import edu.wpi.first.wpilibj.command.Command;

public class RunRoller extends Command {
    public RunRoller() {
        requires(Robot.roller);
    }
    
    protected void execute() {
        Robot.roller.set(true);
    }

    protected boolean isFinished() {
        return false;
    }

	protected void end() {
        Robot.roller.set(false);
	}
}