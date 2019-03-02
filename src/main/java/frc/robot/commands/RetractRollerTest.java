package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class RetractRollerTest extends InstantCommand {

    public RetractRollerTest() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setPistons(false);
    }
}