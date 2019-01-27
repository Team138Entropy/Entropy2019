package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.commands.RunRoller;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class AquisitionRoller extends Subsystem {
    private static WPI_TalonSRX rollerTalon = new WPI_TalonSRX(Constants.rollerTalon);
    
    protected void initDefaultCommand() {
        setDefaultCommand(new RunRoller());
    }

    public void set(boolean on) {
        // Because this is experimental, I'm not sure if we want to be running the talon at full speed.
        rollerTalon.set(ControlMode.PercentOutput, on ? 0.5 : 0);
    }
}