package gui.main_frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainApplicationFrame extends JFrame {

  public MainApplicationFrame() {
    setInnerIndentation(50);

    setContentPane(new MainDesktopPane());
    setJMenuBar(new MainMenuBar(this));

    setUpClosingLogic();
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
}
