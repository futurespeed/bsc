package org.fs.bsc.flow.editor.ui;

import org.fs.bsc.flow.editor.model.BscFlow;
import org.fs.bsc.flow.editor.model.BscFlowAction;
import org.fs.bsc.flow.editor.model.BscFlowDirection;
import org.fs.bsc.flow.editor.model.DisplayInfo;
import org.fs.bsc.flow.editor.ui.support.Connector;
import org.fs.bsc.flow.editor.ui.support.RectangleMovingShadow;
import org.fs.bsc.flow.editor.ui.support.RectangleUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BscFlowDesignPanel extends JPanel {

    private BscFlow flow;

    private RectangleMovingShadow movingShadow;

    private Map<String, BscFlowActionPart> actionPartMap = new HashMap<>();

    public BscFlowDesignPanel() {
        setLayout(null);
    }

    public void refresh() {
        actionPartMap.clear();
        removeAll();

        DisplayInfo defaultDisplayInfo = new DisplayInfo();
        defaultDisplayInfo.setHeight(80);
        defaultDisplayInfo.setWidth(30);

        movingShadow = new RectangleMovingShadow();
        add(movingShadow.getPanel());

        // start action
        if (flow.getStartAction() != null) {
            BscFlowAction action = flow.getStartAction();
            if (null == action.getDisplay()) {
                action.setDisplay(defaultDisplayInfo);
            }
            BscFlowActionPart actionPart = new BscFlowActionPart("Start",
                    new Point(action.getDisplay().getX(), action.getDisplay().getY()),
                    new Dimension(action.getDisplay().getWidth(), action.getDisplay().getHeight()));
            actionPart.setAction(action);
            add(actionPart);
            actionPartMap.put(action.getCode(), actionPart);
        }

        // end actions
        for (BscFlowAction action : flow.getActions()) {
            if (null == action.getDisplay()) {
                action.setDisplay(defaultDisplayInfo);
            }
            BscFlowActionPart actionPart = new BscFlowActionPart(action.getName(),
                    new Point(action.getDisplay().getX(), action.getDisplay().getY()),
                    new Dimension(action.getDisplay().getWidth(), action.getDisplay().getHeight()));
            actionPart.setAction(action);
            add(actionPart);
            actionPartMap.put(action.getCode(), actionPart);
        }

        // actions
        for (BscFlowAction action : flow.getEndActions()) {
            if (null == action.getDisplay()) {
                action.setDisplay(defaultDisplayInfo);
            }
            BscFlowActionPart actionPart = new BscFlowActionPart("End",
                    new Point(action.getDisplay().getX(), action.getDisplay().getY()),
                    new Dimension(action.getDisplay().getWidth(), action.getDisplay().getHeight()));
            actionPart.setAction(action);
            add(actionPart);
            actionPartMap.put(action.getCode(), actionPart);
        }

        // add connection
        for (Map.Entry<String, BscFlowActionPart> entry : actionPartMap.entrySet()) {
            BscFlowActionPart part = entry.getValue();
            java.util.List<BscFlowDirection> directionList = part.getAction().getDirections();
            if (directionList != null && !directionList.isEmpty()) {
                for (BscFlowDirection direction : directionList) {
                    BscFlowAction targetAction = direction.getTargetAction();
                    BscFlowActionPart targetPart = actionPartMap.get(targetAction.getCode());
                    DisplayInfo targetActionDisplay = targetAction.getDisplay();

                    Point to = new Point(targetActionDisplay.getX(), targetActionDisplay.getY());
                    Connector connector = new Connector();
//                    connector.connect(part.getPosition(), to);
//                    connector.connect(RectangleUtils.getConnectPoint(part.getPosition(), part.getSize(), to),
//                            RectangleUtils.getConnectPoint(targetPart.getPosition(), targetPart.getSize(), part.getPosition()));
                    connector.connect(RectangleUtils.getMiddlePoint(part.getPosition(), part.getSize()),
                            RectangleUtils.getConnectPoint(targetPart.getPosition(), targetPart.getSize(), part.getPosition()));
                    add(connector);
                    part.addTargetConnector(connector);
                    targetPart.addSourceConnector(connector);
                }
            }
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
