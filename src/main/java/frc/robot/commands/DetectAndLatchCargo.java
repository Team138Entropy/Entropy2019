package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Sensors;

public class DetectAndLatchCargo extends InstantCommand {

    public DetectAndLatchCargo () {

    }

    public void execute() {
        Sensors.detectAndLatchCargo();
    }
}