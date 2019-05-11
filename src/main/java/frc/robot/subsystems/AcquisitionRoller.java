package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class AcquisitionRoller extends Subsystem {
    private static WPI_TalonSRX rollerTalon = new WPI_TalonSRX(RobotMap.ROLLER_TALON_ID);
    private static Solenoid pistonSolenoid = new Solenoid(RobotMap.ACQUISITION_PISTON_SOLENOID_CHANNEL);

    public enum AcquisitionState {
        IDLE,
        ACQUIRE
    }

    public enum RollerState {
        INTAKE,
        STOP,
        HOLD,
        EJECT
    }

    protected void initDefaultCommand() {

    }

    public void init() {
        pistonSolenoid.set(false);
        rollerTalon.set(ControlMode.PercentOutput, 0.0d);
        rollerTalon.setNeutralMode(NeutralMode.Brake);
    }

    public synchronized void setRoller(final RollerState targetState) {
        boolean on = false;
        
        switch (targetState) {
            case EJECT:
                on = true;
                rollerTalon.setInverted(false);
                break;
            case INTAKE:
                on = true;
                rollerTalon.setInverted(true);
                break;
            case STOP:
                rollerTalon.setInverted(false);
                on = false;
                break;
            case HOLD:
                rollerTalon.setInverted(true);
                rollerTalon.set(ControlMode.PercentOutput, Constants.ROLLER_HOLD_SPEED);
                return;
        }

        rollerTalon.set(ControlMode.PercentOutput, on ? Robot.shuffHandler.getRollerSpeed() : 0.0d);
    }

    public synchronized void setPistons() {

    }

    public synchronized void setPistons(boolean ps) {
        System.out.println("Setting pistons to: " + Boolean.toString(ps) + (ps ? "(EXTEND)" : "(RETRACT)"));
        if (pistonSolenoid.get() != ps)
            pistonSolenoid.set(ps);
    }

    public void togglePistons() {
        System.out.println("togglePistons called");
        setPistons(!pistonSolenoid.get());
    }

    public void toggleRoller() {
        boolean isOn = (rollerTalon.getMotorOutputPercent() > 0);
        setRoller (!isOn ? RollerState.INTAKE : RollerState.STOP);
    }

    public void reverseRoller() {
        boolean isEject = (rollerTalon.getInverted());
        setRoller (isEject ? RollerState.INTAKE : RollerState.EJECT);
    }

    
	public double getRollerCurrent () {
		return rollerTalon.getOutputCurrent();
	}
}
