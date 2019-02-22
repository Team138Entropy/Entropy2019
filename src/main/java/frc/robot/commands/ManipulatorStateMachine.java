package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Sensors;
import frc.robot.subsystems.Elevator.ElevatorTarget;
import frc.robot.subsystems.Elevator;
import frc.robot.Constants;

public class ManipulatorStateMachine extends Command {

    private boolean stayState = false;
    private boolean changeState = false;

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

    ManipulatorState currentState;
    ManipulatorState oldState;
    boolean isEntryActionComplete = false;

    public ManipulatorStateMachine() {
        requires(Robot.manipulator);
        requires(Robot.elevator);
    }

    protected void initialize() {
        currentState = ManipulatorState.IDLE;
    }

    protected void execute() {
        stayState = Robot.manipulator.getStayState();
        changeState = Robot.manipulator.getChangeState();

        switch(currentState) {
            case IDLE: idle();
            case PREPARE_ACQUIRE_HP: prepareAcquire();
            case READY_ACQUIRE_HP: readyAcquire();
            case COMPLETE_ACQUIRE_HP: completeAcquire();
            case ACQUIRED_HP: acquiredHP();
            case DEPLOY_HP: deployHP();
            case IDLE_CARGO: acquiredCargo();
            case DEPLOY_CARGO: deployCargo();
        }
    }

    private void idle() {
        if (!isEntryActionComplete) {
            Robot.manipulator.actuate(Constants.RETRACT, Constants.HORIZONTAL);
            Robot.elevator.Elevate(Elevator.ElevatorTarget.FLOOR); // TODO: Get this correct
            isEntryActionComplete = true;
        }

        if (Robot.elevator.IsMoveComplete()) {
            if (changeState) {
                changeState(ManipulatorState.PREPARE_ACQUIRE_HP);
            } else {
                if (Sensors.isCargoPresent()) {
                    changeState(ManipulatorState.IDLE_CARGO);
                }
            }
        }
    }

    private void prepareAcquire() {
        if (!isEntryActionComplete) {
            Robot.manipulator.actuate(Constants.EXTEND, Constants.VERTICAL);
            Robot.elevator.Elevate(Elevator.ElevatorTarget.LEVEL_1); // TODO: Get this correct
            isEntryActionComplete = true;
        }

        if (Robot.elevator.IsMoveComplete()) {
            changeState(ManipulatorState.READY_ACQUIRE_HP);
        }
    }

    private void readyAcquire() {
        if (changeState) {
            changeState(ManipulatorState.COMPLETE_ACQUIRE_HP);
        }
    }

    private void completeAcquire() {
        if (!isEntryActionComplete) {
            Robot.elevator.Elevate(ElevatorTarget.LEVEL_2); // TODO: Make a preset for slightly above level 1
            isEntryActionComplete = true;
        }

        if (Robot.elevator.IsMoveComplete()) {
            Robot.manipulator.actuate(Constants.RETRACT, Constants.VERTICAL);
            Robot.elevator.Elevate(ElevatorTarget.LEVEL_1);
            
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

        if (changeState) {
            changeState(ManipulatorState.DEPLOY_HP);
        } else {
            if (stayState) {
                changeState(ManipulatorState.PREPARE_ACQUIRE_HP);
            }
        }
    }

    private void deployHP() {
        if (!isEntryActionComplete) {
            Robot.manipulator.actuate(Constants.EXTEND, Constants.VERTICAL);
            isEntryActionComplete = true;
        }

        if (changeState) {
            changeState(ManipulatorState.IDLE);
        } else {
            if (stayState) {
                changeState(ManipulatorState.ACQUIRED_HP);
            }
        }
    }

    private void acquiredCargo() {
        if (changeState) {
            changeState(ManipulatorState.DEPLOY_CARGO);
        }
    }

    private void deployCargo() {
        if (!isEntryActionComplete) {
            Robot.manipulator.actuate(Constants.RETRACT, Constants.VERTICAL);
            isEntryActionComplete = true;
        }

        if (changeState) {
            changeState(ManipulatorState.IDLE);
        }
    }

    private void changeState(ManipulatorState newState) {
        oldState = currentState;
        currentState = newState;
        isEntryActionComplete = false;
    }

    public boolean isFinished() {
        return false;
    }
}