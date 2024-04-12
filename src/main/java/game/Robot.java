package game;

import java.util.Observable;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import serialization.IStorable;

public class Robot extends Observable implements IStorable {

  private double lowerBoundX, lowerBoundY, upperBoundX, upperBoundY;

  private static final double VELOCITY = 0.1;
  private static final double ANGULAR_VELOCITY = 0.001;

  private static final double TARGET_CLOSE_ENOUGH = 5;
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

  public void setLimits(double lowerBoundX, double lowerBoundY, double upperBoundX, double upperBoundY) {
    this.lowerBoundX = lowerBoundX;
    this.lowerBoundY = lowerBoundY;
    this.upperBoundX = upperBoundX;
    this.upperBoundY = upperBoundY;
  }

  public void setTarget(Target target) {
    this.target = target;

    angularVelocity = 0;
    if (angleToTarget() > direction) {
      angularVelocity = -ANGULAR_VELOCITY;
    } else {
      angularVelocity = ANGULAR_VELOCITY;
    }
  }

  private double distanceToTarget() {
    return GameLogic.LogicMath.distance(getX(), getY(), target.getX(),
        target.getY());
  }

  private double angleToTarget() {
    return GameLogic.LogicMath.asNormalizedRadians(
        GameLogic.LogicMath.angleTo(getX(), getY(), target.getX(), target.getY()));
  }

  private double speedCoefficientX() {
    return 1 - 2 * Math.abs((upperBoundX - x) / (upperBoundX - lowerBoundX) - 0.5);
//    return 1;
  }

  private double speedCoefficientY() {
    return 1 - 2 * Math.abs((upperBoundY - y) / (upperBoundY - lowerBoundY) - 0.5);
//    return 1;
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

    final double newAngle = GameLogic.LogicMath.asNormalizedRadians(direction + angularVelocity * dt);

    final double dx = velocity / angularVelocity * (Math.sin(newAngle) - Math.sin(direction));
    final double dy = velocity / angularVelocity * (Math.cos(newAngle) - Math.cos(direction));

    x += dx * speedCoefficientX();
    y -= dy * speedCoefficientY();
    direction = newAngle;

    setChanged();
    notifyObservers();
  }

  private static String formatTitle(String title) {
    String cased = title.toUpperCase();

    return cased.replaceAll(" +", "_");
  }

  private Preferences getPreferences() {
    return Preferences.userRoot().node("robot preferences");
  }

  @Override
  public void save() {
    var preferences = getPreferences();

    preferences.putDouble(formatTitle("robot x"), getX());
    preferences.putDouble(formatTitle("robot y"), getY());
    preferences.putDouble(formatTitle("robot direction"), getDirection());

    preferences.putDouble(formatTitle("target x"), target.getX());
    preferences.putDouble(formatTitle("target y"), target.getY());
  }

  @Override
  public void restore() {
    var preferences = getPreferences();

    double robotX = preferences.getDouble(formatTitle("robot x"), 100);
    double robotY = preferences.getDouble(formatTitle("robot y"), 100);
    double robotDirection = preferences.getDouble(formatTitle("robot direction"), 0);

    double targetX = preferences.getDouble(formatTitle("target x"), 150);
    double targetY = preferences.getDouble(formatTitle("target y"), 100);

    target = new Target(targetX, targetY);
    x = robotX;
    y = robotY;
    direction = robotDirection;
  }
}
