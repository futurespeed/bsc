package org.fs.bsc.flow.editor.ui;

import org.fs.bsc.flow.editor.model.BscFlowAction;
import org.fs.bsc.flow.editor.ui.support.Connector;
import org.fs.bsc.flow.editor.ui.support.EditableRectangle;
import org.fs.bsc.flow.editor.ui.support.RectangleMovingShadow;
import org.fs.bsc.flow.editor.ui.support.RectangleUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BscFlowActionPart extends EditableRectangle {

    private BscFlowAction action;

    private JLabel nameLabel;

    private java.util.List<Connector> sourceConnectors;

    private java.util.List<Connector> targetConnectors;

    public BscFlowActionPart(String name, Point position, Dimension size) {
        super(position, size, false);
        nameLabel = new JLabel(name);
        add(nameLabel);
        setBackground(Color.YELLOW);
        sourceConnectors = new ArrayList<>();
        targetConnectors = new ArrayList<>();
    }

    @Override
    public void drawStart(Point point) {
        super.drawStart(point);
        if (getParent() instanceof BscFlowDesignPanel) {
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

        action.getDisplay().setX(getPosition().x);
        action.getDisplay().setY(getPosition().y);

        if (getParent() instanceof BscFlowDesignPanel) {
            BscFlowDesignPanel designPanel = (BscFlowDesignPanel) getParent();
            RectangleMovingShadow movingShadow = designPanel.getMovingShadow();
            movingShadow.hide();
            setVisible(true);

            //FIXME
            BscFlowEditorUI ui = (BscFlowEditorUI) getParent().getParent().getParent().getParent().getParent();
            ui.save();
        }


        for (Connector connector : sourceConnectors) {
            connector.connect(connector.getStart(),
                    RectangleUtils.getConnectPoint(getPosition(), getSize(), connector.getStart()));
        }
        for (Connector connector : targetConnectors) {
            connector.connect(RectangleUtils.getMiddlePoint(getPosition(), getSize()),
                    connector.getEnd());
        }
    }

    @Override
    public void drawMove(Point point) {
        super.drawMove(point);
        if (getParent() instanceof BscFlowDesignPanel) {
            RectangleMovingShadow movingShadow = ((BscFlowDesignPanel) getParent()).getMovingShadow();
            movingShadow.setLocation(getNewPosition(), false);
            movingShadow.go();
        }
        for (Connector connector : sourceConnectors) {
            connector.connect(connector.getStart(),
                    RectangleUtils.getConnectPoint(getNewPosition(), getSize(), connector.getStart()));
        }
        for (Connector connector : targetConnectors) {
            connector.connect(RectangleUtils.getMiddlePoint(getNewPosition(), getSize()),
                    connector.getEnd());
        }
    }

    public void setAction(BscFlowAction action) {
        this.action = action;
    }

    public BscFlowAction getAction() {
        return action;
    }

    public void addSourceConnector(Connector connector) {
        sourceConnectors.add(connector);
    }

    public void addTargetConnector(Connector connector) {
        targetConnectors.add(connector);
    }
}
