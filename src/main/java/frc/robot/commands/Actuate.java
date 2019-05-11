package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class Actuate extends InstantCommand {

    boolean wrist = false;

    public Actuate (boolean wrist) {
        requires(Robot.manipulator);
    }

    public void execute() {
        Robot.manipulator.actuate(wrist);
    }
}