public class LineTrack{
    public static void main(String[] args){
        LineTrackParse t = new LineTrackParse();
        t.setSensorValues(args);
        double degs = t.calcAngle();
        System.out.println(degs);
    }
}