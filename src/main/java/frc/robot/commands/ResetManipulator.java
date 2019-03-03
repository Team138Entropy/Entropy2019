package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class ResetManipulator extends InstantCommand {
    public ResetManipulator () {
        requires(Robot.manipulator);
    }

    public void execute () {
        Robot.sequenceCoordinator.reset();
    }
}