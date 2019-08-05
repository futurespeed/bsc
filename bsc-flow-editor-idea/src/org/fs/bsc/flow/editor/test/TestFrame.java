package org.fs.bsc.flow.editor.test;

import org.fs.bsc.flow.editor.ui.support.Connector;
import org.fs.bsc.flow.editor.ui.support.EditableRectangle;

import javax.swing.*;
import java.awt.*;

public class TestFrame {
    public static void main(String[] args) {

        JPanel panel = new JPanel(null);
        EditableRectangle rect = new EditableRectangle(new Point(12, 13), new Dimension(80, 30), true);
        panel.add(rect);

        Connector connector = new Connector();
        panel.add(connector);
        connector.connect(new Point(10, 10), new Point(110, 210));

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
