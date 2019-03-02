package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StopRollerSequence extends CommandGroup {

    public StopRollerSequence() {
        addParallel(new RetractRoller());
        addParallel(new StopRoller());
    }
}
