package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ExtendRoller extends InstantCommand {

    public ExtendRoller() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setPistons(Constants.EXTEND);
    }
}