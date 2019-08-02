package org.fs.bsc.flow.editor.ui.support;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditableRectangle extends JPanel implements Drawable, Resizable {

    private Point position;
    private Point newPosition;
    private Dimension relateDimension;

    private Dimension size;

    private boolean drawing;
    private boolean hover;

    private boolean enableResize;

    public EditableRectangle(Point position, Dimension size, boolean enableResize) {
        this.position = position;
        this.size = size;
        this.enableResize = enableResize;
        init();
    }

    protected void init() {
        setLocation(position);
        setSize(size);
        setCursor(new Cursor(Cursor.MOVE_CURSOR));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if(enableResize) {
                    hover = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(enableResize) {
                    hover = false;
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                drawStart(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                drawStop(e.getPoint());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                drawMove(e.getPoint());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintControlVendor(g);
    }

    protected void paintControlVendor(Graphics g) {
        if (!hover) {
            return;
        }
        g.setColor(Color.GRAY);
        for (int i = 0; i < 4; i++) {
            g.drawLine(0, i, 3, i);//left-top
            g.drawLine(0, size.height - (i + 1), 3, size.height - (i + 1));//left-bottom
            g.drawLine(0, size.height / 2 + i - 2, 3, size.height / 2 + i - 2);//left-middle

            g.drawLine(size.width - 1, i, size.width - 4, i);//right-top
            g.drawLine(size.width - 1, size.height - (i + 1), size.width - 4, size.height - (i + 1));//right-bottom
            g.drawLine(size.width - 1, size.height / 2 + i - 2, size.width - 4, size.height / 2 + i - 2);//right-middle

            g.drawLine(size.width / 2 - 1, i, size.width / 2 + 2, i);//middle-top
            g.drawLine(size.width / 2 - 1, size.height - (i + 1), size.width / 2 + 2, size.height - (i + 1));//middle-bottom
        }
    }

    @Override
    public void drawStart(Point point) {
        relateDimension = new Dimension(point.x - position.x, point.y - position.y);
        newPosition = position;
        drawing = true;
    }

    @Override
    public void drawStop(Point point) {
        if (!drawing) {
            return;
        }
        drawing = false;
        position = newPosition;
        setLocation(position);
    }

    @Override
    public void drawMove(Point point) {
        if (!drawing) {
            return;
        }
        newPosition = new Point(point.x - relateDimension.width, point.y - relateDimension.height);
    }

    @Override
    public void changeWidth(int width) {
        size.width = width;
        setSize(size);
    }

    @Override
    public void changeHeight(int height) {
        size.height = height;
        setSize(size);
    }

    @Override
    public void changeSize(Dimension dimension) {
        size = dimension;
        setSize(size);
    }

    public static void main(String[] args) {

        JPanel panel = new JPanel(null);
        EditableRectangle rect = new EditableRectangle(new Point(12, 13), new Dimension(80, 30), true);
        panel.add(rect);

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
