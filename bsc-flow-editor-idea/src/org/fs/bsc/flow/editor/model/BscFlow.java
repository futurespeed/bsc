package org.fs.bsc.flow.editor.model;

import java.util.List;

public class BscFlow {
	private String code;
	private String name;
	private String desc;
	private BscFlowStartAction startAction;
	private List<BscFlowAction> actions;
	private List<BscFlowEndAction> endActions;
	private List<BscField> inputFields;
	private List<BscField> outputFields;
	private DisplayInfo display;

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
	public BscFlowAction getStartAction() {
		return startAction;
	}
	public void setStartAction(BscFlowStartAction startAction) {
		this.startAction = startAction;
	}
	public List<BscFlowAction> getActions() {
		return actions;
	}
	public void setActions(List<BscFlowAction> actions) {
		this.actions = actions;
	}
	public List<BscFlowEndAction> getEndActions() {
		return endActions;
	}
	public void setEndActions(List<BscFlowEndAction> endActions) {
		this.endActions = endActions;
	}
	public List<BscField> getInputFields() {
		return inputFields;
	}
	public void setInputFields(List<BscField> inputFields) {
		this.inputFields = inputFields;
	}
	public List<BscField> getOutputFields() {
		return outputFields;
	}
	public void setOutputFields(List<BscField> outputFields) {
		this.outputFields = outputFields;
	}
	public DisplayInfo getDisplay() {
		return display;
	}

	public void setDisplay(DisplayInfo display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + getCode() + "]";
	}
}
