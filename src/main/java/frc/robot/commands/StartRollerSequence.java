package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartRollerSequence extends CommandGroup {

    public StartRollerSequence() {
        addParallel(new ExtendRoller());
        addParallel(new StartRoller());
    }
}
