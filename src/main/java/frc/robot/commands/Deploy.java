package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class Deploy extends InstantCommand {

    public Deploy() {
        requires(Robot.manipulator);
    }

    public void execute() {
        Robot.sequenceCoordinator.deploy();
    }
}