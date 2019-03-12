package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ReverseRoller extends InstantCommand {

    public ReverseRoller() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.reverseRoller();
    }
}
