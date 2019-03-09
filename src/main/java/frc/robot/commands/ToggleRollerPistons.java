package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleRollerPistons extends InstantCommand {

    public ToggleRollerPistons() {
        requires(Robot.roller);
    }

    protected void execute() {
        System.out.println("trying to use piston");
        Robot.roller.togglePistons();
    }
}