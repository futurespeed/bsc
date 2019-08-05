package org.fs.bsc.flow.editor.ui;

import org.fs.bsc.flow.editor.model.BscFlow;
import org.fs.bsc.flow.editor.model.BscFlowAction;
import org.fs.bsc.flow.editor.model.DisplayInfo;
import org.fs.bsc.flow.editor.ui.support.RectangleMovingShadow;

import javax.swing.*;
import java.awt.*;

public class BscFlowDesignPanel extends JPanel {

    private BscFlow flow;

    private RectangleMovingShadow movingShadow;

    public BscFlowDesignPanel() {
        setLayout(null);
    }

    public void refresh() {
        removeAll();

        DisplayInfo defaultDisplayInfo = new DisplayInfo();
        defaultDisplayInfo.setHeight(80);
        defaultDisplayInfo.setWidth(30);

        movingShadow = new RectangleMovingShadow();
        add(movingShadow.getPanel());

        if (flow.getStartAction() != null) {
            BscFlowAction action = flow.getStartAction();
            if (null == action.getDisplay()) {
                action.setDisplay(defaultDisplayInfo);
            }
            BscFlowActionPart actionPart = new BscFlowActionPart("Start",
                    new Point(action.getDisplay().getX(), action.getDisplay().getY()),
                    new Dimension(action.getDisplay().getWidth(), action.getDisplay().getHeight()));
            add(actionPart);
        }

        for (BscFlowAction action : flow.getActions()) {
            if (null == action.getDisplay()) {
                action.setDisplay(defaultDisplayInfo);
            }
            BscFlowActionPart actionPart = new BscFlowActionPart(action.getName(),
                    new Point(action.getDisplay().getX(), action.getDisplay().getY()),
                    new Dimension(action.getDisplay().getWidth(), action.getDisplay().getHeight()));
            add(actionPart);
        }

        for (BscFlowAction action : flow.getEndActions()) {
            if (null == action.getDisplay()) {
                action.setDisplay(defaultDisplayInfo);
            }
            BscFlowActionPart actionPart = new BscFlowActionPart("End",
                    new Point(action.getDisplay().getX(), action.getDisplay().getY()),
                    new Dimension(action.getDisplay().getWidth(), action.getDisplay().getHeight()));
            add(actionPart);
        }
    }

    public BscFlow getFlow() {
        return flow;
    }

    public void setFlow(BscFlow flow) {
        this.flow = flow;
    }

    public RectangleMovingShadow getMovingShadow() {
        return movingShadow;
    }
}
