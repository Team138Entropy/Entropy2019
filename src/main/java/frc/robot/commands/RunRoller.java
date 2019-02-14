package frc.robot.commands;

import frc.robot.Robot;

import frc.robot.subsystems.AquisitionRoller.PistonState;

import edu.wpi.first.wpilibj.command.Command;

public class RunRoller extends Command {
    
    public RunRoller() {
        requires(Robot.roller);
    }
    
    protected void execute() {
        Robot.roller.set(true);
        Robot.roller.setPistons(PistonState.EXTEND);
    }

    protected boolean isFinished() {
        return false;
    }

	protected void end() {
        Robot.roller.setPistons(PistonState.RETRACT);
        
        // Don't stop the roller right away; we need to make sure we've acquired the cargo.
        try {
            wait(500);
        } catch (InterruptedException e) {}
        Robot.roller.set(false);
	}
}