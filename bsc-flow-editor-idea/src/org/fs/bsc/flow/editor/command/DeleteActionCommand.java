package org.fs.bsc.flow.editor.command;

import org.fs.bsc.flow.editor.model.*;

import java.util.List;

public class DeleteActionCommand implements Command {

    private BscFlowAction action;

    private BscFlow flow;

    private List<BscFlowDirection> sourceDirections;

    @Override
    public void execute() {
        if (action instanceof BscFlowStartAction) {
            flow.setStartAction(null);
        } else if (action instanceof BscFlowEndAction) {
            sourceDirections = action.getSourceDirections();
            if (sourceDirections != null) {
                for (BscFlowDirection direction : sourceDirections) {
                    direction.getSourceAction().getDirections().remove(direction);
                }
            }
            flow.getEndActions().remove(action);
        } else {
            sourceDirections = action.getSourceDirections();
            if (sourceDirections != null) {
                for (BscFlowDirection direction : sourceDirections) {
                    direction.getSourceAction().getDirections().remove(direction);
                }
            }
            flow.getActions().remove(action);
        }
    }

    @Override
    public void undo() {
        if (action instanceof BscFlowStartAction) {
            flow.setStartAction((BscFlowStartAction) action);
        } else if (action instanceof BscFlowEndAction) {
            flow.getEndActions().add((BscFlowEndAction) action);
            if (sourceDirections != null) {
                for (BscFlowDirection direction : sourceDirections) {
                    direction.getSourceAction().getDirections().add(direction);
                }
            }
        } else {
            flow.getActions().add(action);
            if (sourceDirections != null) {
                for (BscFlowDirection direction : sourceDirections) {
                    direction.getSourceAction().getDirections().add(direction);
                }
            }
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
