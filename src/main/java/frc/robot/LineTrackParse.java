package frc.robot;
import java.util.ArrayList;

public class LineTrackParse{
    // # of sensors pre row
    private static final int sensorsPerRow = 4;

    // measured on 2/16/19 on practice robot
    // unit: inches

    // physical, relative distance between sensors
    private static final int sensorDistance = 1;
    // physical, relative distance between senors or rows
    private static final int rowDistance = 6;

    public boolean[] topSensorRow = new boolean[sensorsPerRow];
    public boolean[] bottomSensorRow = new boolean[sensorsPerRow];
    ArrayList<Integer> trueIndexesInTopRow = new ArrayList<Integer>();
    ArrayList<Integer> trueIndexesInBottomRow = new ArrayList<Integer>();

    public boolean isValidData = true;
    public void setSensorValues(String[] values, String str2){
        boolean isFillingBottomRow = false;
        int sensorToFill = 0;
        for(String str : values){
            if(str.trim().length() == 0 || str == "\n" || str == "\r\n"){
                continue;
            }
            boolean isValid = str.matches("^\\d+$");
            if(!isValid){
                isFillingBottomRow = true;
                sensorToFill = 0;
            }else{
                boolean isTrue = Integer.parseInt(str) > 50;
                if(sensorToFill >= sensorsPerRow){
                    isValidData = false;
                    return;
                }
                if(!isFillingBottomRow){
                    topSensorRow[sensorToFill] = isTrue;
                    if(isTrue){
                        trueIndexesInTopRow.add(sensorToFill);
                    }
                }else{
                    bottomSensorRow[sensorToFill] = isTrue;
                    if(isTrue){
                        trueIndexesInBottomRow.add(sensorToFill);
                    }
                }
                sensorToFill ++;
            }
        }

        if(!isFillingBottomRow){
            // if we never got a seperator...
            // we're still on calibration
            isValidData = false;
            return;
        }
        isValidData = true;
        
    }

    private double calculateAverage(ArrayList<Integer> marks) {
        Integer sum = 0;
        if(!marks.isEmpty()) {
            for (Integer mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }

    public double calcAngle(){
        double avgTopRow = calculateAverage(trueIndexesInTopRow);
        double avgBottomRow = calculateAverage(trueIndexesInBottomRow);
        double degs = Math.toDegrees(Math.atan(
            (
                (avgTopRow - avgBottomRow) *
                sensorDistance
                ) / rowDistance
             )
        );
        return degs;
    }

    // if the row is all "true"
    // isTopRow: if true, the top row, if false, the bottom row
    public int countTriggeredInRow(boolean isTopRow){
        boolean[] row = null;
        if(isTopRow){
            row = topSensorRow;
        }else{
            row = bottomSensorRow;
        }

        return countTrue(row);
    }

    private static int countTrue(boolean... array)
    {
        int num = 0;
        for(boolean b : array) if(b) num++;
        return num;
    }
}