package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ExtendRoller extends InstantCommand {
    
    public ExtendRoller() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setPistons(true);
    }
}