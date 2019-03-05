package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Actuate;
import frc.robot.commands.ElevateToTarget;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Elevator.ElevatorTarget;

public class ManipulatorSequential extends CommandGroup {

    public ManipulatorSequential (boolean tra, boolean rot, ElevatorTarget tar) {
        requires(Robot.sequenceCoordinator);

        addSequential(new Actuate(tra, rot));

        addSequential(new ElevateToTarget(tar));
    }

    public ManipulatorSequential (boolean tra, boolean rot) {
        requires(Robot.sequenceCoordinator);

        addSequential(new Actuate(tra, rot));
    }

    public ManipulatorSequential (ElevatorTarget tar) {
        requires(Robot.sequenceCoordinator);
        
        addSequential(new ElevateToTarget(tar));
    }

}