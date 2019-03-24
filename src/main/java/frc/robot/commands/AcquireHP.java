package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator.ElevatorTarget;
import frc.robot.commands.*;

public class AcquireHP extends CommandGroup {

    public AcquireHP() {
        requires(Robot.manipulator);

        addSequential(new TranslateHatchPanel(Constants.EXTEND));
        addSequential(new Wait(Constants.PNEUMATIC_DELAY));
        addSequential(new ElevateRelative(Constants.HATCH_PANEL_UP));
        addSequential(new TranslateHatchPanel(Constants.RETRACT));
    }
}