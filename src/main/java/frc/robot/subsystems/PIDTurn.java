package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Sensors;

/*

 */
public class PIDTurn implements PIDOutput {
    PIDController turnController;
    double rotateToAngleRate = 0.0;

    //used when driving a set distance
    boolean distanceMode = false;
    //stuff from Drive_State.java
    double distance;
    double right_speed;
    double modified_speed;
    double difference;
    // COMPETITION SCALE FACTOR: 0.005
    // PRACTICE SCALE FACTOR: 0.01
    double scale_factor = 0.02;

    double lastRightDistance = 0.0;
    double lastLeftDistance = 0.0;
    int stallCounter = 0;
    boolean areMotorsStalled = false;
    //above code is copied from Drive_State.java

    //************************************************
    //PID CONSTANTS
    //THESE WILL HAVE TO BE TUNED!!!!

    //kP Values for the 2016 boulder robot
    //.03 turning
    //.15 drive straighting

    static double kP = .03;
    //integral coeficient
    static double kI = 0.0;
    static double kD = 0.0;

    //*******************************************

    double driveSpeed = 0.0;
    double driveDistance = 0.0;

    //Degree Tolerance
    //within how many degrees will you be capable of turning
    static double ToleranceDegrees = 2.0;


    //Just Idles
    public PIDTurn(){


    }

    //Drive Stright, for some power and some distance
    public PIDTurn(double driveSpeedarg, double driveDistancearg){
		/*
		Robot.gyro.resetCal();
		driveSpeed = driveSpeedarg;
		driveDistance = driveDistancearg;
		distanceMode = true;
		turnController = new PIDController(kP, kI, kD, Robot.gyro.getGyro(), this);
		turnController.setAbsoluteTolerance(ToleranceDegrees);
		turnController.setInputRange(-360.0f,  360.0f);
	    turnController.setOutputRange(-1.0, 1);
		turnController.setSetpoint(0f);
	    turnController.setContinuous(true);
	    turnController.enable();
		*/

		/*
		NOTE: if below code does not work, try reseting the gyro instead like I did in the comented out code above
		*/

        double d = Sensors.gyro.getAngle();
        float desiredAngleToHold = (float) d;
        driveSpeed = driveSpeedarg;
        driveDistance = driveDistancearg;
        distanceMode = true;
        turnController = new PIDController(kP, kI, kD, Sensors.gyro, this);
        turnController.setAbsoluteTolerance(ToleranceDegrees);
        turnController.setInputRange(-360.0f,  360.0f);
        turnController.setOutputRange(-1.0, 1);
        turnController.setSetpoint(desiredAngleToHold);
        turnController.setContinuous(true);
        turnController.enable();

    }

    public PIDTurn(double driveSpeedarg, double driveDistancearg, double p, double i, double d){
		/*
		Robot.gyro.resetCal();
		driveSpeed = driveSpeedarg;
		driveDistance = driveDistancearg;
		distanceMode = true;
		turnController = new PIDController(kP, kI, kD, Robot.gyro.getGyro(), this);
		turnController.setAbsoluteTolerance(ToleranceDegrees);
		turnController.setInputRange(-360.0f,  360.0f);
	    turnController.setOutputRange(-1.0, 1);
		turnController.setSetpoint(0f);
	    turnController.setContinuous(true);
	    turnController.enable();
		*/

		/*
		NOTE: if below code does not work, try reseting the gyro instead like I did in the comented out code above
		*/
        kP = p;
        kI = i;
        kD = d;
        double tempDOuble = Sensors.gyro.getAngle();
        float desiredAngleToHold = (float) tempDOuble;
        driveSpeed = driveSpeedarg;
        driveDistance = driveDistancearg;
        distanceMode = true;
        turnController = new PIDController(kP, kI, kD, Sensors.gyro, this);
        turnController.setAbsoluteTolerance(ToleranceDegrees);
        turnController.setInputRange(-360.0f,  360.0f);
        turnController.setOutputRange(-1.0, 1);
        turnController.setSetpoint(desiredAngleToHold);
        turnController.setContinuous(true);
        turnController.enable();

    }


    //just straight drives, no stopping
    public PIDTurn(double driveSpeedarg){
        double tempDOuble = Sensors.gyro.getAngle();
        float desiredAngleToHold = (float) tempDOuble;
        driveSpeed = driveSpeedarg;
        turnController = new PIDController(kP, kI, kD, Sensors.gyro, this);
        turnController.setAbsoluteTolerance(ToleranceDegrees);
        turnController.setInputRange(-360.0f,  360.0f);
        turnController.setOutputRange(-1.0, 1);
        turnController.setSetpoint(desiredAngleToHold);
        turnController.setContinuous(true);
        turnController.enable();
    }


