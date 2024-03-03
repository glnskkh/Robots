package gui.main_frame;

import gui.GameWindow;
import gui.LogWindow;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import log.Logger;

public class MainDesktopPane extends JDesktopPane {

  public MainDesktopPane() {
    super();

    addWindow(getLogWindow());
    addWindow(getGameWindow());
  }

  private LogWindow getLogWindow() {
    LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

    logWindow.setLocation(10, 10);
    logWindow.setSize(300, 800);

    setMinimumSize(logWindow.getSize());
    logWindow.pack();

    Logger.debug("Протокол работает");

    return logWindow;
  }

  private GameWindow getGameWindow() {
    GameWindow gameWindow = new GameWindow();

    gameWindow.setSize(400, 400);

    return gameWindow;
  }

  private void addWindow(JInternalFrame frame) {
    add(frame);
    frame.setVisible(true);
  }
}