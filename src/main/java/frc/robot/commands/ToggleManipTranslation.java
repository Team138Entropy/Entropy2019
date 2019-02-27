package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class ToggleManipTranslation extends InstantCommand {

    public ToggleManipTranslation() {
        requires(Robot.manipulator);
    }

    public void execute () {
        System.out.println("Translate command called");
        Robot.manipulator.translate(!Robot.manipulator.getTranslated());
    }
}