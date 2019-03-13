package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class ToggleManipulator extends InstantCommand {

    public ToggleManipulator() {
        requires(Robot.manipulator);
    }

    public void execute () {
        if (Robot.shuffHandler.isPistonRotateEnabled())
        {
            Robot.manipulator.rotate(!Robot.manipulator.getRotated());
        }
        else
        {
            Robot.manipulator.translate(!Robot.manipulator.getTranslated());
        }
    }
}