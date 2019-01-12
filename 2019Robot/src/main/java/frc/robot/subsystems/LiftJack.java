package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.RobotMap;
import frc.robot.Constants;

public class LiftJack extends Subsystem {

    private Solenoid _liftSolenoid = new Solenoid(RobotMap.SOLENOID_LIFT_PORT);

    public void initDefaultCommand() {

    }

    public void lowerJack() {
        _liftSolenoid.set(Constants.jackSolenoidLowered);
    }

    public void raiseJack() {
        _liftSolenoid.set(!Constants.jackSolenoidLowered);
    }
}