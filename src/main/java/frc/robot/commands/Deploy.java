package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Manipulator;

public class Deploy extends CommandGroup {

    private Manipulator manip;

    public Deploy() {
        requires(Robot.manipulator);
        addSequential(new ToggleManipTranslation());
    }
}