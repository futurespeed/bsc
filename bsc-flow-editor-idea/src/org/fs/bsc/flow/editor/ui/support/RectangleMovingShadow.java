package org.fs.bsc.flow.editor.ui.support;

import javax.swing.*;
import java.awt.*;

public class RectangleMovingShadow extends AbstractFrameSkipAdapter {

    private JPanel panel;

    private Point location;

    private Dimension size;

    public RectangleMovingShadow() {
        panel = new JPanel();
        panel.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        panel.setBackground(Color.GREEN);
        panel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        hide();
        setFps(60);
    }

    public void show() {
        panel.setVisible(true);
    }

    public void hide() {
        panel.setVisible(false);
    }

    public void setLocation(Point location) {
        setLocation(location, true);
    }

    public void setLocation(Point location, boolean move) {
        this.location = location;
        if (move) {
            this.panel.setLocation(location);
        }
    }

    public Point getLocation() {
        return location;
    }

    public void setSize(Dimension size) {
        setSize(size, true);
    }

    public void setSize(Dimension size, boolean resize) {
        this.size = size;
        if (resize) {
            this.panel.setSize(size);
        }
    }

    public Dimension getSize() {
        return size;
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    protected void doGo() {
        panel.setLocation(location);
    }
}
