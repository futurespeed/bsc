package org.fs.bsc.exec;

import org.fs.bsc.component.BscComponent;
import org.fs.bsc.exception.BscException;
import org.fs.bsc.loader.BscComponentLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultBscComponentProvider implements BscComponentProvider {

    private Map<String, BscComponent> componentMap;

    private List<BscComponentLoader> componentLoaderList;

    public DefaultBscComponentProvider() {
        componentMap = new HashMap<String, BscComponent>();
        componentLoaderList = new ArrayList<BscComponentLoader>();
    }

    public void loadComponents() {
        for (BscComponentLoader loader : componentLoaderList) {
            loader.loadComponents(this);
        }
    }

    public void addComponentLoader(BscComponentLoader loader) {
        componentLoaderList.add(loader);
    }

    public BscComponent getComponent(String componentCode) {
        return componentMap.get(componentCode);
    }

    public synchronized void addComponent(String code, BscComponent component) {
        if (componentMap.containsKey(code)) {
            throw new BscException("Fail to add component, component [" + code + "] already exist !");
        }
        componentMap.put(code, component);
    }
}
