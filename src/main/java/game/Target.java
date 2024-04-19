package game;

import java.awt.geom.Point2D;
import java.util.prefs.Preferences;
import serialization.IStorable;

public class Target implements IStorable {
  private final Point2D.Double position = new Point2D.Double();

  public Target(double x, double y) {
    position.setLocation(x, y);
  }

  public Target() {}

  public Point2D.Double getPosition() {
    return position;
  }

  private Preferences getPreferences() {
    return Preferences.userRoot().node("target preferences");
  }

  private static String formatTitle(String title) {
    String cased = title.toUpperCase();

    return cased.replaceAll(" +", "_");
  }

  @Override
  public void save() {
    var preferences = getPreferences();

    preferences.putDouble(formatTitle("target x"), getPosition().getX());
    preferences.putDouble(formatTitle("target y"), getPosition().getY());
  }

  @Override
  public void restore() {
    var preferences = getPreferences();

    double targetX = preferences.getDouble(formatTitle("target x"), 100);
    double targetY = preferences.getDouble(formatTitle("target y"), 150);

    position.setLocation(targetX, targetY);
  }
}