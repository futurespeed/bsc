package org.fs.bsc.event;

public class DefaultBscEventManager implements BscEventManager {
    public void publishEvent(BscEvent event) {
        System.out.println("[" + Thread.currentThread() + "]" + event);
    }
}
