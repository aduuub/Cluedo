package view;

import cluedo.Cluedo;
import cluedo.Game;
import util.CluedoError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Adam on 14/08/16.
 */
public class MenuPanel extends JPanel implements ActionListener {

    private final int BUTTON_WIDTH = 120;
    private final int BUTTON_HEIGHT = 40;
    private Game game;

    public MenuPanel(Game game) {
        this.game = game;
        intitButtons();
        setBackground(Color.black);
        setPreferredSize(new Dimension(BoardFrame.BOARD_WIDTH - 500, BoardFrame.BOARD_HEIGHT));
    }

    public void intitButtons() {

        // Move Button
        JButton b1 = new JButton("Move");
        b1.setActionCommand("Move");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        b1.addActionListener(this);
        add(b1, BorderLayout.LINE_END);

        // Show Hand
        b1 = new JButton("Show Hand");
        b1.setActionCommand("Show_Hand");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        b1.addActionListener(this);
        add(b1, BorderLayout.LINE_END);

        // Suggest
        b1 = new JButton("Suggest");
        b1.setActionCommand("Suggest");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        b1.addActionListener(this);
        add(b1, BorderLayout.LINE_END);

        // Accuse
        b1 = new JButton("Accuse");
        b1.setActionCommand("Accuse");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        b1.addActionListener(this);
        add(b1, BorderLayout.LINE_END);

        // End turn
        b1 = new JButton("End Turn");
        b1.setActionCommand("End_Turn");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        b1.addActionListener(this);
        add(b1, BorderLayout.LINE_END);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Move": break;
            case "Suggest": break;
            case "Accuse": break;
            case "End_Turn": break;
            default: throw new CluedoError("Unrecognised button action command");
        }
    }
}
