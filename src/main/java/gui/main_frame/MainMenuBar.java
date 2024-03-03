package gui.main_frame;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import log.Logger;

public class MainMenuBar extends JMenuBar {

  private final MainApplicationFrame mainFrame;

  public MainMenuBar(MainApplicationFrame mainFrame) {
    super();

    this.mainFrame = mainFrame;

    this.add(getLookAndFeelMenu());
    this.add(getTestMenu());
    this.add(getCloseMenuItem());
  }

  private static JMenuItem getAddLogMessageMenuItem() {
    JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);

    addLogMessageItem.addActionListener((event) -> {
      Logger.debug("Новая строка");
    });

    return addLogMessageItem;
  }

  private JMenu getLookAndFeelMenu() {
    JMenu lookAndFeelMenu = new JMenu("Режим отображения");

    lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
    lookAndFeelMenu.getAccessibleContext()
        .setAccessibleDescription("Управление режимом отображения приложения");

    lookAndFeelMenu.add(getSystemLookAndFeelMenuItem());
    lookAndFeelMenu.add(getCrossPlatformLookAndFeel());

    return lookAndFeelMenu;
  }

  private JMenuItem getCrossPlatformLookAndFeel() {
    JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);

    crossplatformLookAndFeel.addActionListener((event) -> {
      mainFrame.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      mainFrame.invalidate();
    });

    return crossplatformLookAndFeel;
  }

  private JMenuItem getSystemLookAndFeelMenuItem() {
    JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);

    systemLookAndFeel.addActionListener((event) -> {
      mainFrame.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      mainFrame.invalidate();
    });

    return systemLookAndFeel;
  }

  private JMenu getTestMenu() {
    JMenu testMenu = new JMenu("Тесты");
    testMenu.setMnemonic(KeyEvent.VK_T);
    testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");

    testMenu.add(getAddLogMessageMenuItem());

    return testMenu;
  }

  private JMenuItem getCloseMenuItem() {
    JMenuItem closeMenuItem = new JMenuItem("Закрыть", KeyEvent.VK_C);
    closeMenuItem.getAccessibleContext().setAccessibleDescription("Закрыть приложение");

    closeMenuItem.addActionListener((event) -> {
      mainFrame.callCloseDialog();
    });

    return closeMenuItem;
  }
}
