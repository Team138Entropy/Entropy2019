package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;


public class NetworkTable2{
    public static void main(String[] args){
        new NetworkTable2().run();
    }

    public void run()
    {
        //Set the default instance of NetworkTables that was created automatically when your program starts.
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        //Get the table within that instance that contains the data. There can be as many tables as you like and exist to make it easier to organize your data.  In this case it's a table called dataTable.
        NetworkTable table = inst.getTable("SmartDashboard");
        NetworkTableEntry xEntry = table.getEntry("x");
        NetworkTableEntry yEntry = table.getEntry("y");
        inst.startClientTeam(138);
        inst.startDSClient();
        while(true){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException ex){
                System.out.println("interrupted");
                return;
            }
            double x = xEntry.getDouble(0.0);
            double y = yEntry.getDouble(0.0);
            System.out.println(x + " " + y);
        }


    }


}
