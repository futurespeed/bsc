package org.fs.bsc.flow.editor.model;

import java.util.List;
import java.util.Map;

public class BscFlowAction {
	public static final String RESULT_SUCCESS = "success"; 
	public static final String RESULT_FAIL = "fail";
	public static final String RESULT_EXCEPTION = "exception";
	public static final String CONTEXT_PARAM_NAME = "action_params";
	private String code;
	private String name;
	private String desc;
	private String componentCode;
	private List<BscFlowDirection> directions;
	private List<BscFieldRel> inputs;
	private List<BscFieldRel> outputs;
	private Map<String, Object> params;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getComponentCode() {
		return componentCode;
	}
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
	public List<BscFlowDirection> getDirections() {
		return directions;
	}
	public void setDirections(List<BscFlowDirection> directions) {
		this.directions = directions;
	}
	public List<BscFieldRel> getInputs() {
		return inputs;
	}
	public void setInputs(List<BscFieldRel> inputs) {
		this.inputs = inputs;
	}
	public List<BscFieldRel> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<BscFieldRel> outputs) {
		this.outputs = outputs;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + getCode() + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BscFlowAction){
			String code2 = ((BscFlowAction) obj).getCode();
			if(code != null){
				return code.equals(code2);
			}
			return super.equals(obj);
		}
		return false;
	}
}
