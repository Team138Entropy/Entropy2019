package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;

/**
 * This is the development branch.
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory .
 */
public class Robot extends TimedRobot {
	// Interface with players
        
    // Subsystems
    public static final Compressor compressor = new Compressor();
    public static final Drivetrain drivetrain = new Drivetrain();
    public static final LineDetectorSerial lineDetectorSerial = new LineDetectorSerial();
    public static final Turret turret = new Turret();
    public static final Elevator elevator = new Elevator ();
    public static double accumulatedHeading = 0.0; // Accumulate heading angle (target)
	public static final OI oi = new OI();
    Preferences prefs = Preferences.getInstance();
    
    // Global constants
    public static String mode; // "auto" or "teleop"
    public static String gameData;

    private static void nothing(String a){

    }

    private DigitalInput pin = null;
    private DigitalInput pin2 = null;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	drivetrain.DriveTrainInit();
    	compressor.start();	
        Robot.accumulatedHeading = 0;
        Constants.AutoEnable=true;
        elevator.ElevatorInit();
        turret.TurretInit();
        Constants.practiceBot = isPracticeRobot();
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
        nothing("Your bot is disabled!\n");
    }
	
	public void disabledPeriodic() {
        nothing("Periodic disabled!");
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    	System.err.println("WE DON'T HAVE AUTONOMOUS YOU FOOL!");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        System.err.println("WE DON'T HAVE AUTONOMOUS YOU FOOL!");
    }

    public void teleopInit() {
    	mode = "teleop";
    	//Sensors.resetEncoders();
        Sensors.gyro.reset();
        Robot.accumulatedHeading = 0;
	    Robot.drivetrain.Relax();

		Constants.AutoEnable=true;
		Constants.IntegralError=0;
    }
    
    public static boolean isPracticeRobot() {
    	return (! Sensors.practiceRobotJumperPin.get());
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        elevator.updateSmartDashboard();
        Sensors.updateSmartDashboard();
        turret.updateSmartDashboard();
//		LiveWindow.run();

        Sensors.setResetArduino(OI.resetButton.get());
        Sensors.setCalibrate(OI.calibrateButton.get());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        System.out.println("nothing to see here...");
    }
}
