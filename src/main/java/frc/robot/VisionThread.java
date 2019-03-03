package frc.robot;

import org.opencv.core.Mat;

/*
import org.opencv.core.Core;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import org.opencv.imgproc.Imgproc;
*/

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;

/**
 * Singleton {@link java.lang.Thread Thread} for handling driver vision
 */
public class VisionThread extends Thread {
        
    // Input stream reference goes here
    private CvSink inputStream;

    // Output stream goes here
    private static CvSource outputStream;

    // Temporary space for the current frame
    private static Mat frameBuffer = new Mat();

    // Initialization code goes here
    public VisionThread() {
        CameraServer.getInstance().addAxisCamera(Constants.rearCameraLabel, Constants.rearCameraHostname);

        inputStream  = CameraServer.getInstance().getVideo(Constants.rearCameraLabel);
        outputStream  = CameraServer.getInstance().putVideo("Rear Camera", 320, 240);
    }

    // The singleton mechanism
    private static VisionThread thread;
    public static VisionThread getInstance() {
        if (thread == null) {
            thread = new VisionThread();
        }
        return thread;
    }

    @Override
    public void run() {

        while (true) {

            // Shove a frame into the buffer
            inputStream.grabFrame(frameBuffer);

            // Make sure the frame isn't empty because everyone will die if it is.
            if (frameBuffer.empty())
                continue;

            // Yeet it back at the driver station
            outputStream.putFrame(frameBuffer);
        }

    }
}