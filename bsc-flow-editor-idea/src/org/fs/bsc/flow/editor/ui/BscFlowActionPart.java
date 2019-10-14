package org.fs.bsc.flow.editor.ui;

import com.intellij.openapi.ui.Messages;
import org.fs.bsc.flow.editor.command.AddConnectionCommand;
import org.fs.bsc.flow.editor.command.Command;
import org.fs.bsc.flow.editor.model.BscComponent;
import org.fs.bsc.flow.editor.model.BscFlowAction;
import org.fs.bsc.flow.editor.model.BscFlowEndAction;
import org.fs.bsc.flow.editor.model.BscFlowStartAction;
import org.fs.bsc.flow.editor.ui.support.Connector;
import org.fs.bsc.flow.editor.ui.support.EditableRectangle;
import org.fs.bsc.flow.editor.ui.support.RectangleMovingShadow;
import org.fs.bsc.flow.editor.ui.support.RectangleUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BscFlowActionPart extends EditableRectangle {

    private BscFlowEditorUI ui;

    private BscFlowAction action;

    private JLabel nameLabel;

    private java.util.List<Connector> sourceConnectors;

    private java.util.List<Connector> targetConnectors;

    public BscFlowActionPart(BscFlowEditorUI ui, String name, Point position, Dimension size) {
        super(position, size, false);
        this.ui = ui;
        nameLabel = new JLabel(name);
        add(nameLabel);
        setBackground(Color.YELLOW);
        sourceConnectors = new ArrayList<>();
        targetConnectors = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        String actionName;
        if (action instanceof BscFlowStartAction) {
            actionName = "Start";
        } else if (action instanceof BscFlowEndAction) {
            actionName = "End";
        } else {
            actionName = action.getName();
        }
        nameLabel.setText(actionName);
        super.paintComponent(g);
    }

    @Override
    protected void mouseClicked() {
        if (getParent() instanceof BscFlowDesignPanel) {
            BscFlowDesignPanel designPanel = (BscFlowDesignPanel) getParent();
            // selection
            designPanel.getUi().getSelectionManager().selectOne(this);

            // connection command
            Command command = designPanel.getUi().getCommandManager().getCurrentCommand();
            if (command != null) {
                if (command instanceof AddConnectionCommand) {
                    AddConnectionCommand addConnectionCommand = (AddConnectionCommand) command;
                    if (null == addConnectionCommand.getSourceAction()) {
                        addConnectionCommand.setSourceAction(getAction());
                    } else if (null == addConnectionCommand.getTargetAction()) {
                        addConnectionCommand.setTargetAction(getAction());
                        designPanel.getUi().getCommandManager().execute(addConnectionCommand);
                        designPanel.refresh();
                        designPanel.getUi().save();
                    }
                }
            }
        }
    }

    @Override
    protected void mouseDoubleClicked() {
        if (this.getAction() instanceof BscFlowStartAction ||
                this.getAction() instanceof BscFlowEndAction) {
            return;
        }
        BscComponent bscComponent = ui.getBscComponent(action.getComponentCode());
        if (null == bscComponent) {
            Messages.showErrorDialog("Can not find component [" + action.getComponentCode() + "]", "Error");
            return;
        }
        ActionParamDialog actionParamDialog = new ActionParamDialog(ui, bscComponent, action);
        actionParamDialog.init();
        actionParamDialog.setVisible(true);
    }

    @Override
    public void drawStart(Point point) {
        super.drawStart(point);
        RectangleMovingShadow movingShadow = ui.getDesignPanel().getMovingShadow();
        movingShadow.setSize(getSize());
        movingShadow.setLocation(getPosition());
        movingShadow.show();
        setVisible(false);
    }

    @Override
    public void drawStop(Point point) {
        super.drawStop(point);

        action.getDisplay().setX(getPosition().x);
        action.getDisplay().setY(getPosition().y);

        RectangleMovingShadow movingShadow = ui.getDesignPanel().getMovingShadow();
        movingShadow.hide();
        setVisible(true);

        ui.save();


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
        RectangleMovingShadow movingShadow = ui.getDesignPanel().getMovingShadow();
        movingShadow.setLocation(getNewPosition(), false);
        movingShadow.go();

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
