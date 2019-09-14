package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class RotateCargoManipulator extends InstantCommand {

    boolean translation = false;

    public RotateCargoManipulator (boolean translate) {
        requires(Robot.manipulator);
        translation = translate;
    }

    public void execute() {
        Robot.manipulator.rotateCargoManipulator(translation);
    }
}