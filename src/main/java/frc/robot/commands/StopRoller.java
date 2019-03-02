package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class StopRoller extends InstantCommand {

    public StopRoller() {
        requires(Robot.roller);
    }

    public void execute() {
        Robot.roller.setRoller(false);
    }
}
