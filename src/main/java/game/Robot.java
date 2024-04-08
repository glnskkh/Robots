package game;

import java.util.Observable;

public class Robot extends Observable {

  private static final double VELOCITY = 0.1;
  private static final double ANGULAR_VELOCITY = 0.001;

  private static final double TARGET_CLOSE_ENOUGH = 1;
  private final static double EPSILON = 0.05;
  private final double velocity = VELOCITY;
  private double direction = 0;
  private double x = 100;
  private double y = 100;
  private double angularVelocity = 0;

  private Target target;

  public Robot(Target target) {
    this.target = target;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getDirection() {
    return direction;
  }

  public void setTarget(Target target) {
    this.target = target;

    angularVelocity = 0;
    if (angleToTarget() > direction) {
      angularVelocity = ANGULAR_VELOCITY;
    } else {
      angularVelocity = -ANGULAR_VELOCITY;
    }

    if (distanceToTarget() < 20 * TARGET_CLOSE_ENOUGH)
      angularVelocity = -angularVelocity;
  }

  private double distanceToTarget() {
    return GameLogic.LogicMath.distance(getX(), getY(), target.getX(),
        target.getY());
  }

  private double angleToTarget() {
    return GameLogic.LogicMath.asNormalizedRadians(
        GameLogic.LogicMath.angleTo(getX(), getY(), target.getX(), target.getY()));
  }

  public void move(double dt) {
    if (distanceToTarget() < TARGET_CLOSE_ENOUGH) {
      return;
    }

    if (Math.abs(direction - angleToTarget()) < EPSILON) {
      x += velocity * Math.cos(direction) * dt;
      y += velocity * Math.sin(direction) * dt;

      return;
    }

    double newAngle = GameLogic.LogicMath.asNormalizedRadians(direction + angularVelocity * dt);

    x += velocity / angularVelocity * (Math.sin(newAngle) - Math.sin(direction));
    y -= velocity / angularVelocity * (Math.cos(newAngle) - Math.cos(direction));
    direction = newAngle;

    setChanged();
    notifyObservers();
  }
}
