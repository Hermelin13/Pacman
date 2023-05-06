/**************************************************
 * AUTHORS: Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of view key
 */

package pacman.view;

import pacman.common.MazeObject;
import pacman.view.ComponentView;
import pacman.view.FieldView;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class KeyView implements ComponentView {
    private final MazeObject model;
    private final FieldView parent;

    public KeyView(FieldView parent, MazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        Math.max(h, w);
        double diameter = Math.min(h, w) - 20.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;
        Rectangle2D.Double rect = new Rectangle2D.Double(x, y, diameter, diameter);
        g2.setColor(Color.decode("#3d8c40"));
        g2.fill(rect);
    }
}