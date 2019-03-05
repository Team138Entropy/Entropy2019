package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleRoller extends InstantCommand {

    public ToggleRoller() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.toggleRoller();
    }
}
