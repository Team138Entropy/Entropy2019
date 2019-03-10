package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.AcquisitionRoller.AcquisitionState;

public class SetAcquireCargoState extends InstantCommand {

    AcquisitionState _acquisitionState = AcquisitionState.IDLE;

    public SetAcquireCargoState (AcquisitionState acquisitionState) {
        requires(Robot.roller);
        _acquisitionState = acquisitionState;
    }

    public void execute() {
        Robot.roller.acquisitionState = _acquisitionState;
    }
}