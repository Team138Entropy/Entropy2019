package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Deploy extends Command {

    public Deploy() {
        requires(Robot.manipulator);
        Robot.manipulator.deploy();
    }

    public boolean isFinished() {
        return false;
    }
}