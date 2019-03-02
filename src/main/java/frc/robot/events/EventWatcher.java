package frc.robot.events;

import edu.wpi.first.wpilibj.command.Scheduler;

import java.util.ArrayList;



/**
 * Singleton thread for monitoring joysticks.
 */
public class EventWatcher extends Thread {

    private ArrayList<Event> queue = new ArrayList<>();

    private static EventWatcher thread = new EventWatcher();

    public static EventWatcher getInstance() {
        return thread;
    }

    @Override
    public void run() {
        while (true) {
            for (Event e : queue) {
                if (e.check()) {
                    Scheduler.getInstance().add(e.getCommand());
                }
            }
        }
    }
}
