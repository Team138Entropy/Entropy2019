package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ToggleManipRotation extends Command {

    public ToggleManipRotation() {
        requires(Robot.manipulator);
        Robot.manipulator.rotate(!Robot.manipulator.getRotated());
    }

    public boolean isFinished() {
        return false;
    }
}