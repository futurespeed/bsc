package org.fs.bsc.flow.editor.ui;

import org.fs.bsc.flow.editor.ui.support.EditableRectangle;

import javax.swing.*;
import java.awt.*;

public class BscFlowActionPart extends EditableRectangle {

    private JLabel nameLabel;

    public BscFlowActionPart(String name, Point position, Dimension size){
        super(position, size, false);
        nameLabel = new JLabel(name);
        add(nameLabel);
        setBackground(Color.YELLOW);
    }
}
