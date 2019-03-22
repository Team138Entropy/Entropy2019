package frc.robot.events;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Sensors;
import frc.robot.commands.DefaultPosition;
import frc.robot.commands.ReacquireCargo;

public class OverCurrentDetected implements Event {


    public boolean check() {
        return (Sensors.isOverCurrent());
    }

    public Command getCommand() {
        return new ReacquireCargo();
    }

}
