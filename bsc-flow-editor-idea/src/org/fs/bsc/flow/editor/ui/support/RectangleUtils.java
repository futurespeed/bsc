package org.fs.bsc.flow.editor.ui.support;

import java.awt.*;

public class RectangleUtils {

    public static Point getMiddlePoint(Point selfPoint, Dimension selfSize) {
        return new Point(selfPoint.x + selfSize.width / 2, selfPoint.y + selfSize.height / 2);
    }

    public static Point getConnectPoint(Point selfPoint, Dimension selfSize, Point otherPoint) {
        double dx = Double.valueOf(otherPoint.x - selfPoint.x);
        if (0 == dx) {
            dx = 1;
        }
        double gradient = Double.valueOf(otherPoint.y - selfPoint.y) / dx;
        double d = Math.toDegrees(Math.atan(gradient));
        if (otherPoint.x < selfPoint.x) {
            d = d + 180;
        } else if (otherPoint.y < selfPoint.y) {
            d = d + 360;
        }
        d = d % 360;

        int pointPos = 0; // 0-top 1-left 2-right 3-bottom
        if (d >= 45 && d < 135) {
            pointPos = 3;
        } else if (d >= 135 && d < 225) {
            pointPos = 1;
        } else if (d >= 315 || d < 45) {
            pointPos = 2;
        }

        Point connectPoint = new Point(selfPoint.x, selfPoint.y);
        if (0 == pointPos) {
            connectPoint = new Point(selfPoint.x + selfSize.width / 2, selfPoint.y);
        } else if (1 == pointPos) {
            connectPoint = new Point(selfPoint.x, selfPoint.y + selfSize.height / 2);
        } else if (2 == pointPos) {
            connectPoint = new Point(selfPoint.x + selfSize.width, selfPoint.y + selfSize.height / 2);
        } else if (3 == pointPos) {
            connectPoint = new Point(selfPoint.x + selfSize.width / 2, selfPoint.y + selfSize.height);
        }
        return connectPoint;
    }
}
