package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class AcquisitionRoller extends Subsystem {
    private static WPI_TalonSRX rollerTalon = new WPI_TalonSRX(RobotMap.ROLLER_TALON_ID);
    private static Solenoid pistonSolenoid = new Solenoid(RobotMap.ACQUISITION_PISTON_SOLENOID_CHANNEL);

    protected void initDefaultCommand() {
    }

    protected void init() {
        pistonSolenoid.set(false);
        rollerTalon.set(ControlMode.PercentOutput, 0.0d);
    }

    public synchronized void setRoller(boolean on) {
        rollerTalon.set(ControlMode.PercentOutput, on ? Constants.ROLLER_SPEED : 0.0d);
    }

    public synchronized void setPistons(boolean ps) {
        if (pistonSolenoid.get() == ps)
            return;
        else
            pistonSolenoid.set(ps);
    }

    public void togglePistons() {
        setPistons(!pistonSolenoid.get());
    }

    public void toggleRoller() {
        boolean isOn = (rollerTalon.getMotorOutputPercent() > 0);
        setRoller (!isOn);
    }
}
