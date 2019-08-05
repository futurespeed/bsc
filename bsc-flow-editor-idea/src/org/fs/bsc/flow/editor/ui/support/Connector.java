package org.fs.bsc.flow.editor.ui.support;

import javax.swing.*;
import java.awt.*;

public class Connector extends JPanel {

    private Point start;
    private Point end;
    private Color lineColor = Color.BLACK;
    private int arrowSize = 4;

    public Connector(){
        setBackground(Color.YELLOW);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(lineColor);
        g.drawLine(start.x - getX(), start.y - getY(), end.x  - getX(), end.y - getY());
        for(int i = 1; i <= arrowSize; i++){
            g.drawLine(start.x - getX() + (end.x - start.x) - i, start.y - getY() + (end.y - start.y) - i, end.x  - getX(), end.y - getY());
        }
    }

    public void connect(Point start, Point end){
        this.start = start;
        this.end = end;
        setLocation(Math.min(start.x, end.x), Math.min(start.y, end.y));
        setSize(Math.abs(start.x - end.x) + 1, Math.abs(start.y - end.y) + 1);
        repaint();
    }
}
