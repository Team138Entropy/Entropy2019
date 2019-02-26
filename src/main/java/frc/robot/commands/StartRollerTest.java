package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class StartRollerTest extends InstantCommand {

    public StartRollerTest() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setRoller(true);
    }
}
