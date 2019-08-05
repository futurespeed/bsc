package org.fs.bsc.flow.editor.ui;

import org.fs.bsc.flow.editor.ui.support.EditableRectangle;
import org.fs.bsc.flow.editor.ui.support.RectangleMovingShadow;

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

    @Override
    public void drawStart(Point point) {
        super.drawStart(point);
        if(getParent() instanceof BscFlowDesignPanel){
            RectangleMovingShadow movingShadow = ((BscFlowDesignPanel) getParent()).getMovingShadow();
            movingShadow.setSize(getSize());
            movingShadow.setLocation(getPosition());
            movingShadow.show();
            setVisible(false);
        }
    }

    @Override
    public void drawStop(Point point) {
        super.drawStop(point);
        if(getParent() instanceof BscFlowDesignPanel){
            RectangleMovingShadow movingShadow = ((BscFlowDesignPanel) getParent()).getMovingShadow();
            movingShadow.hide();
            setVisible(true);
        }
    }

    @Override
    public void drawMove(Point point) {
        super.drawMove(point);
        if(getParent() instanceof BscFlowDesignPanel){
            RectangleMovingShadow movingShadow = ((BscFlowDesignPanel) getParent()).getMovingShadow();
            movingShadow.setLocation(getNewPosition(), false);
            movingShadow.go();
        }
    }
}
