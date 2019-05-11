package frc.robot.commands;

import frc.robot.commands.DefaultPosition;
import frc.robot.subsystems.AcquisitionRoller.RollerState;
import edu.wpi.first.wpilibj.command.CommandGroup;
	
	public class CompleteAcquireCargo extends CommandGroup {
        public CompleteAcquireCargo() {
            addSequential(new ResetOvercurrentDetected());
            addSequential(new SetRoller(RollerState.HOLD));
            addSequential(new DefaultPosition());
        }
}