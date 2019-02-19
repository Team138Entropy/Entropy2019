package frc.robot.commands;

import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Sensors;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.LineTrackParse;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Serial extends Command {
    private SerialPort port;
    private final GenericHID.RumbleType rumble = GenericHID.RumbleType.kRightRumble;
    private XboxController controller = null;
    private LineTrackParse t = new LineTrackParse();
    private String buffer = "";

    public Serial() {
        try {
            port = new SerialPort(9600, SerialPort.Port.kUSB);
            port.enableTermination();    
        } catch (Exception e) {
            try{
                port = new SerialPort(9600, SerialPort.Port.kUSB1);
                port.enableTermination();
            } catch (Exception e1) {
                try{
                    port = new SerialPort(9600, SerialPort.Port.kUSB2);
                    port.enableTermination();
                } catch (Exception e2) {
                    System.err.println("Connect an arduino to a usb port");
                    throw e;
                }
            }
        }
        
        requires(Robot.lineDetectorSerial);
    }

    protected void execute() {
        String str = port.readString();
        System.out.println("str " + str);
        // buffers to "\n"
        str = buffer + str;

        if(str.contains("\n")){
            SmartDashboard.putString("serialData", str);
            System.out.println("n" + str);
            String[] split = str.split("\n");
            str = split[0];
            buffer = split[1];

            if(str.length() == 0) return;
            if(str.charAt(0) == '#'){
                System.out.println("serial: " + str);
            }else{
                System.out.println("got data " + str);
                procArgs(str.split(" "));
            }
        }else{
            System.out.println("no n" + str);
            buffer = str;
        }
    }

    private void procArgs(String[] args){
        t.setSensorValues(args);
        if(t.isValidData){
            SmartDashboard.putBoolean("isInCalibration", false);
            double degs = t.calcAngle();
            System.out.println("calculated angle " + degs);

            boolean rows[] = { true, false };
            double vibrateVal = 0;
            // top row, then bottom
            SmartDashboard.putBooleanArray("topSensors", t.topSensorRow);
            SmartDashboard.putBooleanArray("bottomSensors", t.bottomSensorRow);
            for(boolean row: rows){
                double count = t.countTriggeredInRow(row) - 1;
                if(count > 0){
                    vibrateVal = Math.max(count / 3, vibrateVal);
                }
            }
            OI.driverStick.setRumble(rumble, vibrateVal);
        }else{
            SmartDashboard.putBoolean("isInCalibration", true);
            System.out.println("Calibration mode!");
        }
    }

    protected boolean isFinished() {
        return false;
    }
}
