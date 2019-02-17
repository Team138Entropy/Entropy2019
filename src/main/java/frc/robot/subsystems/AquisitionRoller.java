package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.Sensors;

import frc.robot.commands.RunRoller;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class AquisitionRoller extends Subsystem {
    private static WPI_TalonSRX rollerTalon = new WPI_TalonSRX(RobotMap.ROLLER_TALON_ID);
    private static Solenoid pistonSolenoid = new Solenoid(RobotMap.PISTON_SOLENDTID_CHANNEL);

    public enum PistonState {
        EXTEND, RETRACT;
    }

    private static PistonState currentState = PistonState.RETRACT;

    protected void initDefaultCommand() {
        setDefaultCommand(new RunRoller());
    }

    public synchronized void set(boolean on) {
        if (Sensors.isCargoPresent()) {
            rollerTalon.set(ControlMode.PercentOutput, 0.0d);
            return;
        }
        
        // Because this is experimental, I'm not sure if we want to be running the talon at full speed.
        rollerTalon.set(ControlMode.PercentOutput, on ? 0.5d : 0.0d);
    }

    /**
     * This is intentionally set up as if it were for a DoubleSolenoid,
     * because at the time of writing it wasn't clear how we were going to
     * retract the roller. This is just a handy abstraction.
     */
    public synchronized void setPistons(PistonState ps) {
        if (currentState == ps)
            return;

        if (Sensors.isCargoPresent()) {
            pistonSolenoid.set(false);
            return;
        }
        
        switch (ps) {
            case EXTEND:
                pistonSolenoid.set(true);
                break;
            case RETRACT:
                pistonSolenoid.set(false);
                break;
        }
    }

    public PistonState togglePistons() {
        return currentState = (currentState == PistonState.EXTEND) ? PistonState.EXTEND : PistonState.RETRACT;
    }
}