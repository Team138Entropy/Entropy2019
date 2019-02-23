package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Acquire extends Command {

    public Acquire() {
        requires(Robot.manipulator);
        Robot.manipulator.acquire();
    }

    public boolean isFinished() {
        return false;
    }
}