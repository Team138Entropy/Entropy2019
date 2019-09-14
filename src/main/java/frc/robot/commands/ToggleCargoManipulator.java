package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleCargoManipulator extends InstantCommand {

    public ToggleCargoManipulator() {
        requires(Robot.manipulator);
    }

    protected void execute() {
        Robot.manipulator.toggleCargoManipulator();
    }
}