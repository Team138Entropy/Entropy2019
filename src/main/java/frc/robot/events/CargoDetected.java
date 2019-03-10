package frc.robot.events;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Sensors;
import frc.robot.commands.DefaultPosition;
import frc.robot.subsystems.AcquisitionRoller.AcquisitionState;;

public class CargoDetected implements Event {

    int detectionCount;

    public boolean check() {
        if (Robot.roller.acquisitionState == AcquisitionState.ACQUIRE && Sensors.isCargoPresent()) {
            detectionCount ++;
        }

        if (detectionCount > 1000)
        {
            detectionCount = 0;
            return true;
        }
        else {
            return false;
        }
    }

    public Command getCommand() {
        return new DefaultPosition();
    }
}
