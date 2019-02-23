package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TestRollerPistons extends Command {

    public TestRollerPistons() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setPistons(Constants.EXTEND);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.roller.setPistons(Constants.RETRACT);
	}
}