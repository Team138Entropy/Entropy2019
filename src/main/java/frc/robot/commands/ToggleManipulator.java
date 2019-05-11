package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class ToggleManipulator extends InstantCommand {

    public ToggleManipulator() {
        requires(Robot.manipulator);
    }

    public void execute () {
        Robot.manipulator.actuate(!Robot.manipulator.getWristDown());
    }
}