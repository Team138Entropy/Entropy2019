package frc.robot.commands;

import frc.robot.Robot;

import frc.robot.subsystems.AquisitionRoller.PistonState;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class RetractRoller extends InstantCommand {
    public RetractRoller() {
        requires(Robot.roller);
    }

    protected void execute() {
        Robot.roller.setPistons(PistonState.RETRACT);
    }
}