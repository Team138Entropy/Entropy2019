package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.DTI;
import frc.robot.Constants;
import frc.robot.DTI;
import frc.robot.subsystems.AquisitionRoller.PistonState;

import edu.wpi.first.wpilibj.command.Command;

public class RunRoller extends Command {
    
    public RunRoller() {
        requires(Robot.roller);
    }
    
    protected void execute() {
        // We take the opposite of the value because up = negative y on the joysticks
        if (-DTI.operatorStick.getRawAxis(DTI.NykoController.leftYAxis) > Constants.CloseLoopJoystickDeadband) {
            Robot.roller.set(true);
            Robot.roller.setPistons(PistonState.EXTEND);
        }
    }

    protected boolean isFinished() {
        return false;
    }

	protected void end() {
        Robot.roller.setPistons(PistonState.RETRACT);
        Robot.roller.set(false);
	}
}