package game;

public class Target {
  private static final double DEFAULT_X = 150;
  private static final double DEFAULT_Y = 100;

  private double x, y;

  public Target() {
    this.x = DEFAULT_X;
    this.y = DEFAULT_Y;
  }

  public Target(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }
}
