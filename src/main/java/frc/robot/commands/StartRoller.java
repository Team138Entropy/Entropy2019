package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class StartRoller extends InstantCommand {

    public StartRoller() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setRoller(true);
    }
}
