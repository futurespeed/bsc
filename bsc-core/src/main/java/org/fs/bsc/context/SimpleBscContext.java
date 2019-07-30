package org.fs.bsc.context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleBscContext extends LinkedHashMap<String, Object> implements BscContext {
    private Map<String, Object> extParams = new HashMap<String, Object>();

    public void setExtParam(String key, Object value) {
        extParams.put(key, value);
    }

    public Object getExtParam(String key) {
        return extParams.get(key);
    }

}
