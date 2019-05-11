package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.AcquisitionRoller;

public class DeployCargo extends CommandGroup {

    public DeployCargo() {
        addSequential(new UnlatchCargo());
        addSequential(new RetractRoller());
        addSequential(new SetRoller(AcquisitionRoller.RollerState.EJECT));
    }
}