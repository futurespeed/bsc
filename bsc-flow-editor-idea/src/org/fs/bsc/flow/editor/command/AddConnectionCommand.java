package org.fs.bsc.flow.editor.command;

import org.fs.bsc.flow.editor.model.BscFlow;
import org.fs.bsc.flow.editor.model.BscFlowAction;
import org.fs.bsc.flow.editor.model.BscFlowDirection;

import java.util.ArrayList;

public class AddConnectionCommand implements Command {

    private BscFlowAction sourceAction;

    private BscFlowAction targetAction;

    private BscFlowDirection direction;

    private BscFlow flow;

    @Override
    public void execute() {
        direction = new BscFlowDirection();
        direction.setSourceAction(sourceAction);
        direction.setTargetAction(targetAction);
        direction.setActionCode(targetAction.getCode());
        if(null == sourceAction.getDirections()){
            sourceAction.setDirections(new ArrayList<>());
        }
        sourceAction.getDirections().add(direction);
    }

    @Override
    public void undo() {
        sourceAction.getDirections().remove(direction);
    }

    public BscFlowAction getSourceAction() {
        return sourceAction;
    }

    public void setSourceAction(BscFlowAction sourceAction) {
        this.sourceAction = sourceAction;
    }

    public BscFlowAction getTargetAction() {
        return targetAction;
    }

    public void setTargetAction(BscFlowAction targetAction) {
        this.targetAction = targetAction;
    }

    public BscFlowDirection getDirection() {
        return direction;
    }

    public void setDirection(BscFlowDirection direction) {
        this.direction = direction;
    }

    public BscFlow getFlow() {
        return flow;
    }

    public void setFlow(BscFlow flow) {
        this.flow = flow;
    }
}
