package org.fs.bsc.flow.editor.command;

import org.fs.bsc.flow.editor.model.BscFlow;
import org.fs.bsc.flow.editor.model.BscFlowAction;
import org.fs.bsc.flow.editor.model.BscFlowEndAction;
import org.fs.bsc.flow.editor.model.BscFlowStartAction;

import java.util.UUID;

public class AddActionCommand implements Command {
    protected BscFlowAction action;

    protected BscFlow flow;

    @Override
    public void execute() {
        String prefix = action.getComponentCode();
        action.setCode(prefix + "." + UUID.randomUUID().toString());
        if(action instanceof BscFlowStartAction){
            flow.setStartAction((BscFlowStartAction) action);
        }else if(action instanceof BscFlowEndAction){
            flow.getEndActions().add((BscFlowEndAction) action);
        }else {
            flow.getActions().add(action);
        }
    }

    @Override
    public void undo() {
        if(action instanceof BscFlowStartAction){
            flow.setStartAction(null);
        }else if(action instanceof BscFlowEndAction){
            flow.getEndActions().remove(action);
        }else {
            flow.getActions().remove(action);
        }
    }

    public BscFlowAction getAction() {
        return action;
    }

    public void setAction(BscFlowAction action) {
        this.action = action;
    }

    public BscFlow getFlow() {
        return flow;
    }

    public void setFlow(BscFlow flow) {
        this.flow = flow;
    }
}
