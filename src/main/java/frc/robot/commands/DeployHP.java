package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class DeployHP extends CommandGroup {

    public DeployHP() {
        requires(Robot.manipulator);
        addSequential(new ToggleManipTranslation());
    }
}