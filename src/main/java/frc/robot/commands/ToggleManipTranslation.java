package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class ToggleManipTranslation extends InstantCommand {

    public ToggleManipTranslation() {
        requires(Robot.manipulator);
    }

    public void execute () {
        System.out.println("Toggle wrist command called");
        Robot.manipulator.actuate(!Robot.manipulator.getWristDown());
    }
}