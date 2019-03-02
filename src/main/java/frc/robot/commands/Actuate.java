package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class Actuate extends InstantCommand {

    boolean translation = false;
    boolean rotation = false;

    public Actuate (boolean translate, boolean rotate) {
        requires(Robot.manipulator);
        translation = translate;
        rotation = rotate;
    }

    public void execute() {
        Robot.manipulator.actuate(translation, rotation);
    }
}