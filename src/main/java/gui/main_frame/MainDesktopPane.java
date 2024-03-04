package gui.main_frame;

import gui.GameWindow;
import gui.LogWindow;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import log.Logger;

public class MainDesktopPane extends JDesktopPane {

  public MainDesktopPane() {
    super();

    addWindow(createLogWindow(), 300, 800);
    addWindow(new GameWindow(), 400, 400);
  }

  private LogWindow createLogWindow() {
    LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

    logWindow.setLocation(10, 10);

    setMinimumSize(logWindow.getSize());
    logWindow.pack();

    Logger.debug("Протокол работает");

    return logWindow;
  }

  private void addWindow(JInternalFrame frame, int width, int height) {
    frame.setSize(width, height);
    add(frame);
    frame.setVisible(true);
  }
}