package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Sensors;

public class UnlatchCargo extends InstantCommand {

    public UnlatchCargo () {

    }

    public void execute() {
        Sensors.unlatchCargo();
    }
}