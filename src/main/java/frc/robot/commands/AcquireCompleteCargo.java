package frc.robot.commands;

import frc.robot.commands.DefaultPosition;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

public class AcquireCompleteCargo extends InstantCommand {
    protected void execute() {
        Scheduler.getInstance().add(new DefaultPosition());
    }
}