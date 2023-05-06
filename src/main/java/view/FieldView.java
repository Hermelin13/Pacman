/**************************************************
 * AUTHORS: Adam Dalibor Jurčík (xjurci08)
 * Description: Implementation of maze gui
 */

package view;

import common.Field;
import common.MazeObject;
import common.Observable;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class FieldView extends JPanel implements Observable.Observer {
    private final Field model;
    private final List<ComponentView> objects;
    private int changedModel = 0;

    public interface FieldChangedCallback {
        void onFieldChanged();
    }


    private FieldChangedCallback callback;

    public FieldView(Field model, FieldChangedCallback callback) {
        this.model = model;
        this.objects = new ArrayList();
        this.callback = callback;
        this.privUpdate();
        model.addObserver(this);
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.objects.forEach((v) -> {
            v.paintComponent(g);
        });
    }

    private void privUpdate() {
        if (this.model.canMove()) {
            this.setBackground(Color.white);
            if (!this.model.isEmpty()) {
                MazeObject o = this.model.get();
                ComponentView v;
                if (o.isPacman()) {
                    v = new PacmanView(this, this.model.get());
                    this.objects.add(v);
                } else if (o.isGhost()) {
                    v = new GhostView(this, this.model.get());
                    this.objects.add(v);
                } else if (o.isTarget()) {
                    v = new TargetView(this, this.model.get());
                    this.objects.add(v);

                } else {
                    this.objects.clear();
                    v = new KeyView(this, this.model.get());
                    this.objects.add(v);
                }

            } else {
                this.objects.clear();
            }
        } else {
            this.setBackground(Color.lightGray);
        }

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public final void update(Observable field) {
        ++this.changedModel;
        this.privUpdate();
        // Call the callback function
        if (this.callback != null) {
            this.callback.onFieldChanged();
        }
    }

    public int numberUpdates() {
        return this.changedModel;
    }

    public void clearChanged() {
        this.changedModel = 0;
    }

    public Field getField() {
        return this.model;
    }
}