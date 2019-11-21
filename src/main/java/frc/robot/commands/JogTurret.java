package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class JogTurret extends Command {
	
	private int _jogDirection = 0;
	
	public JogTurret(int direction)
	{
		requires(Robot.turret);
		_jogDirection = direction;
	}
	
	public JogTurret()
	{
		requires(Robot.turret);
	}
	

	protected void initialize() {
		// Command was invoked with specific direction
		if (_jogDirection != 0)
		{
			//Robot.turret.JogTurret(_jogDirection, Constants.TurretJogSpeed);
		}
		// Command was not invoked with specific direction - use D Pad
		else
		{
			//Robot.turret.JogTurret(OI.getTurretJogDirection(), Constants.TurretJogSpeed);
		}
	}

	protected void execute() {
		// Command was invoked with specific direction
		if (_jogDirection != 0)
		{
			//Robot.turret.JogTurret(_jogDirection, Constants.TurretJogSpeed);
		}
		// Command was not invoked with specific direction - use D Pad
		else{
			//Robot.turret.JogTurret(OI.getTurretJogDirection(), Constants.TurretJogSpeed);
		}			
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.turret.StopMoving();
		
	}

	protected void interrupted() {
		
		Robot.turret.StopMoving();
	}

}