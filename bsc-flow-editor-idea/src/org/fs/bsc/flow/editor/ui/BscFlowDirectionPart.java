package org.fs.bsc.flow.editor.ui;

import org.fs.bsc.flow.editor.model.BscFlowDirection;
import org.fs.bsc.flow.editor.ui.support.Connector;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BscFlowDirectionPart extends Connector {

    private BscFlowEditorUI ui;

    private BscFlowDirection direction;

    private long lastClickTimestamp;

    public BscFlowDirectionPart(BscFlowEditorUI ui, BscFlowDirection direction) {
        this.ui = ui;
        this.direction = direction;
        init();
    }

    protected void init() {
        BscFlowDirectionPart that = this;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                long currTimeStamp = System.currentTimeMillis();
                if(currTimeStamp - lastClickTimestamp < 500){
                    // double click
                    DirectionParamDialog directionParamDialog = new DirectionParamDialog(ui, direction, that);
                    directionParamDialog.init();
                    directionParamDialog.setVisible(true);
                }else {
                    // click
                    ui.getSelectionManager().selectOne(that);
                }
                lastClickTimestamp = currTimeStamp;
            }
        });

        if (direction != null) {
            setDescription(direction.getDesc());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (direction != null) {
            setDescription(direction.getDesc());
        }
        super.paintComponent(g);
    }

    public BscFlowDirection getDirection() {
        return direction;
    }

    public void setDirection(BscFlowDirection direction) {
        this.direction = direction;
    }
}
