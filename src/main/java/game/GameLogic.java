package game;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class GameLogic extends Observable {

  private final Robot robot;
  private final long dt = 5;
  private final Point2D.Double target = new Point2D.Double(150, 100);
  private Timer timer;
  private Point2D.Double windowBounds = new Point2D.Double(300, 300);

  public GameLogic() {
    robot = new Robot();
    robot.restore();
  }

  public void startTimer() {
    timer = new Timer("event generator", true);
    addActionToTimer(new TimerTask() {
      @Override
      public void run() {
        robot.move(dt, target, windowBounds);

        setChanged();
        notifyObservers();
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

  public Robot getRobot() {
    return robot;
  }

  public Point2D.Double getTarget() {
    return target;
  }

  public void setTarget(Point target) {
    this.target.setLocation(target);
  }

  public Point2D.Double getWindowBounds() {
    return windowBounds;
  }

  public void setWindowBounds(Point2D.Double windowBounds) {
    this.windowBounds = windowBounds;
  }
}
