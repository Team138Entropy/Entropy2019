package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.AcquisitionRoller;

public class DeployCargo extends Command {

    boolean done = false;

    @Override
    protected void execute() {
        Robot.roller.setPistons(false);
        Robot.roller.setRoller(AcquisitionRoller.RollerState.EJECT);

        done = true;
    }

    @Override
    protected boolean isFinished() {
        return done;
    }
}