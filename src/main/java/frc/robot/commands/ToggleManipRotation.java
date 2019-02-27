package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class ToggleManipRotation extends InstantCommand {

    public ToggleManipRotation() {
        requires(Robot.manipulator);
    }

    public void execute() {
        System.out.println("Rotate command called");
        Robot.manipulator.rotate(!Robot.manipulator.getRotated());
    }
}