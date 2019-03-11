package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;

public class DeployCargo extends CommandGroup {

    public DeployCargo() {
        addSequential(new UnlatchCargo());
        addSequential(new TranslateManipulator(Constants.EXTEND));
        addSequential(new Rotate(Constants.VERTICAL));
        addSequential(new Wait(0.5));
        addSequential(new DefaultPosition());
    }
}