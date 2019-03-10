package frc.robot.events;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.commands.SetRoller;
import frc.robot.subsystems.AcquisitionRoller.RollerState;

public class LeftOperatorStickBackward implements Event {

    public boolean check() {
        return (OI.operatorStick.getRawAxis(OI.NykoController.leftYAxis) > Constants.CloseLoopJoystickDeadband);
    }

    public Command getCommand() {
        return new SetRoller(RollerState.EJECT);
    }
}
