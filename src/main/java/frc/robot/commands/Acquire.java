package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Elevator.ElevatorTarget;

public class Acquire extends CommandGroup {

    private Manipulator manip;

    public Acquire() {
        requires(Robot.manipulator);

        addSequential(new ElevateToTarget(ElevatorTarget.LEVEL_1_ACQUIRE));
        addSequential(new Rotate(Constants.VERTICAL));
        addSequential(new ToggleManipTranslation());
        addSequential(new ElevateToTarget(ElevatorTarget.LEVEL_1));
    }
}