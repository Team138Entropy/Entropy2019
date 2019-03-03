package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class SwitchCamera extends Command {

    public SwitchCamera() {
        //requires(Robot.driverVision);
    }

    protected void execute() {
        /*
        if (OI.operatorStick.getRawButtonPressed(OI.NykoController.rightTrigger))
            Robot.driverVision.switchToCamera(DriverVision.Camera.REAR);
        else if (OI.operatorStick.getRawButtonReleased(OI.NykoController.rightTrigger))
            Robot.driverVision.switchToCamera(DriverVision.Camera.FRONT);
            */
    }

    protected boolean isFinished() {
        return false;
    }
}