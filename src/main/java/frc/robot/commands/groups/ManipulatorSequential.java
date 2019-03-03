package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Actuate;
import frc.robot.commands.ElevateToTarget;
import frc.robot.subsystems.Elevator.ElevatorTarget;

public class ManipulatorSequential extends CommandGroup {

    public ManipulatorSequential (boolean tra, boolean rot, ElevatorTarget tar) {

        if (tra != false) {
            addSequential(new Actuate(tra, rot));
        }

        if (tar != ElevatorTarget.NONE) {
            addSequential(new ElevateToTarget(tar));
        }
    }

}