package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class ReacquireCargo extends CommandGroup {

    public ReacquireCargo() {
        addSequential(new ReverseRoller());
        addSequential(new Wait(0.5));
        addSequential(new ReverseRoller());
    }
}