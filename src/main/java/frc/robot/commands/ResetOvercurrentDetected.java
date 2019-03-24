package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Sensors;

public class ResetOvercurrentDetected extends InstantCommand {

    public ResetOvercurrentDetected () {

    }

    public void execute() {
        Sensors.resetOvercurrentDetected();
    }
}