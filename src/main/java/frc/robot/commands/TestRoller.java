package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TestRoller extends Command {

    public TestRoller() {
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