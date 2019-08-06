package org.fs.bsc.flow.editor.ui;

import org.fs.bsc.flow.editor.model.BscFlowAction;
import org.fs.bsc.flow.editor.model.BscFlowDirection;
import org.fs.bsc.flow.editor.model.DisplayInfo;
import org.fs.bsc.flow.editor.ui.support.Connector;
import org.fs.bsc.flow.editor.ui.support.EditableRectangle;
import org.fs.bsc.flow.editor.ui.support.RectangleMovingShadow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BscFlowActionPart extends EditableRectangle {

    private BscFlowAction action;

    private JLabel nameLabel;

    private java.util.List<Connector> connectors;

    public BscFlowActionPart(String name, Point position, Dimension size){
        super(position, size, false);
        nameLabel = new JLabel(name);
        add(nameLabel);
        setBackground(Color.YELLOW);
        connectors = new ArrayList<>();
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
        action.getDisplay().setX(getPosition().x);
        action.getDisplay().setY(getPosition().y);
        for(Connector connector : connectors){
            connector.connect(getPosition(), connector.getEnd());
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
        for(Connector connector : connectors){
            connector.connect(getNewPosition(), connector.getEnd());
        }
    }

    public void setAction(BscFlowAction action) {
        this.action = action;
    }

    public BscFlowAction getAction() {
        return action;
    }

    public void addDirection(BscFlowDirection direction){
        Connector connector = new Connector();
        DisplayInfo targetActionDisplay = direction.getTargetAction().getDisplay();
        Point to = new Point(targetActionDisplay.getX(), targetActionDisplay.getY());
        connector.connect(getPosition(), to);
        connectors.add(connector);
        getParent().add(connector);
    }
}
