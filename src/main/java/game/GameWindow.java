package game;

import gui.MainApplicationFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import serialization.PreferenceStorableInternalFrame;

public class GameWindow extends PreferenceStorableInternalFrame {

  private final GameVisualizer gameVisualizer;

  public GameWindow(GameLogic logic) {
    super();

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
}
