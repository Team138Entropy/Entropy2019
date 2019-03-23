package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class TranslateHatchPanel extends InstantCommand {

    boolean translation = false;

    public TranslateHatchPanel (boolean translate) {
        requires(Robot.manipulator);
        translation = translate;
    }

    public void execute() {
        Robot.manipulator.translateHatchPanel(translation);
    }
}