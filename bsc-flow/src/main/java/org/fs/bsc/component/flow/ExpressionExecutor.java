package org.fs.bsc.component.flow;

import org.fs.bsc.context.BscContext;

public interface ExpressionExecutor {

    boolean execute(String expression, BscContext context);
}
