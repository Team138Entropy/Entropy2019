package frc.robot.events;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Sensors;
import frc.robot.commands.DefaultPosition;

public class OverCurrentDetected implements Event {


    public boolean check() {
        return (Sensors.isOverCurrent());
    }

    public Command getCommand() {
        System.out.println("Cargo Acquired");
        return new DefaultPosition();
    }

}
