package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.Constants;

public class DeployHP extends CommandGroup {

    public DeployHP() {
        requires(Robot.manipulator);
        
        addSequential(new TranslateHatchPanel(Constants.EXTEND));
        addSequential(new Wait(Constants.PNEUMATIC_DELAY));
        addSequential(new ElevateRelative(Constants.HATCH_PANEL_DOWN));
        addSequential(new TranslateHatchPanel(Constants.RETRACT));
    }
}