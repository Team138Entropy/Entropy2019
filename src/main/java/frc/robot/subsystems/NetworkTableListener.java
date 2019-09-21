package frc.robot.subsystems;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTableListener {

    public static void main(String[] args)
    {
        new NetworkTableListener().run();
    }

    public void run(){
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("VisionData");
        NetworkTableEntry yEntry = table.getEntry("Y");
        inst.startClientTeam(138);

        /*
        table.addEntryListener("X",(table,key,entry,value,flags)->{
            System.out.println("X changed value" + value.getValue());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        */


        yEntry.addListener(event -> {
            System.out.println("New Y value: " + event.value.getValue());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        try{
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            System.out.println("interupt");
            return;
        }


    }


}
