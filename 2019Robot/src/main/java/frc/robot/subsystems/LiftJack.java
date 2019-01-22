package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.LowerJack;
import frc.robot.commands.RaiseJack;
import frc.robot.Constants;

public class LiftJack extends Subsystem {

    private Solenoid _liftSolenoid = new Solenoid(RobotMap.SOLENOID_LIFT_PORT);

    public void Initialize()
    {
        SmartDashboard.putString("LiftState", "Raised");
        SmartDashboard.putData("lower", new LowerJack());
        SmartDashboard.putData("raise", new RaiseJack());
        SmartDashboard.putNumber("liftspeed", 0);
    }

    public void initDefaultCommand() {

    }

    public void lowerJack() {
        SmartDashboard.putString("LiftState", "Lowered");
        _liftSolenoid.set(Constants.jackSolenoidLowered);
        SmartDashboard.putNumber("liftspeed",60);
    }

    public void raiseJack() {
        SmartDashboard.putString("LiftState", "Raised");
        _liftSolenoid.set(!Constants.jackSolenoidLowered);
        SmartDashboard.putNumber("liftspeed", 0);
    }
}