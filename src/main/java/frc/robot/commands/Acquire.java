package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class Acquire extends CommandGroup {

    public Acquire() {
        requires(Robot.manipulator);
    }

    public void execute() {
        //Robot.sequenceCoordinator.acquire();
    }
}