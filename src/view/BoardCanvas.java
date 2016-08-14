// This file is part of the Multi-player Pacman Game.
//
// Pacman is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published
// by the Free Software Foundation; either version 3 of the License,
// or (at your option) any later version.
//
// Pacman is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
// the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public
// License along with Pacman. If not, see <http://www.gnu.org/licenses/>
//
// Copyright 2010, David James Pearce.
// UPDATED 03/06/2010, Terrence Miller

package view;

import cluedo.Cluedo;
import cluedo.Game;

import java.io.*;
import java.util.HashSet;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The board canvas is responsible for drawing the game. Currently, it uses a
 * relatively primitive form of double buffering to ensure there's no flicker
 * during frame updates. This class also generates a number of images using
 * Java's graphics capabilities, which saves having to have lots of very similar
 * images for the different directions.
 *
 * @author djp
 */
public class BoardCanvas extends Canvas {
    private static final String IMAGE_PATH = "images/";

    private static final String[] preferredFonts = {"Courier New", "Arial", "Times New Roman"};
    private Font font;
    private Cluedo cluedo;
    private final Game game;
    private final Board board = null;
    private Image boardImage;

    public BoardCanvas(Cluedo cluedo) {
        this.cluedo = cluedo;
        this.game = cluedo.getGame();

        this.boardImage = loadImage("boardImage.png");
        setVisible(true);
    }


    @Override
    public void paint(Graphics g) {
       g.drawImage(boardImage, 0, 0, null);
    }

    public void setUpCharacter() {

    }


    private void drawMessage(String msg, Graphics g) {
        g.setFont(font);
        int width = board.getWidth();
        int height = board.getHeight();
        FontMetrics metrics = g.getFontMetrics();
        int ascent = metrics.getAscent();
        char[] chars = msg.toCharArray();
        int msgWidth = metrics.charsWidth(msg.toCharArray(), 0, msg.length());
        int msgHeight = metrics.getHeight();
        int boxWidth = msgWidth + 30;
        int boxHeight = msgHeight + 30;
        int x = ((width * 30) - boxWidth) / 2;
        int y = ((height * 30) - boxHeight) / 2;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, boxWidth, boxHeight);
        g.setColor(Color.yellow);
        g.drawRect(x, y, boxWidth, boxHeight);
        g.drawRect(x + 1, y + 1, boxWidth - 2, boxHeight - 2);
        g.drawChars(chars, 0, chars.length, x + 15, y + 15 + ascent);
        offscreen = null; // reset offscreen, since we want to get rid of the
        // message
    }


    private Image offscreen = null;

    public void update(Graphics g) {
        if (offscreen == null) {
            Image localOffscreen = offscreen;
            Graphics offgc = offscreen.getGraphics();
            // do normal redraw
            paint(offgc);
            // transfer offscreen to window
            g.drawImage(localOffscreen, 0, 0, this);
        }
    }

    /**
     * Rotate an image a given number of degrees.
     *
     * @param src
     * @param angle
     * @return
     */
    public static Image rotate(Image src, double angle) {
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.rotate(Math.toRadians(angle), width / 2, height / 2);
        g.drawImage(src, 0, 0, width, height, null);
        g.dispose();
        return img;
    }

    /**
     * Load an image from the file system, using a given filename.
     *
     * @param filename
     * @return
     */
    public static Image loadImage(String filename) {
        try {
            return ImageIO.read(new File(IMAGE_PATH + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
