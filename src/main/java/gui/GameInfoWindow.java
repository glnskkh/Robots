package gui;

import game.GameLogic;
import game.Robot;
import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import serialization.PreferenceStorableInternalFrame;

public class GameInfoWindow extends PreferenceStorableInternalFrame implements Observer {

  private final Robot robot;
  private JLabel label;

  public GameInfoWindow(GameLogic logic) {
    super();

    this.robot = logic.getRobot();

    robot.addObserver(this);

    setResizable(true);
    setClosable(true);
    setMaximizable(true);
    setIconifiable(true);

    label = new JLabel();
    getContentPane().add(label, BorderLayout.CENTER);

    pack();
  }

  @Override
  public void update(Observable o, Object arg) {
    label.setText("x=%f y=%f\ndirection=%f".formatted(robot.getX(), robot.getY(), robot.getDirection()));
  }
}
