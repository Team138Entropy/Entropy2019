package frc.robot;

import frc.robot.subsystems.DriverVision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import org.opencv.imgproc.Imgproc;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;

/**
 * Singleton {@link java.lang.Thread Thread} for handling driver vision
 */
public class VisionThread extends Thread {
        
    // Input stream references go here
    private CvSink frontCameraInputStream, rearCameraInputStream;

    private CvSink inputStream;
    private DriverVision.Camera currentCamera;

    // Output stream goes here
    private static CvSource outputStream;

    // Temporary space for the current frame
    private static Mat frameBuffer = new Mat();

    // Initialization code goes here
    public VisionThread() {
        frontCameraInputStream = CameraServer.getInstance().getVideo(Constants.frontCameraLabel);
        rearCameraInputStream  = CameraServer.getInstance().getVideo(Constants.rearCameraLabel);
        
        currentCamera = DriverVision.Camera.FRONT;
        inputStream   = frontCameraInputStream;
        outputStream  = CameraServer.getInstance().putVideo("Unified Camera", 320, 240);
    }

    // The singleton mechanism
    private static VisionThread thread;
    public static VisionThread getInstance() {
        if (thread == null) {
            thread = new VisionThread();
        }
        return thread;
    }

    // Reassign the input stream, but only if it's actually been changed
    public synchronized void switchToCamera(DriverVision.Camera camera) {
        if (currentCamera == camera)
            return;
        
        switch (camera) {
            case FRONT:
                currentCamera = DriverVision.Camera.FRONT;
                inputStream = frontCameraInputStream;
                break;
            case REAR:
                currentCamera = DriverVision.Camera.REAR;
                inputStream = rearCameraInputStream;
                break;
        }
    }

    @Override
    public void run() {

        while (true) {

            // Shove a frame into the buffer
            inputStream.grabFrame(frameBuffer);

            // Make sure the frame isn't empty because everyone will die if it is.
            if (frameBuffer.empty())
                continue;

            // Annotate the frame
            Imgproc.putText(
                frameBuffer,
                (currentCamera == DriverVision.Camera.FRONT) ? "Front" : "Rear",
                new Point(5, 5),
                Core.FONT_HERSHEY_COMPLEX_SMALL,
                1,
                new Scalar(255, 255, 255),
                3
            );

            // Yeet it back at the driver station
            outputStream.putFrame(frameBuffer);
        }

    }
}