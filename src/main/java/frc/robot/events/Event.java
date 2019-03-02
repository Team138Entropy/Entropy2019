package frc.robot.events;

import edu.wpi.first.wpilibj.command.Command;

public interface Event {
    /**
     * Check whether or not some command should be added to the scheduler.
     * @return Whether or not it should be added to the scheduler.
     */
    boolean check();

    /**
     * Get the command to add to the scheduler.
     * @return The command to add to the scheduler.
     */
    Command getCommand();
}
