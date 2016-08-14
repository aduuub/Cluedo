package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by javahemans on 14/08/16.
 */
public class selectCharachters extends JDialog{

    JButton sc;
    JButton mustard;
    JButton peacock;
    JButton white;
    JButton green;
    JButton plum;

    public selectCharachters(JFrame parent) {
        super(parent, "Selecting Characters ...", false);



        setSize(400,300);

        sc =  new JButton("Miss Scarlet");
        mustard = new JButton("Colonel Mustard");
        peacock = new JButton("Mrs Peacock);
        white = new JButton("Mrs White");
        green = new JButton("Mr Green");
        plum  = new JButton("Professor Plum");
        setLayout(new FlowLayout());
        this.add(sc);
        this.add(mustard);
        this.add(peacock);
        this.add(white);
        this.add(green);
        this.add(plum);
    }
}
