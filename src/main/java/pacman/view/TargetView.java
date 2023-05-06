/**************************************************
 * AUTHORS:Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of target view
 */

package pacman.view;

import pacman.common.MazeObject;
import pacman.view.ComponentView;
import pacman.view.FieldView;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TargetView implements ComponentView {
    private final MazeObject model;
    private final FieldView parent;

    public TargetView(FieldView parent, MazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, w, h);
        g2.setColor(Color.decode("#3d8c40"));
        g2.fill(rect);
    }
}