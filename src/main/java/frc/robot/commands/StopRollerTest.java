package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class StopRollerTest extends  InstantCommand {

    public StopRollerTest() {
        requires(Robot.roller);
    }

    public void execute() {
        Robot.roller.setRoller(false);
    }
}
