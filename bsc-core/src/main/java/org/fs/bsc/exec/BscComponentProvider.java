package org.fs.bsc.exec;

import org.fs.bsc.component.BscComponent;

public interface BscComponentProvider {
    BscComponent getComponent(String componentCode);

    void addComponent(String code, BscComponent component);
}
