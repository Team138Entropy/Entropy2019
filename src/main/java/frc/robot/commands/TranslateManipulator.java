package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class TranslateManipulator extends InstantCommand {

    boolean translation = false;

    public TranslateManipulator (boolean translate) {
        requires(Robot.manipulator);
        translation = translate;
    }

    public void execute() {
        Robot.manipulator.translate(translation);
    }
}