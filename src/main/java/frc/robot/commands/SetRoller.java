package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.AcquisitionRoller.RollerState;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetRoller extends InstantCommand {

    private RollerState targetState;

    public SetRoller(RollerState targetState) {
        this.targetState = targetState;
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setRoller(targetState);
    }
}
