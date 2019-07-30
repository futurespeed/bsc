package org.fs.bsc.context;

import java.util.Map;

public interface BscContext extends Map<String, Object> {
    void setExtParam(String key, Object value);

    Object getExtParam(String key);
}
