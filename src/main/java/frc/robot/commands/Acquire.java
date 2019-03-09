package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator.ElevatorTarget;

public class Acquire extends CommandGroup {

    public Acquire() {
        requires(Robot.manipulator);

        addSequential(new Rotate(Constants.VERTICAL));
        addSequential(new ToggleManipTranslation());
        addSequential(new ElevateToTarget(ElevatorTarget.LEVEL_1));
    }
}