package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ExtendRollerTest extends InstantCommand {
    
    public ExtendRollerTest() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setPistons(true);
    }
}