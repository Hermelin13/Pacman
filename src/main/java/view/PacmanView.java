/**************************************************
 * AUTHORS: Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of pacman view
 */

package view;

import common.MazeObject;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class PacmanView implements ComponentView {
    private MazeObject model;
    private FieldView parent;

    public PacmanView(FieldView parent, MazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        Math.max(h, w);
        double diameter = Math.min(h, w) - 10.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, diameter, diameter);
        g2.setColor(Color.decode("#FCE205"));
        g2.fill(ellipse);
    }
}

