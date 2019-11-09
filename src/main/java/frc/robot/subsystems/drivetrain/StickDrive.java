package frc.robot.subsystems.drivetrain;

import frc.robot.OI.FlightStick;
import frc.robot.dto.DriveSignal;

public class StickDrive implements DriveEngine {
    private FlightStick leftStick, rightStick;
    
    public StickDrive(FlightStick leftStick, FlightStick rightStick) {
            this.leftStick = leftStick;
            this.rightStick = rightStick;
    }

    public DriveSignal drive() {
        return new DriveSignal(leftStick.getRawAxis(FlightStick.yAxis), rightStick.getRawAxis(FlightStick.yAxis));
    }
}