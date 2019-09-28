package frc.robot;

/**
 * Helper class to implement "Cheesy Drive". "Cheesy Drive" simply means that the "turning" stick controls the curvature
 * of the robot's path rather than its rate of heading change. This helps make the robot more controllable at high
 * speeds. Also handles the robot's quick turn functionality - "quick turn" overrides constant-curvature turning for
 * turn-in-place maneuvers.
 */
public class CheesyDrive {

    // These constants help prevent accidental triggering
    /** @see #handleDeadband(double, double)*/
    private static final double kThrottleDeadband = 0.02;
    /** @see #handleDeadband(double, double)*/
    private static final double kWheelDeadband = 0.02;

    // These factors determine how fast the wheel traverses the "non linear" sine curve.
    /** @see <a href="https://www.desmos.com/calculator/hshfsz5we0">Math on Desmos</a>*/
    private static final double kHighWheelNonLinearity = 0.65;
    /** @see <a href="https://www.desmos.com/calculator/hshfsz5we0">Math on Desmos</a>*/
    private static final double kLowWheelNonLinearity = 0.5;

    private static final double kHighNegInertiaScalar = 4.0;

    private static final double kLowNegInertiaThreshold = 0.65;
    private static final double kLowNegInertiaTurnScalar = 3.5;
    private static final double kLowNegInertiaCloseScalar = 4.0;
    private static final double kLowNegInertiaFarScalar = 5.0;

    private static final double kHighSensitivity = 0.65;
    private static final double kLowSensitiity = 0.8; // Previously 0.65

    private static final double kQuickStopDeadband = 0.5;
    private static final double kQuickStopWeight = 0.1;
    private static final double kQuickStopScalar = 5.0;

    private double mOldWheel = 0.0;
    private double mQuickStopAccumulator = 0.0;
    private double mNegInertiaAccumlator = 0.0;

    /**
     * Here's the function that does all the work. There's a lot of math here.
     * @param throttle
     * @param wheel The raw Y-value from the joystick
     * @param isQuickTurn {@code true} if we want to turn in place
     * @param isHighGear
     * @return
     * @see <a href="https://www.desmos.com/calculator/hshfsz5we0">Math on Desmos</a>
     */
    public DriveSignal cheesyDrive(double throttle, double wheel, boolean isQuickTurn, boolean isHighGear) {

        wheel = handleDeadband(wheel, kWheelDeadband);
        throttle = handleDeadband(throttle, kThrottleDeadband);

        // "negative inertia" means we're slowing down.
        double negInertia = wheel - mOldWheel;
        mOldWheel = wheel;

        if (isHighGear) {
            wheel = applyMagicFunc(wheel, kHighWheelNonLinearity, 2);
        } else {
            wheel = applyMagicFunc(wheel, kLowWheelNonLinearity, 2);
        }

        double leftPwm, rightPwm, overPower;
        double sensitivity;

        double angularPower;
        double linearPower;

        // Negative inertia!
        double negInertiaScalar;
        if (isHighGear) {
            negInertiaScalar = kHighNegInertiaScalar;
            sensitivity = kHighSensitivity;
        } else {
            if (wheel * negInertia > 0) {
                // If we are moving away from 0.0, aka, trying to get more wheel.
                negInertiaScalar = kLowNegInertiaTurnScalar;
            } else {
                // Otherwise, we are attempting to go back to 0.0.
                if (Math.abs(wheel) > kLowNegInertiaThreshold) {
                    negInertiaScalar = kLowNegInertiaFarScalar;
                } else {
                    negInertiaScalar = kLowNegInertiaCloseScalar;
                }
            }
            sensitivity = kLowSensitiity;
        }
        double negInertiaPower = negInertia * negInertiaScalar;
        mNegInertiaAccumlator += negInertiaPower;

        wheel = wheel + mNegInertiaAccumlator;
        if (mNegInertiaAccumlator > 1) {
            mNegInertiaAccumlator -= 1;
        } else if (mNegInertiaAccumlator < -1) {
            mNegInertiaAccumlator += 1;
        } else {
            mNegInertiaAccumlator = 0;
        }
        linearPower = throttle;

        // Quickturn!
        // Thank you, 254, for the incredibly helpful comment. -Will
        if (isQuickTurn) {
            if (Math.abs(linearPower) < kQuickStopDeadband) {
                double alpha = kQuickStopWeight;
                mQuickStopAccumulator = (1 - alpha) * mQuickStopAccumulator
                        + alpha * Util.limit(wheel, 1.0) * kQuickStopScalar;
            }
            overPower = 1.0;
            angularPower = wheel;
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * wheel * sensitivity - mQuickStopAccumulator;
            if (mQuickStopAccumulator > 1) {
                mQuickStopAccumulator -= 1;
            } else if (mQuickStopAccumulator < -1) {
                mQuickStopAccumulator += 1;
            } else {
                mQuickStopAccumulator = 0.0;
            }
        }

        rightPwm = leftPwm = linearPower;
        leftPwm += angularPower;
        rightPwm -= angularPower;

        if (leftPwm > 1.0) {
            rightPwm -= overPower * (leftPwm - 1.0);
            leftPwm = 1.0;
        } else if (rightPwm > 1.0) {
            leftPwm -= overPower * (rightPwm - 1.0);
            rightPwm = 1.0;
        } else if (leftPwm < -1.0) {
            rightPwm += overPower * (-1.0 - leftPwm);
            leftPwm = -1.0;
        } else if (rightPwm < -1.0) {
            leftPwm += overPower * (-1.0 - rightPwm);
            rightPwm = -1.0;
        }
        return new DriveSignal(leftPwm, rightPwm);
    }

    /**
     * Handles a deadband, obviously.
     * @param val The value we want to regulate with the deadband.
     * @param deadband The minimum the <i>absolute value</i> of {@code val} should be before we use it.
     * @return {@code val} if its absolute value is greater than {@code deadband}, otherwise 0.
     */
    private double handleDeadband(double val, double deadband) {
        return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
    }

    /**
     * Apply 254's "magic function" an arbitrary number of times.
     * @param rawValue The raw value from the joystick.
     * @param nonLinearity The "intensity" of the adjustment from applying the function. Don't let this go above 1.
     * @param iterations The number of times we want to apply the function.
     * @return The result of the last iteration.
     * @see <a href="https://www.desmos.com/calculator/hshfsz5we0">Math on Desmos</a>
     */
    private double applyMagicFunc(final double rawValue, final double nonLinearity, final int iterations) {
        final double denominator = Math.sin(Math.PI / 2.0 * nonLinearity);
        boolean newMath = true;
        double retVal = rawValue;

        for (int i = 0; i < iterations; i++) {
            if (newMath) {
                retVal = retVal * retVal * retVal; //y = x^3
            } else {
                retVal = Math.sin(Math.PI / 2.0 * nonLinearity * retVal) / denominator;
            }
        }

        return retVal;
    }
}

/*
          c,_.--.,y
            7 a.a(
           (   ,_Y)
           :  '---;
       ___.'\.  - (
     .'"""S,._'--'_2..,_
     |    ':::::=:::::  \
     .     f== ;-,---.' T
      Y.   r,-,_/_      |
      |:\___.---' '---./
      |'`             )
       \             ,
       ':;,.________.;L
       /  '---------' |
       |              \
       L---'-,--.-'--,-'
        T    /   \   Y
        |   Y    ,   |
        |   \    (   |
        (   )     \,_L
        7-./      )  `,
       /  _(      '._  \
     '---'           '--'
 */