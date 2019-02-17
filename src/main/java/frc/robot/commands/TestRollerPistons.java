package frc.robot.commands;

import frc.robot.Robot;

import frc.robot.subsystems.AquisitionRoller.PistonState;

import edu.wpi.first.wpilibj.command.Command;

public class TestRollerPistons extends Command {

    public TestRollerPistons() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setPistons(PistonState.EXTEND);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.roller.setPistons(PistonState.RETRACT);
	}
}