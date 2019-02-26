package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.Sensors;
import frc.robot.subsystems.Elevator.ElevatorTarget;

public class Manipulator extends Subsystem {

    public Solenoid translationSolenoid = new Solenoid(RobotMap.MANIPULATOR_TRANSLATION_PORT);
    public Solenoid rotationSolenoid = new Solenoid(RobotMap.MANIPULATOR_ROTATION_PORT);

    private boolean isTranslated = false;
    private boolean isRotated = false;

    private enum OperatorCommand {
        NONE,
        ACQUIRE,
        DEPLOY,
    }

    private enum ManipulatorState {
        IDLE,
        PREPARE_ACQUIRE_HP,
        READY_ACQUIRE_HP,
        COMPLETE_ACQUIRE_HP,
        ACQUIRED_HP,
        DEPLOY_HP,
        IDLE_CARGO,
        DEPLOY_CARGO,
    }

    // State machine control variables
    ManipulatorState currentState = ManipulatorState.IDLE;
    ManipulatorState oldState;
    OperatorCommand operatorCommand = OperatorCommand.NONE;
    boolean isEntryActionComplete = false;

    public void initDefaultCommand() {
        // Obligatory initDefaultCommand() declaration so we don't get yelled at by WPILib
    }

    public void executeStateMachine() {
        switch(currentState) {
            case IDLE:
                idle();
                break;
            case PREPARE_ACQUIRE_HP:
                prepareAcquire();
                break;
            case READY_ACQUIRE_HP:
                readyAcquire();
                break;
            case COMPLETE_ACQUIRE_HP:
                completeAcquire();
                break;
            case ACQUIRED_HP:
                acquiredHP();
                break;
            case DEPLOY_HP:
                deployHP();
                break;
            case IDLE_CARGO:
                acquiredCargo();
                break;
            case DEPLOY_CARGO:
                deployCargo();
                break;
        }
    }

    // States
    private void idle() {
        if (!isEntryActionComplete) {
            actuate(Constants.RETRACT, Constants.HORIZONTAL);
            Robot.elevator.Elevate(Elevator.ElevatorTarget.FLOOR);
            isEntryActionComplete = true;
        }

        if (Robot.elevator.IsMoveComplete()) {
            if (operatorCommand == OperatorCommand.ACQUIRE) {
                changeState(ManipulatorState.PREPARE_ACQUIRE_HP);
            } else if (Sensors.isCargoPresent()) {
                changeState(ManipulatorState.IDLE_CARGO);
            }
        }
    }

    private void prepareAcquire() {
        if (!isEntryActionComplete) {
            Robot.manipulator.actuate(Constants.EXTEND, Constants.VERTICAL);
            //Robot.elevator.Elevate(Elevator.ElevatorTarget.LEVEL_1);
            isEntryActionComplete = true;
        }

        if (Robot.elevator.IsMoveComplete()) {
            changeState(ManipulatorState.READY_ACQUIRE_HP);
        }
    }

    private void readyAcquire() {
        if (operatorCommand == OperatorCommand.ACQUIRE) {
            changeState(ManipulatorState.COMPLETE_ACQUIRE_HP);
        }
    }

    private void completeAcquire() {
        if (!isEntryActionComplete) {
            Robot.elevator.ElevateRelative(Constants.HP_ELEVATE_OFFSET);
            isEntryActionComplete = true;
        }

        if (Robot.elevator.IsMoveComplete()) {
            Robot.manipulator.actuate(Constants.RETRACT, Constants.VERTICAL);
            //Robot.elevator.Elevate(ElevatorTarget.LEVEL_1);
            Robot.elevator.ElevateRelative(Constants.HP_ELEVATE_OFFSET * -1);
            
            if (Robot.elevator.IsMoveComplete()) {
                changeState(ManipulatorState.ACQUIRED_HP);
            }
        }
    }

    private void acquiredHP() {
        if (!isEntryActionComplete) {
            Robot.manipulator.actuate(Constants.RETRACT, Constants.VERTICAL);
            isEntryActionComplete = true;
        }

        if (operatorCommand == OperatorCommand.DEPLOY) {
            changeState(ManipulatorState.DEPLOY_HP);
        } else if (operatorCommand == OperatorCommand.ACQUIRE &&
                   Robot.elevator.getTargetPosition() == ElevatorTarget.LEVEL_1) {
            
            changeState(ManipulatorState.PREPARE_ACQUIRE_HP);
        }
    }

    private void deployHP() {
        if (!isEntryActionComplete) {
            Robot.manipulator.actuate(Constants.EXTEND, Constants.VERTICAL);
            isEntryActionComplete = true;
        }

        if (operatorCommand == OperatorCommand.ACQUIRE) {
            changeState(ManipulatorState.IDLE);
        } else if (operatorCommand == OperatorCommand.DEPLOY) {
            changeState(ManipulatorState.ACQUIRED_HP);
        }
    }

    private void acquiredCargo() {
        if (operatorCommand == OperatorCommand.DEPLOY) {
            changeState(ManipulatorState.DEPLOY_CARGO);
        }
    }

    private void deployCargo() {
        if (!isEntryActionComplete) {
            Robot.manipulator.actuate(Constants.RETRACT, Constants.VERTICAL);
            isEntryActionComplete = true;
        }

        if (operatorCommand == OperatorCommand.ACQUIRE) {
            changeState(ManipulatorState.IDLE);
        }
    }

    // Hardware state accessers
    public boolean getTranslated() {
        return isTranslated;
    }

    public boolean getRotated() {
        return isRotated;
    }

    // State machine controls
    private void changeState(ManipulatorState newState) {
        SmartDashboard.putString("Current Manipulator State", newState.toString());
        oldState = currentState;
        currentState = newState;

        // Reset our control variables
        operatorCommand = OperatorCommand.NONE;
        isEntryActionComplete = false;
    }

    // OI interfaces
    public void acquire() {
        operatorCommand = OperatorCommand.ACQUIRE;
    }

    public void deploy() {
        operatorCommand = OperatorCommand.DEPLOY;
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