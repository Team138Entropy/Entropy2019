package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Manipulator extends Subsystem {

    public Solenoid translationSolenoid = new Solenoid(RobotMap.MANIPULATOR_TRANSLATION_PORT);
    public Solenoid rotationSolenoid = new Solenoid(RobotMap.MANIPULATOR_ROTATION_PORT);

    private boolean isTranslated = false;
    private boolean isRotated = false;

    public void initDefaultCommand() {
        // Obligatory initDefaultCommand() declaration so we don't get yelled at by WPILib
    }

    // Hardware state accessers
    public boolean getTranslated() {
        return isTranslated;
    }

    public boolean getRotated() {
        return isRotated;
    }

    // Hardware interface
    public void actuate(boolean translation, boolean rotation) {
        isTranslated = translation;
        translationSolenoid.set(translation);
        isRotated = rotation;
        rotationSolenoid.set(rotation);
    }

    public void translate(boolean translation) {
        isTranslated = translation;
        translationSolenoid.set(translation);
    }

    public void rotate(boolean rotation) {
        isRotated = rotation;
        rotationSolenoid.set(rotation);
    }
}