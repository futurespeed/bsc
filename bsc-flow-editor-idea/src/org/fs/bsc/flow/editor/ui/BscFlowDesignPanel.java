package org.fs.bsc.flow.editor.ui;

import org.fs.bsc.flow.editor.command.AddActionCommand;
import org.fs.bsc.flow.editor.command.Command;
import org.fs.bsc.flow.editor.model.BscFlow;
import org.fs.bsc.flow.editor.model.BscFlowAction;
import org.fs.bsc.flow.editor.model.BscFlowDirection;
import org.fs.bsc.flow.editor.model.DisplayInfo;
import org.fs.bsc.flow.editor.ui.support.Connector;
import org.fs.bsc.flow.editor.ui.support.RectangleMovingShadow;
import org.fs.bsc.flow.editor.ui.support.RectangleUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class BscFlowDesignPanel extends JPanel {

    private BscFlow flow;

    private RectangleMovingShadow movingShadow;

    private Map<String, BscFlowActionPart> actionPartMap = new HashMap<>();

    private BscFlowEditorUI ui;

    public BscFlowDesignPanel(BscFlowEditorUI ui) {
        this.ui = ui;
        setLayout(null);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ui.getSelectionManager().cleanSelection();
                Command command = ui.getCommandManager().getCurrentCommand();
                if (null == command) {
                    return;
                }
                if (command instanceof AddActionCommand) {
                    AddActionCommand addActionCommand = (AddActionCommand) command;
                    DisplayInfo displayInfo = new DisplayInfo();
                    displayInfo.setWidth(80);
                    displayInfo.setHeight(30);
                    Point mousePos = MouseInfo.getPointerInfo().getLocation();
                    Point panelPos = getLocationOnScreen();
                    displayInfo.setX(mousePos.x - panelPos.x);
                    displayInfo.setY(mousePos.y - panelPos.y);
                    addActionCommand.getAction().setDisplay(displayInfo);
                    ui.getCommandManager().execute(command);
                    refresh();
                    ui.save();
                }
            }
        });
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
            BscFlowActionPart actionPart = new BscFlowActionPart(ui, "Start",
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
            BscFlowActionPart actionPart = new BscFlowActionPart(ui, action.getName(),
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
            BscFlowActionPart actionPart = new BscFlowActionPart(ui, "End",
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
                    BscFlowDirectionPart connector = new BscFlowDirectionPart();
                    connector.setDirection(direction);
                    add(connector);
                    connector.connect(RectangleUtils.getMiddlePoint(part.getPosition(), part.getSize()),
                            RectangleUtils.getConnectPoint(targetPart.getPosition(), targetPart.getSize(), part.getPosition()));
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

    public BscFlowEditorUI getUi() {
        return ui;
    }
}
