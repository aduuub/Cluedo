package view;

import cluedo.Cluedo;
import cluedo.Game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Adam on 8/08/16.
 */
public class BoardFrame extends JFrame {

    public static final int BOARD_WIDTH = 700;
    public static final int BOARD_HEIGHT = 540;

    private final BoardCanvas canvas;
    private final Cluedo cluedo;
    private final Toolkit toolkit;

    private JPanel menuPanel;

    public BoardFrame(String title, Cluedo cluedo, KeyListener[] keys) {
        super(title);
        setLayout(new BorderLayout());

        this.toolkit = getToolkit();
        this.cluedo = cluedo;
        this.canvas = new BoardCanvas(cluedo);
        this.menuPanel = new MenuPanel(cluedo);
        add(menuPanel, BorderLayout.LINE_END);

        // Display window
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setVisible(true);
        setResizable(true);
        pack();

        // Title and Menu bar
        setTitle("Cluedo");
        setMenuBar();

        // Layout
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.requestFocus();

        CharachtersDialog dialog = new CharachtersDialog(this, cluedo);
    }

    public void setMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        JCheckBoxMenuItem cbMenuItem;

        // Create the menu bar.
        menuBar = new JMenuBar();

        /* Init the file menu. */
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        // File menu items
        menuItem = new JMenuItem("New Game",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Undo",
                KeyEvent.VK_T);
        menu.add(menuItem);

        // Quit
        cbMenuItem = new JCheckBoxMenuItem("Quit");
        cbMenuItem.addActionListener(e -> System.exit(0));
        menu.add(cbMenuItem);


        // Option Menu
        menu = new JMenu("Options");

        // Sound
        cbMenuItem = new JCheckBoxMenuItem("Enable Sound");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        menu.add(cbMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public void repaint() {
        canvas.repaint();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Cluedo cluedo = new Cluedo();
            BoardFrame ex = new BoardFrame("Adam", cluedo, new KeyListener[0]);
            ex.setVisible(true);

        });
    }
}