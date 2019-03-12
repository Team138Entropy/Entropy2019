package frc.robot.events;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Sensors;
import frc.robot.commands.DefaultPosition;

public class CargoDetected implements Event {

    int detectionCount;

    public boolean check() {
        return (Sensors.isCargoPresent());
    }

    public Command getCommand() {
        return new DefaultPosition();
    }
}
