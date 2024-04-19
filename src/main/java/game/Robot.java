package game;

import java.awt.geom.Point2D;
import java.util.prefs.Preferences;
import serialization.IStorable;

public class Robot implements IStorable {
  private final Point2D.Double position = new Point2D.Double(100, 100);
  private double direction = 0;
  private double angularVelocity = 0;
  private final double velocity = 0.1;

  private final Point2D.Double lastTargetPosition = new Point2D.Double(position.getX(), position.getY());

  private static String formatTitle(String title) {
    String cased = title.toUpperCase();

    return cased.replaceAll(" +", "_");
  }

  public void move(Point2D.Double dv) {
    position.setLocation(getPosition().getX() + dv.getX(), getPosition().getY() + dv.getY());
  }

  public double getVelocity() {
    return velocity;
  }

  public Point2D.Double getPosition() {
    return position;
  }

  public double getDirection() {
    return direction;
  }

  public void setDirection(double direction) {
    this.direction = direction;
  }

  public void setAngularVelocity(double angularVelocity) {
    this.angularVelocity = angularVelocity;
  }

  public double getAngularVelocity() {
    return angularVelocity;
  }

  private Preferences getPreferences() {
    return Preferences.userRoot().node("robot preferences");
  }

  @Override
  public void save() {
    var preferences = getPreferences();

    preferences.putDouble(formatTitle("robot x"), getPosition().getX());
    preferences.putDouble(formatTitle("robot y"), getPosition().getY());
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
