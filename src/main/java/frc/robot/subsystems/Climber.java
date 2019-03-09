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
        System.out.println("Climb Piston EXTEND");
        pistonSolenoid.set(Constants.EXTEND);
        
    }
    
    public void retractClimbPiston() {
        System.out.println("Climb Piston RETRACT");
        pistonSolenoid.set(Constants.RETRACT);

    }
}