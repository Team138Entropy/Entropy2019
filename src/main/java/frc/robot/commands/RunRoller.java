package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;

import frc.robot.subsystems.AquisitionRoller.PistonState;

import edu.wpi.first.wpilibj.command.Command;

public class RunRoller extends Command {
    private static boolean rollerOn = false;

    
    public RunRoller() {
        requires(Robot.roller);
    }
    
    protected void execute() {
        rollerOn = OI.operatorStick.getRawButton(OI.NykoController.leftTrigger);

        if (rollerOn) {
            Robot.roller.setPistons(PistonState.EXTEND);
            Robot.roller.set(true);
        } else {
            Robot.roller.setPistons(PistonState.RETRACT);
        }
    }

    protected boolean isFinished() {
        return false;
    }

	protected void end() {
	}

	protected void interrupted() {
	}
}