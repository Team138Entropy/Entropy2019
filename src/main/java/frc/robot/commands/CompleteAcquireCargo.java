package frc.robot.commands;

import frc.robot.commands.DefaultPosition;
import edu.wpi.first.wpilibj.command.CommandGroup;
	
	public class CompleteAcquireCargo extends CommandGroup {
        public CompleteAcquireCargo() {
            addSequential(new ResetOvercurrentDetected());
            addSequential(new DefaultPosition());
        }
}