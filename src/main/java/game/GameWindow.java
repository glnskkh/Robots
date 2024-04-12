package game;

import gui.MainApplicationFrame;
import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import serialization.PreferenceStorableInternalFrame;

public class GameWindow extends PreferenceStorableInternalFrame {

  private final GameVisualizer gameVisualizer;
  private final GameLogic logic;

  public GameWindow(GameLogic logic) {
    super();

    this.logic = logic;

    logic.startTimer();

    setTitle(MainApplicationFrame.getLocaleString("gameWindow.title"));
    setResizable(true);
    setClosable(true);
    setMaximizable(true);
    setIconifiable(true);

    gameVisualizer = new GameVisualizer(logic);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(gameVisualizer, BorderLayout.CENTER);
    getContentPane().add(panel);
    pack();
  }

  public void dispose() {
    super.dispose();

    logic.stopTimer();
  }
}
