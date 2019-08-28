package org.fs.bsc.flow.editor.test;

import com.intellij.ui.components.JBScrollPane;
import org.fs.bsc.flow.editor.ui.support.Connector;
import org.fs.bsc.flow.editor.ui.support.EditableRectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestFrame {
    public static void main(String[] args) {

        JPanel panel = new JPanel(null);


        final Connector connector = new Connector();
        panel.add(connector);
        connector.connect(new Point(0, 0), new Point(10, 10));

        EditableRectangle rect = new EditableRectangle(new Point(12, 13), new Dimension(80, 30), true);
        rect.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                connector.connect(new Point(200, 200), new Point(e.getX(), e.getY()));
            }
        });
        panel.add(rect);

        JScrollPane scrollPane = new JBScrollPane(new JLabel("sdfdsf"));

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.getContentPane().add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
