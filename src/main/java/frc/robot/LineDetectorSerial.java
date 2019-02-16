package frc.robot.subsystems;

// import frc.robot.Constants;
import frc.robot.commands.RunRoller;
import frc.robot.commands.Serial;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LineDetectorSerial extends Subsystem {
    protected void initDefaultCommand() {
        setDefaultCommand(new Serial());
    }
}