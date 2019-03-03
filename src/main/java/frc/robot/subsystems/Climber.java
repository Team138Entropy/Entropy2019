package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.RobotMap;


public class Climber extends Subsystem {

    private static Solenoid pistonSolenoid; 

    public void ClimberInit() {
        pistonSolenoid = new Solenoid(RobotMap.CLIMB_PISTON_SOLENOID_CHANNEL);

     }

    protected void initDefaultCommand() {
    }
    
    public void extendClimbPiston() {
        pistonSolenoid.set(Constants.EXTEND);
        
    }
    
    public void retractClimbPiston() {
        pistonSolenoid.set(Constants.RETRACT);

    }
}