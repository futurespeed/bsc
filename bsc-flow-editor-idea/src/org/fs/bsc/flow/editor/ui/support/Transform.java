package org.fs.bsc.flow.editor.ui.support;

import java.awt.*;

public class Transform {
    private double sin;
    private double cos = 1.0D;
    private double dy;
    private double dx;
    private double scaleY = 1.0D;
    private double scaleX = 1.0D;

    public void setScale(double scale) {
        scaleX = (scaleY = scale);
    }

    public void setScale(double x, double y) {
        scaleX = x;
        scaleY = y;
    }

    public void setRotation(double angle) {
        cos = Math.cos(angle);
        sin = Math.sin(angle);
    }

    public void setTranslation(double x, double y) {
        dx = x;
        dy = y;
    }

    public Point getTransformed(Point p) {
        double x = p.x;
        double y = p.y;
        x *= scaleX;
        y *= scaleY;
        double temp = x * cos - y * sin;
        y = x * sin + y * cos;
        x = temp;
        return new Point((int) Math.round(x + dx), (int) Math.round(y + dy));
    }
}