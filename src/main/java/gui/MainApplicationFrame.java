package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
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
import serialization.PreferenceStorable;

public class MainApplicationFrame extends JFrame {

  private static ResourceBundle rb;
  private JMenuBar mainMenuBar;
  private JDesktopPane mainDesktopPane;

  public MainApplicationFrame() {
    setInnerIndentation(50);

    createDesktopPane();
    createMainMenuBar();
    setJMenuBar(mainMenuBar);

    setUpClosingLogic();
  }

  public static String getLocaleString(String key) {
    return rb.getString(key);
  }

  @Override
  public void setLocale(Locale l) {
    super.setLocale(l);
    rb = ResourceBundle.getBundle("MainApplicationFrameLocale", this.getLocale());
  }

  private void createDesktopPane() {
    mainDesktopPane = new JDesktopPane();

    addWindow(createLogWindow(), 300, 800);
    addWindow(new GameWindow(), 400, 400);

    for (var frame : mainDesktopPane.getAllFrames()) {
      ((PreferenceStorable) frame).restore();
    }

    setContentPane(mainDesktopPane);
  }

  private void setUpClosingLogic() {
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
    int confirm = JOptionPane.showConfirmDialog(this, getLocaleString("closeDialog.message"),
        getLocaleString("closeDialog.title"), JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
      setVisible(false);

      for (var frame : mainDesktopPane.getAllFrames()) {
        ((PreferenceStorable) frame).save();
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

    mainMenuBar.add(createMenuItem(getLocaleString("closeDialog.title"), KeyEvent.VK_C, (event) -> {
      callCloseDialog();
    }));

    mainMenuBar.add(createLocaleMenu());

    setJMenuBar(mainMenuBar);
  }

  private JMenuItem createMenuItem(String text, int mnemonic, ActionListener action) {
    JMenuItem menuItem = new JMenuItem(text, mnemonic);

    menuItem.addActionListener(action);

    return menuItem;
  }

  private JMenu createLocaleMenu() {
    JMenu localeMenu = new JMenu(getLocaleString("localeMenu.text"));

    localeMenu.setMnemonic(KeyEvent.VK_L);
    localeMenu.getAccessibleContext()
        .setAccessibleDescription(getLocaleString("localeMenu.accessible"));

    localeMenu.add(createMenuItem(getLocaleString("locale_ru_RU.text"), KeyEvent.VK_R, (event) -> {
      setLocale(new Locale("ru", "RU"));
    }));

    localeMenu.add(createMenuItem(getLocaleString("locale_en_US.text"), KeyEvent.VK_R, (event) -> {
      setLocale(new Locale("en", "US"));
    }));

    return localeMenu;
  }

  private JMenu createLookAndFeelMenu() {
    JMenu lookAndFeelMenu = new JMenu(getLocaleString("lookAndFeelMenu.text"));

    lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
    lookAndFeelMenu.getAccessibleContext()
        .setAccessibleDescription(getLocaleString("lookAndFeelMenu.accessible"));

    lookAndFeelMenu.add(
        createMenuItem(getLocaleString("systemLookAndFeel.text"), KeyEvent.VK_S, (event) -> {
          setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          invalidate();
        }));

    lookAndFeelMenu.add(
        createMenuItem(getLocaleString("crossplatformLookAndFeel.text"), KeyEvent.VK_S, (event) -> {
          setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
          invalidate();
        }));

    return lookAndFeelMenu;
  }

  private JMenu createTestMenu() {
    JMenu testMenu = new JMenu(getLocaleString("testMenu.text"));
    testMenu.setMnemonic(KeyEvent.VK_T);
    testMenu.getAccessibleContext()
        .setAccessibleDescription(getLocaleString("testMenu.accessible"));

    testMenu.add(
        createMenuItem(getLocaleString("addLogMessageItem.text"), KeyEvent.VK_S, (event) -> {
          Logger.debug(getLocaleString("Logger.message001"));
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

    Logger.debug(getLocaleString("Logger.message002"));

    return logWindow;
  }
}
