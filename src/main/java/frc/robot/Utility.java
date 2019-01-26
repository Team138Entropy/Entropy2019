package frc.robot;
/*
 * Useful routines
 */
public class Utility {
	/**
	 * Limit a value to being between two boundaries.
	 * @param testValue The value we want to look at.
	 * @param lowerBound The lowest {@code testValue} should be able to go.
	 * @param upperBound The highest {@code testValue} should be able to go.
	 * @return {@code testValue} if it falls between {@code lowerBound} and {@code upperBound},
	 *         or the value of the boundary it crosses.
	 */
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
