package game;

import java.awt.geom.Point2D;
import java.util.prefs.Preferences;
import serialization.IStorable;

public class Robot implements IStorable {

  private static final double VELOCITY = 0.1;
  private static final double ANGULAR_VELOCITY = 0.001;
  private static final double TARGET_CLOSE_ENOUGH = 5;
  private final static double EPSILON = 0.05;

  private final Point2D.Double position = new Point2D.Double(100, 100);
  private double direction = 0;
  private double angularVelocity = 0;

  private final Point2D.Double lastTargetPosition = new Point2D.Double(position.getX(), position.getY());

  private static String formatTitle(String title) {
    String cased = title.toUpperCase();

    return cased.replaceAll(" +", "_");
  }

  public double getX() {
    return position.getX();
  }

  public double getY() {
    return position.getY();
  }

  public double getDirection() {
    return direction;
  }

  private double asNormalizedRadians(double angle) {
    final double TAU = 2 * Math.PI;

    if (angle < 0) {
      return TAU - ((-angle) % TAU);
    }

    return angle % TAU;
  }

  private double angleTo(Point2D.Double point) {
    final double dx = point.getX() - position.getX();
    final double dy = point.getY() - position.getY();

    return asNormalizedRadians(Math.atan2(dy, dx));
  }

  private double speedCoefficientX(double upperBoundX) {
    return Math.max(1 - 2 * Math.abs((upperBoundX - position.getX()) / upperBoundX - 0.5), 0.01);
  }

  private double speedCoefficientY(double upperBoundY) {
    return Math.max(1 - 2 * Math.abs((upperBoundY - position.getY()) / upperBoundY - 0.5), 0.01);
  }

  public void move(double dt, Point2D.Double target, Point2D.Double windowBound) {
    if (position.distance(target) < TARGET_CLOSE_ENOUGH) {
      return;
    }

    double velocity = VELOCITY;

    if (lastTargetPosition.distance(target) > TARGET_CLOSE_ENOUGH) {
      lastTargetPosition.setLocation(target);

      if (angleTo(target) > direction) {
        angularVelocity = -ANGULAR_VELOCITY;
      } else {
        angularVelocity = ANGULAR_VELOCITY;
      }
    }

    if (Math.abs(direction - angleTo(target)) < EPSILON) {
      position.setLocation(
          position.getX() + velocity * Math.cos(direction) * dt,
          position.getY() + velocity * Math.sin(direction) * dt
      );

      return;
    }

    final double newAngle = asNormalizedRadians(direction + angularVelocity * dt);

    final double dx = velocity / angularVelocity * (Math.sin(newAngle) - Math.sin(direction));
    final double dy = velocity / angularVelocity * (Math.cos(newAngle) - Math.cos(direction));

    position.setLocation(
        position.getX() + dx * speedCoefficientX(windowBound.getX()),
        position.getY() - dy * speedCoefficientY(windowBound.getY())
    );
    direction = newAngle;
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
  }

  @Override
  public void restore() {
        var preferences = getPreferences();

        double robotX = preferences.getDouble(formatTitle("robot x"), 100);
        double robotY = preferences.getDouble(formatTitle("robot y"), 100);
        double robotDirection = preferences.getDouble(formatTitle("robot direction"), 0);

        position.setLocation(robotX, robotY);
        direction = robotDirection;
  }
}
