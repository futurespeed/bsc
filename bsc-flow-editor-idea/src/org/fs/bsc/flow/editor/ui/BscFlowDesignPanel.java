package org.fs.bsc.flow.editor.ui;

import org.fs.bsc.flow.editor.model.BscFlow;
import org.fs.bsc.flow.editor.model.BscFlowAction;
import org.fs.bsc.flow.editor.model.DisplayInfo;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class BscFlowDesignPanel extends JPanel {

    private BscFlow flow;

    public BscFlowDesignPanel(){
        setLayout(null);
    }

    public void refresh(){
        removeAll();

        DisplayInfo defaultDisplayInfo = new DisplayInfo();

        if(flow.getStartAction() != null) {
            BscFlowAction action = flow.getStartAction();
            if(null == action.getDisplay()){
                action.setDisplay(defaultDisplayInfo);
            }
            JLabel label = new JLabel("Start");
            label.setBorder(LineBorder.createGrayLineBorder());
            label.setLocation(action.getDisplay().getX(), action.getDisplay().getY());
            label.setSize(action.getDisplay().getWidth(), action.getDisplay().getHeight());
            add(label);
        }

        for(BscFlowAction action : flow.getActions()){
            if(null == action.getDisplay()){
                action.setDisplay(defaultDisplayInfo);
            }
            JLabel label = new JLabel(action.getName());
            label.setBorder(LineBorder.createGrayLineBorder());
            label.setLocation(action.getDisplay().getX(), action.getDisplay().getY());
            label.setSize(action.getDisplay().getWidth(), action.getDisplay().getHeight());
            add(label);
        }

        for(BscFlowAction action : flow.getEndActions()){
            if(null == action.getDisplay()){
                action.setDisplay(defaultDisplayInfo);
            }
            JLabel label = new JLabel("End");
            label.setBorder(LineBorder.createGrayLineBorder());
            label.setLocation(action.getDisplay().getX(), action.getDisplay().getY());
            label.setSize(action.getDisplay().getWidth(), action.getDisplay().getHeight());
            add(label);
        }
    }

    public BscFlow getFlow() {
        return flow;
    }

    public void setFlow(BscFlow flow) {
        this.flow = flow;
    }
}
