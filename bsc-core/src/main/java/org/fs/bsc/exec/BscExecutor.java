package org.fs.bsc.exec;

import org.fs.bsc.component.BscComponent;
import org.fs.bsc.context.BscContext;

public interface BscExecutor {
    String execute(BscComponent component, BscContext context);
}
