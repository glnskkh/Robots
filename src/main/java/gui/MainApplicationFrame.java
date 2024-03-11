package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import log.Logger;

public class MainApplicationFrame extends JFrame {

  private JMenuBar mainMenuBar;
  private JDesktopPane mainDesktopPane;

  public MainApplicationFrame() {
    setInnerIndentation(50);

    createDesktopPane();
    createMainMenuBar();
    setJMenuBar(mainMenuBar);

    setUpClosingLogic();
  }

  private void createDesktopPane() {
    mainDesktopPane = new JDesktopPane();

    addWindow(createLogWindow(), 300, 800);
    addWindow(new GameWindow(), 400, 400);

    setContentPane(mainDesktopPane);
  }

  private void setUpClosingLogic() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        MainApplicationFrame.this.callCloseDialog();
      }
    });
  }

  private void setInnerIndentation(int indent) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    final int indentedWidth = screenSize.width - indent * 2;
    final int indentedHeight = screenSize.height - indent * 2;

    setBounds(indent, indent, indentedWidth, indentedHeight);
  }

  void callCloseDialog() {
    int confirm = JOptionPane.showConfirmDialog(this, "Закрыть приложение?", "Закрыть",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
      setVisible(false);

      for (var frame : mainDesktopPane.getAllFrames()) {
        System.out.println(frame.getX());
        frame.dispose();
      }

      dispose();
    }
  }

  void setLookAndFeel(String className) {
    try {
      UIManager.setLookAndFeel(className);
      SwingUtilities.updateComponentTreeUI(this);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
             UnsupportedLookAndFeelException e) {
      // just ignore
    }
  }

  private void createMainMenuBar() {
    mainMenuBar = new JMenuBar();

    mainMenuBar.add(createLookAndFeelMenu());
    mainMenuBar.add(createTestMenu());

    mainMenuBar.add(createMenuItem("Закрыть", KeyEvent.VK_C, (event) -> {
      callCloseDialog();
    }));

    setJMenuBar(mainMenuBar);
  }

  private JMenuItem createMenuItem(String text, int mnemonic, ActionListener action) {
    JMenuItem menuItem = new JMenuItem(text, mnemonic);

    menuItem.addActionListener(action);

    return menuItem;
  }

  private JMenu createLookAndFeelMenu() {
    JMenu lookAndFeelMenu = new JMenu("Режим отображения");

    lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
    lookAndFeelMenu.getAccessibleContext()
        .setAccessibleDescription("Управление режимом отображения приложения");

    lookAndFeelMenu.add(createMenuItem("Системная схема", KeyEvent.VK_S, (event) -> {
      setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      invalidate();
    }));

    lookAndFeelMenu.add(createMenuItem("Универсальная схема", KeyEvent.VK_S, (event) -> {
      setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      invalidate();
    }));

    return lookAndFeelMenu;
  }

  private JMenu createTestMenu() {
    JMenu testMenu = new JMenu("Тесты");
    testMenu.setMnemonic(KeyEvent.VK_T);
    testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");

    testMenu.add(createMenuItem("Сообщение в лог", KeyEvent.VK_S, (event) -> {
      Logger.debug("Новая строка");
    }));

    return testMenu;
  }

  private void addWindow(JInternalFrame frame, int width, int height) {
    frame.setSize(width, height);
    addWindow(frame);
  }

  private void addWindow(JInternalFrame frame) {
    mainDesktopPane.add(frame);
    frame.setVisible(true);
  }

  private LogWindow createLogWindow() {
    LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());

    logWindow.setLocation(10, 10);

    setMinimumSize(logWindow.getSize());
    logWindow.pack();

    Logger.debug("Протокол работает");

    return logWindow;
  }
}
