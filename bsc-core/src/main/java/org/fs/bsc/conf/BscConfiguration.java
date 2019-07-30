package org.fs.bsc.conf;

import org.fs.bsc.BscServiceProvider;
import org.fs.bsc.context.BscContextManager;
import org.fs.bsc.event.BscEventManager;
import org.fs.bsc.exec.BscExecuteManager;

public interface BscConfiguration {
    BscExecuteManager getBscExecuteManager();

    BscContextManager getBscContextManager();

    BscEventManager getBscEventManager();

    void configure(BscServiceProvider provider);
}
