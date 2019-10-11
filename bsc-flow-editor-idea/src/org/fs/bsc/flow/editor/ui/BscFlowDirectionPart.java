package org.fs.bsc.flow.editor.ui;

import org.fs.bsc.flow.editor.model.BscFlowDirection;
import org.fs.bsc.flow.editor.ui.support.Connector;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BscFlowDirectionPart extends Connector {

    public BscFlowDirectionPart() {
        init();
    }

    protected void init(){
        BscFlowDirectionPart that = this;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getParent() instanceof BscFlowDesignPanel) {
                    BscFlowDesignPanel designPanel = (BscFlowDesignPanel) getParent();
                    designPanel.getUi().getSelectionManager().selectOne(that);
                }
            }
        });
    }

    private BscFlowDirection direction;

    public BscFlowDirection getDirection() {
        return direction;
    }

    public void setDirection(BscFlowDirection direction) {
        this.direction = direction;
    }
}
