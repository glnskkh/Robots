package gui;

import game.GameLogic;
import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import serialization.PreferenceStorableInternalFrame;

public class GameInfoWindow extends PreferenceStorableInternalFrame implements Observer {

  private final JLabel label;

  public GameInfoWindow(GameLogic logic) {
    super();

    setResizable(true);
    setClosable(true);
    setMaximizable(true);
    setIconifiable(true);

    label = new JLabel();
    getContentPane().add(label, BorderLayout.CENTER);

    pack();

    logic.addObserver(this);
  }

  @Override
  public void update(Observable o, Object arg) {
    GameLogic gameLogic = (GameLogic) o;

    label.setText("x=%f y=%f direction=%f".formatted(gameLogic.getRobot().getPosition().getX(),
        gameLogic.getRobot().getPosition().getY(), gameLogic.getRobot().getDirection()));
  }
}
