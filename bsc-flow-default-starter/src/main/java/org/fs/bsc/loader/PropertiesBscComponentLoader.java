package org.fs.bsc.loader;

import org.fs.bsc.component.BscComponent;
import org.fs.bsc.exception.BscException;
import org.fs.bsc.exec.BscComponentProvider;

import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesBscComponentLoader implements BscComponentLoader {

    public void loadComponents(BscComponentProvider provider) {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/bsc_components.properties"));
            for (Entry<Object, Object> entry : prop.entrySet()) {
                String code = entry.getKey().toString();
                String cls = entry.getValue().toString();
                Object inst = Class.forName(cls).newInstance();
                if (inst instanceof BscComponent) {
                    BscComponent component = (BscComponent) inst;
                    component.setCode(code);
                    provider.addComponent(code, component);
                } else {
                    throw new BscException("Class [" + cls + "] is not extended of BscComponent !");
                }
            }
        } catch (Exception e) {
            throw new BscException(e);
        }
    }

}
