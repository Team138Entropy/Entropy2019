package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;

import edu.wpi.first.wpilibj.command.Command;

public class RunRoller extends Command {
    
    
    public RunRoller() {
        requires(Robot.aquisitionRoller);
    }
    
    protected void execute() {
        // Note: we should use the left trigger to activate the roller.
        
        // Not quite sure if this will work. Haven't read the docs yet.
        // ¯\_(ツ)_/¯
        Robot.aquisitionRoller.set(OI.operatorStick.getRawButton(OI.nykoLeftTrigger));
    }

    protected boolean isFinished() {
        return false;
    }

	protected void end() {
	}

	protected void interrupted() {
	}
}