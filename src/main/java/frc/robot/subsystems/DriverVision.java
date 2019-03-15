package frc.robot.subsystems;

import frc.robot.VisionThread;
import frc.robot.Constants;
import frc.robot.commands.SwitchCamera;

import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for handling driver vision
 */
public class DriverVision extends Subsystem {
    
    public static enum Camera {
        FRONT, REAR
    }

    public DriverVision() {
        CameraServer.getInstance().addAxisCamera(Constants.rearCameraLabel, Constants.rearCameraHostname);
        
        VisionThread.getInstance().start();
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new SwitchCamera());
    }

    public synchronized void switchToCamera(Camera camera) {
        //VisionThread.getInstance().switchToCamera(camera);
    }
}