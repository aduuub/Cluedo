package view;

import cluedo.Cluedo;
import cluedo.Game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Adam on 8/08/16.
 */
public class BoardFrame extends JFrame {

    private final BoardCanvas canvas;
    private final Game game;
    private final Toolkit toolkit;


    public BoardFrame(String title, Game game, KeyListener[] keys) {
        super(title);
        this.toolkit = getToolkit();
        this.game = game;
        this.canvas = new BoardCanvas(game);

        // Set size
        Dimension scrnsize = toolkit.getScreenSize();
        setPreferredSize(new Dimension((int) ((scrnsize.width - getWidth()) / 1.5), (int) ((scrnsize.height - getHeight()) / 1.5)));

        // Display window
        setVisible(true);
        pack();
        setResizable(true);

        // Title and Menu bar
        setTitle("Cluedo");
        setMenuBar();

        setLayout(new BorderLayout());
        for (KeyListener k : keys) {
            canvas.addKeyListener(k);
        }
        add(canvas, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.requestFocus();
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
            BoardFrame ex = new BoardFrame("Adam", new Game(null, null, null, null, null), new KeyListener[0]);
            ex.setVisible(true);
        });
    }
}