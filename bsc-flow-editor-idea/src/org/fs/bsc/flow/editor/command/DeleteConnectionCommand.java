package org.fs.bsc.flow.editor.command;

import org.fs.bsc.flow.editor.model.BscFlowDirection;

public class DeleteConnectionCommand implements Command {

    private BscFlowDirection direction;

    @Override
    public void execute() {
        direction.getTargetAction().getSourceDirections().remove(direction);
        direction.getSourceAction().getDirections().remove(direction);
    }

    @Override
    public void undo() {
        direction.getTargetAction().getSourceDirections().add(direction);
        direction.getSourceAction().getDirections().add(direction);
    }

    public BscFlowDirection getDirection() {
        return direction;
    }

    public void setDirection(BscFlowDirection direction) {
        this.direction = direction;
    }
}
