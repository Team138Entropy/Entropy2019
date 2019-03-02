package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class RetractRoller extends InstantCommand {

    public RetractRoller() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setPistons(Constants.RETRACT);
    }
}