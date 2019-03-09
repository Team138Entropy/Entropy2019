package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class Deploy extends CommandGroup {

    public Deploy() {
        requires(Robot.manipulator);
        addSequential(new ToggleManipTranslation());
    }
}