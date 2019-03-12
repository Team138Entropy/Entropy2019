package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.AcquisitionRoller.RollerState;

public class StartRollerSequence extends CommandGroup {

    public StartRollerSequence() {
        addParallel(new ExtendRoller());
        addParallel(new SetRoller(RollerState.INTAKE));
    }
}
