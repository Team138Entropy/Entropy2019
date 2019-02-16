import java.util.ArrayList;

public class LineTrackParse{
    private static final int sensorsPerRow = 4;
    private static final int sensorDistance = 1;
    private static final int rowDistance = 2;

    private boolean[] topSensorRow = new boolean[sensorsPerRow];
    private boolean[] bottomSensorRow = new boolean[sensorsPerRow];
    ArrayList<Integer> trueIndexesInTopRow = new ArrayList<Integer>();
    ArrayList<Integer> trueIndexesInBottomRow = new ArrayList<Integer>();
    public void setSensorValues(String[] values){
        System.out.println();
        boolean isFillingBottomRow = false;
        int sensorToFill = 0;
        for(String str : values){
            boolean isTrue = str.equals("true") || str.equals("1");
            boolean isValid = isTrue || str.equals("false") || str.equals("0");
            if(!isValid){
                isFillingBottomRow = true;
                sensorToFill = 0;
            }else{
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
    // rumble when it runs over a line
}