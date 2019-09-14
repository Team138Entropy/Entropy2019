package frc.robot.events;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.commands.SetRoller;
import frc.robot.subsystems.AcquisitionRoller.RollerState;

public class RightOperatorStickBackward implements Event {

    public boolean check() {
        return (OI.operatorStick.getRawAxis(OI.NykoController.rightYAxis) > Constants.OperatorJoystickDeadband);
    }

    public Command getCommand() {
        return new SetRoller(RollerState.STOP);
    }
}