    //rotates to an angle
    public PIDTurn(float rotateTO){

        turnController = new PIDController(kP, kI, kD, Sensors.gyro, this);
        turnController.setAbsoluteTolerance(ToleranceDegrees);
        turnController.setInputRange(-360.0f,  360.0f);
        turnController.setOutputRange(-1.0, 1);
        turnController.setSetpoint(rotateTO);
        turnController.setContinuous(true);
        turnController.enable();

    }

    public PIDTurn(float rotateTO, double tol){
        ToleranceDegrees = tol;
        turnController = new PIDController(kP, kI, kD, Sensors.gyro, this);
        turnController.setAbsoluteTolerance(ToleranceDegrees);
        turnController.setInputRange(-360.0f,  360.0f);
        turnController.setOutputRange(-1.0, 1);
        turnController.setSetpoint(rotateTO);
        turnController.setContinuous(true);
        turnController.enable();

    }


    public PIDTurn(float rotateTO, double tol, double p, double i, double d){
        kP = p;
        kI = i;
        kD = d;
        ToleranceDegrees = tol;
        turnController = new PIDController(kP, kI, kD, Sensors.gyro, this);
        turnController.setAbsoluteTolerance(ToleranceDegrees);
        turnController.setInputRange(-360.0f,  360.0f);
        turnController.setOutputRange(-1.0, 1);
        turnController.setSetpoint(rotateTO);
        turnController.setContinuous(true);
        turnController.enable();

    }


/*
    boolean Update(){

        if(distanceMode == false){
            Robot.gyro.updateSmartDashboard();
            Robot.drivetrain.driveRobot(driveSpeed, rotateToAngleRate);

            return false;
        }else{
            //this will likely need attention
            //if distanceMode is ON, which would be when supplied distance parameters

            if (areMotorsStalled)
            {
                Robot.drivetrain.driveRobot(0.0, 0.0);
                return false;
            }
            else
            {
                if ((Math.abs(Robot.drivetrain.leftEncoderGet()) + Math.abs(Robot.drivetrain.rightEncoderGet())) / 2 >= distance )
                {
                    Robot.drivetrain.driveRobot(0.0, 0.0);
                    Robot.drivetrain.resetEncoders();
                    Robot.drivetrain.resetIMU();

                    SmartDashboard.putNumber("Left Encoder:", Robot.drivetrain.leftEncoderGet());
                    SmartDashboard.putNumber("Right Encoder:", Robot.drivetrain.rightEncoderGet());

                    return true;
                }
                else
                {
                    Robot.drivetrain.driveRobot(modified_speed, rotateToAngleRate);

                    //get lowest Encoder Values
                    if( Math.abs(Robot.drivetrain.leftEncoderGet()) <  Math.abs(Robot.drivetrain.rightEncoderGet() ){
                    difference = Math.abs(Robot.drivetrain.leftEncoderGet());
                }else{
                    difference = Math.abs(Robot.drivetrain.rightEncoderGet();
                }


                    if (right_speed > 0)
                    {
                        modified_speed = right_speed - (difference * scale_factor);
                    }
                    else
                    {
                        modified_speed = right_speed + (difference * scale_factor);
                    }

                    if (lastRightDistance == Robot.drivetrain.rightEncoderGet() || lastLeftDistance == Robot.drivetrain.leftEncoderGet())
                    {
                        if (stallCounter == 75)
                        {
                            areMotorsStalled = true;
                        }
                        stallCounter++;
                    }
                    else
                    {
                        stallCounter = 0;
                    }

                    lastRightDistance = Robot.drivetrain.rightEncoderGet();
                    lastLeftDistance = Robot.drivetrain.leftEncoderGet();

                    SmartDashboard.putNumber("Distance", (Math.abs(Robot.drivetrain.leftEncoderGet()) + Math.abs(Robot.drivetrain.rightEncoderGet())) / 2);
                    SmartDashboard.putNumber("Left Speed:", modified_speed);
                    SmartDashboard.putNumber("Right Speed:", right_speed);
                    SmartDashboard.putNumber("Left Encoder:", Robot.drivetrain.leftEncoderGet());
                    SmartDashboard.putNumber("Right Encoder:", Robot.drivetrain.rightEncoderGet());

                    return false;
                }
            }


        }

    }
    */

    @Override
    public void pidWrite(double output) {
        // TODO Auto-generated method stub
        rotateToAngleRate = output;

    }


}
