package frc.robot;

import static frc.robot.Robot.*;

/**
 * Singleton thread for updating the SmartDashboard on an interval of {@value Constants#DASHBOARD_INTERVAL} milliseconds.
 */
public class DashboardThread extends Thread {
    private static DashboardThread thread = new DashboardThread();

    public static DashboardThread getInstance() {
        return thread;
    }

    @Override
    public void run() {
        while (true) {
            Sensors.updateSmartDashboard();
            
            try {
                Thread.sleep(Constants.DASHBOARD_INTERVAL);
            } catch (InterruptedException e) {
                System.out.println("Warning: Dashboard thread interrupted, dashboard values will not be correct");
                e.printStackTrace();
            }
        }
    }
}