package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ChangeState extends Command {

    public ChangeState() {
        requires(Robot.manipulator);
        Robot.manipulator.changeState();
    }

    public boolean isFinished() {
        return false;
    }
}