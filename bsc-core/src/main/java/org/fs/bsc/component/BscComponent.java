package org.fs.bsc.component;

import org.fs.bsc.context.BscContext;
import org.fs.bsc.def.BscFieldDef;

import java.util.List;

public interface BscComponent {
    String getCode();

    void setCode(String code);

    String getName();

    String getDesc();

    List<BscFieldDef> getInputFields();

    List<BscFieldDef> getOutputFields();

    String execute(BscContext context);
}
