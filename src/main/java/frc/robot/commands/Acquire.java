package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class Acquire extends InstantCommand {

    public Acquire() {
        requires(Robot.manipulator);
    }

    public void execute() {
        Robot.sequenceCoordinator.acquire();
    }
}