package frc.robot.commands;

import frc.robot.subsystems.Elevator.ElevatorTarget;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
	
	public class DefaultPosition extends CommandGroup {
        public  DefaultPosition() {
            addSequential(new ElevateToTarget(ElevatorTarget.LEVEL_2));
            //addSequential(new CarriageRetracted());
            //addSequential(new CradleWristHorizontal());
            addSequential(new RotateTurretToTarget(Constants.TurretCenterPosition));
            //addSequential(new RollerRetracted());
            //addSequential(new RollerRotationOff());
            addSequential(new RetractClimbPiston());
        }


}