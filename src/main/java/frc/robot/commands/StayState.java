package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StayState extends Command {

    public StayState() {
        requires(Robot.manipulator);
        Robot.manipulator.stayState();
    }

    public boolean isFinished() {
        return false;
    }
}