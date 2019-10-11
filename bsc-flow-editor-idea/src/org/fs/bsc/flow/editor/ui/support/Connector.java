package org.fs.bsc.flow.editor.ui.support;

import org.fs.bsc.flow.editor.selection.Selectable;
import org.fs.bsc.flow.editor.ui.BscFlowDesignPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Connector extends JPanel implements Selectable {

    private Point start;
    private Point end;
    private Color lineColor = Color.BLACK;
    private int arrowSize = 6;

    public Connector() {
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(getX(), getY(), getWidth(), getHeight());
        super.paintComponent(g);
        g.setColor(lineColor);
        g.drawLine(start.x - getX(), start.y - getY(), end.x - getX(), end.y - getY());

        // Arrow
        Point relateEndPoint = new Point(end.x - getX(), end.y - getY());
        double dx = Double.valueOf(end.x - start.x);
        if (0 == dx) {
            dx = 1;
        }
        double gradient = Double.valueOf(end.y - start.y) / dx;
        Arrow arrow = new Arrow();
        arrow.setScale(arrowSize);
        if (end.x < start.x) {
            // add 180 degrees
            arrow.setRotation(Math.toRadians(Math.toDegrees(Math.atan(gradient)) + 180));
        } else {
            arrow.setRotation(Math.atan(gradient));
        }
        arrow.setTranslation(relateEndPoint.x, relateEndPoint.y);
        List<Point> points = arrow.getPoints();
        Point p0 = points.get(0);
        Point p1 = points.get(1);
        Point p2 = points.get(2);
        Polygon arrowPolygon = new Polygon(new int[]{p0.x, p1.x, p2.x}, new int[]{p0.y, p1.y, p2.y}, 3);
        g.fillPolygon(arrowPolygon);
    }

    public void connect(Point start, Point end) {
        this.start = start;
        this.end = end;
        setLocation(Math.min(start.x, end.x) - arrowSize, Math.min(start.y, end.y) - arrowSize);
        setSize(Math.abs(start.x - end.x) + arrowSize * 2, Math.abs(start.y - end.y) + arrowSize * 2);
        repaint();
        repaintParent();
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    @Override
    public boolean selected() {
        lineColor = Color.PINK;
        repaint();
        repaintParent();
        return true;
    }

    @Override
    public boolean deselected() {
        lineColor = Color.BLACK;
        repaint();
        repaintParent();
        return true;
    }

    protected void repaintParent(){
        if(null == getParent()) {
            return;
        }
        getParent().repaint();
    }

    public static class Arrow {
        private final Point p0 = new Point(0, 0);
        private final Point p1 = new Point(-1, 1);
        private final Point p2 = new Point(-1, -1);

        private Transform transform = new Transform();

        public void setRotation(double angle) {
            transform.setRotation(angle);
        }

        public void setScale(double scale) {
            transform.setScale(scale);
        }

        public void setTranslation(double x, double y) {
            transform.setTranslation(x, y);
        }

        public List<Point> getPoints() {
            List<Point> points = new ArrayList<>();
            points.add(transform.getTransformed(p0));
            points.add(transform.getTransformed(p1));
            points.add(transform.getTransformed(p2));
            return points;
        }
    }
}
