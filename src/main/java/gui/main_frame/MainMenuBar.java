package gui.main_frame;

import java.awt.event.ActionListener;
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

    this.add(createLookAndFeelMenu());

    this.add(createTestMenu());

    add(createMenuItem("Закрыть", KeyEvent.VK_C, (event) -> {
      mainFrame.callCloseDialog();
    }));
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
      mainFrame.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      mainFrame.invalidate();
    }));

    lookAndFeelMenu.add(createMenuItem("Универсальная схема", KeyEvent.VK_S, (event) -> {
      mainFrame.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      mainFrame.invalidate();
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
}
