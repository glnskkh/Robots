package game;

import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

  private final Robot robot;
  private final long dt = 5;
  private Target target;
  private Timer timer;

  public GameLogic() {
    target = new Target();

    robot = new Robot(target);
    robot.restore();
  }

  public void startTimer() {
    timer = new Timer("event generator", true);
    addActionToTimer(new TimerTask() {
      @Override
      public void run() {
        robot.move(dt);
      }
    }, dt);
  }

  public void addActionToTimer(TimerTask task, long timeout) {
    timer.schedule(task, 0, timeout);
  }

  public void stopTimer() {
    robot.save();
    timer.cancel();
  }

  public void setTarget(Target target) {
    this.target = target;

    robot.setTarget(target);
  }

  public Robot getRobot() {
    return robot;
  }

  public Target getTarget() {
    return target;
  }

  public static class LogicMath {

    public static double distance(double x0, double y0, double x1, double y1) {
      final double dx = x1 - x0;
      final double dy = y1 - y0;

      return Math.sqrt(dx * dx + dy * dy);
    }

    public static double angleTo(double x0, double y0, double x1, double y1) {
      final double dx = x1 - x0;
      final double dy = y1 - y0;

      return asNormalizedRadians(Math.atan2(dy, dx));
    }

    public static double asNormalizedRadians(double angle) {
      final double TAU = 2 * Math.PI;

      if (angle < 0) {
        return TAU - ((-angle) % TAU);
      }

      return angle % TAU;
    }
  }
}
