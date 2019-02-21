package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class RunRoller extends Command {
    
    public RunRoller() {
        requires(Robot.roller);
    }
    
    protected void execute() {
        // We take the opposite of the value because up = negative y on the joysticks
        if (-OI.operatorStick.getRawAxis(OI.NykoController.leftYAxis) > Constants.CloseLoopJoystickDeadband) {
            Robot.roller.setRoller(true);
            Robot.roller.setPistons(Constants.EXTEND);
        } else {
            Robot.roller.setRoller(false);
            Robot.roller.setPistons(Constants.RETRACT);
        }
    }

    protected boolean isFinished() {
        return false;
    }

	protected void end() {
        Robot.roller.setPistons(Constants.RETRACT);
        Robot.roller.setRoller(false);
	}
}