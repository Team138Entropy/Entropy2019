package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;

public class DeployCargo extends CommandGroup {

    public DeployCargo() {
        addSequential(new UnlatchCargo());
        addSequential(new TranslateManipulator(Constants.EXTEND));
        addSequential(new Wait(Constants.DEPLOY_DELAY));
        addSequential(new Rotate(Constants.VERTICAL));
        addSequential(new Wait(Constants.PNEUMATIC_DELAY));
        addSequential(new TranslateManipulator(Constants.RETRACT));
        addSequential(new Rotate(Constants.HORIZONTAL));
    }
}