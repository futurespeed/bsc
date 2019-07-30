package org.fs.bsc.context;

public class DefaultBscContextManager implements BscContextManager {
    public BscContext newContext() {
        return new SimpleBscContext();
    }
}
