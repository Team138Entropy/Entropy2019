package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ToggleManipTranslation extends Command {

    public ToggleManipTranslation() {
        requires(Robot.manipulator);
        Robot.manipulator.translate(!Robot.manipulator.getTranslated());
    }

    public boolean isFinished() {
        return false;
    }
}