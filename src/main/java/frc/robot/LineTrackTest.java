import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class LineTrackTest{
    private final GenericHID.RumbleType rumble = GenericHID.RumbleType.kRightRumble;
    private XboxController controller = null;

    public static void main(String[] args) {
        LineTrackParse t = new LineTrackParse();
        t.setSensorValues(args);
        double degs = t.calcAngle();
        System.out.println(degs);

        boolean rows[] = { true, false };
        double vibrateVal = 0;
        // top row, then bottom
        for(boolean row: rows){
            double count = t.countTriggeredInRow(row) - 1;
            if(count > 0){
               double vibrateVal = Math.max(count / 3, vibrateVal);
            }
        }
        XboxController.setRumble(rumble, vibrateVal);
    }
}