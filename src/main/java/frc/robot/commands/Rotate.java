package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class Rotate extends InstantCommand {

    boolean rotation = false;

    public Rotate (boolean rotate) {
        requires(Robot.manipulator);
        rotation = rotate;
    }

    public void execute() {
        Robot.manipulator.rotate(rotation);
    }
}