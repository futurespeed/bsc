package org.fs.bsc.exec;

import org.fs.bsc.component.BscComponent;
import org.fs.bsc.context.BscContext;

public interface BscExecuteManager {
    String execute(String componentCode, BscContext context);

    BscComponent getComponent(String componentCode);

    boolean checkCondition(String expression, BscContext context);
}
