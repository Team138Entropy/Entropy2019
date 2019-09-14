package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;


public class NetworkTable2 extends TimedRobot {

    NetworkTable xEntry;
    NetworkTable yEntry;
    NetworkTable distanceEntry;


    public void robotInit()
    {
        //Set the default instance of NetworkTables that was created automatically when your program starts.
        NetworkTableInstance inst = NetworkTableInstance.getDefault();

        //Get the table within that instance that contains the data. There can be as many tables as you like and exist to make it easier to organize your data.  In this case it's a table called dataTable.
        NetworkTable table = inst.getTable("VisionData");

        //Get the entries within that table that correspond to the X and Y values for some operation in your program.
        xEntry = table.getEntry("X");
        yEntry = table.getEntry("Y");
    }

    double x = 0;
    double y = 0;

    public void teleopPeriodic()
    {
        //Using the entry objects, set the value to a double that is constantly increasing. The keys are actually "/datatable/X" and "/datatable/Y". If they don't already exist, the key/value pair is added.
        xEntry.setDouble(x);
        yEntry.setDouble(y);
        x += 0.05;
        y += 1.0;

    }

    public static void main(String[] args){
        System.out.println("yes");
    }

}
