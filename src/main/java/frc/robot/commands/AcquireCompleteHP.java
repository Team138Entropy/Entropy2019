package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator.ElevatorTarget;

public class AcquireCompleteHP extends CommandGroup {

    public AcquireCompleteHP() {
        requires(Robot.manipulator);

        addSequential(new ElevateToTarget(ElevatorTarget.SAFE));
        //addSequential(new Rotate(Constants.VERTICAL));
        //addSequential(new ToggleManipTranslation());
        addSequential(new ElevateToTarget(ElevatorTarget.LEVEL_1));
    }
}