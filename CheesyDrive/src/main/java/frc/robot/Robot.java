package frc.robot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;

//import edu.wpi.first.wpilibj.Preferences;

/**
 * This is not Master branch.
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory .
 */
public class Robot extends IterativeRobot {
	// Interface with players
        
    // Subsystems
    public static final Compressor compressor = new Compressor();
    public static final Drivetrain drivetrain = new Drivetrain();
    public static double accumulatedHeading = 0.0; // Accumulate heading angle (target)

    public static final OI oi = new OI();
	
    Preferences prefs = Preferences.getInstance();
    
    // Global constants
    public static String mode; // "auto" or "teleop"
    public static String gameData;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	drivetrain.DriveTrainInit();
    	compressor.start();
		Sensors.initialize();		
        Robot.accumulatedHeading = 0;
        Constants.AutoEnable=true;

        Constants.practiceBot = isPracticeRobot();
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {

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
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
    }

    public void teleopInit() {
    	mode = "teleop";
    //	Sensors.resetEncoders();
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
//		LiveWindow.run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
  //      LiveWindow.run();
    }
}
