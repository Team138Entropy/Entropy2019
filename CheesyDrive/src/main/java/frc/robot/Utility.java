package frc.robot;
/*
 * Useful routines
 */
public class Utility {
	public static double angleWrap(double angle) {
		// wrap angle to range +/-180 degrees
		double result;
		result=(angle % 360); // Modulo operator, result in range +/-360
		if (result<-180)
			result+=360;
		if (result > 180)
			result-=360;
		// result in range +/-180
		return result;
	}
	
	public static double limitValue(double testValue, double lowerBound, double upperBound)
	{
		if(testValue > upperBound)
		{
			return upperBound;
		}
		else if(testValue < lowerBound)
		{
			return lowerBound;
		}
		else
		{
			return testValue;
		}
	}


	
	public static double diffAngles(double angle1, double angle2) {
		// returns unwrapped difference between two wrapped angles
		// angles are assumed to wrap at +/-180 degree boundary
		// result = angle1-angle2
		double result=0;
		result=angle1-angle2;
		if (result<-180) result+=360;
		if (result>180) result-=360;
		return result;
	}
	
	public static double applyDeadZone(double speed)
	{
		double finalSpeed;

		if ( Math.abs(speed) < Constants.joystickDeadband) {
			finalSpeed = 0;
		}
		else {
			finalSpeed = speed;
		}
		return finalSpeed;
	}
}
