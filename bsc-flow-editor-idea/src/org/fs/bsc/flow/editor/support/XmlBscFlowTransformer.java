package org.fs.bsc.flow.editor.support;

import org.fs.bsc.flow.editor.model.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlBscFlowTransformer {
	
	public static void toXML(BscFlow flowDef, OutputStream out){
		try{
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);
			Element root = doc.createElement("flow");
			doc.appendChild(root);
			root.setAttribute("code", flowDef.getCode());
			root.setAttribute("name", flowDef.getName());
			root.setAttribute("desc", flowDef.getDesc());
			
			setDisplay4Doc(doc, root, flowDef.getDisplay());
			
			if(flowDef.getInputFields() != null){
				Element inputFieldsElement = doc.createElement("inputFields");
				root.appendChild(inputFieldsElement);
				for(BscField field : flowDef.getInputFields()){
					Element fieldElement = doc.createElement("field");
					inputFieldsElement.appendChild(fieldElement);
					fieldElement.setAttribute("code", field.getCode());
					fieldElement.setAttribute("name", field.getName());
					fieldElement.setAttribute("desc", field.getDesc());
					fieldElement.setAttribute("format", field.getFormat());
				}
			}
			
			if(flowDef.getOutputFields() != null){
				Element outputFieldsElement = doc.createElement("outputFields");
				root.appendChild(outputFieldsElement);
				for(BscField field : flowDef.getOutputFields()){
					Element fieldElement = doc.createElement("field");
					outputFieldsElement.appendChild(fieldElement);
					fieldElement.setAttribute("code", field.getCode());
					fieldElement.setAttribute("name", field.getName());
					fieldElement.setAttribute("desc", field.getDesc());
					fieldElement.setAttribute("format", field.getFormat());
				}
			}
			
			setAction4Doc(doc, root, flowDef.getStartAction());
			
			if(flowDef.getEndActions() != null){
				Element endActionsElement = doc.createElement("endActions");
				root.appendChild(endActionsElement);
				for(BscFlowEndAction endAction : flowDef.getEndActions()){
					setAction4Doc(doc, endActionsElement, endAction);
				}
			}
			if(flowDef.getActions() != null){
				Element actionsElement = doc.createElement("actions");
				root.appendChild(actionsElement);
				for(BscFlowAction action : flowDef.getActions()){
					setAction4Doc(doc, actionsElement, action);
				}
			}
			
			DOMSource source = new DOMSource(doc);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, new StreamResult(out));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private static void setAction4Doc(Document doc, Node node, BscFlowAction action){
		Element actionElement = null;
		if(action instanceof BscFlowStartAction){
			BscFlowStartAction startAction = (BscFlowStartAction) action;
			actionElement = doc.createElement("startAction");
			setDisplay4Doc(doc, actionElement, startAction.getDisplay());
		}else if(action instanceof BscFlowEndAction){
			BscFlowEndAction endAction = (BscFlowEndAction) action;
			actionElement = doc.createElement("endAction");
			setDisplay4Doc(doc, actionElement, endAction.getDisplay());
		}else if(action instanceof BscFlowAction){
			BscFlowAction actionDef = (BscFlowAction) action;
			actionElement = doc.createElement("action");
			setDisplay4Doc(doc, actionElement, actionDef.getDisplay());
		}else{
			return;
		}
		node.appendChild(actionElement);
		actionElement.setAttribute("code", action.getCode());
		actionElement.setAttribute("componentCode", action.getComponentCode());
		actionElement.setAttribute("name", action.getName());
		actionElement.setAttribute("desc", action.getDesc());
		List<BscFlowDirection> directions = action.getDirections();
		if(directions != null && (!directions.isEmpty())){
			Element directionsElement = doc.createElement("directions");
			actionElement.appendChild(directionsElement);
			for(BscFlowDirection direction : directions){
				Element directionElement = doc.createElement("direction");
				directionsElement.appendChild(directionElement);
				directionElement.setAttribute("actionCode", direction.getActionCode());
				directionElement.setAttribute("condition", direction.getExpression());
				directionElement.setAttribute("desc", direction.getDesc());
			}
		}
		List<BscFieldRel> inputs = action.getInputs();
		if(inputs != null && (!inputs.isEmpty())){
			Element inputsElement = doc.createElement("inputs");
			actionElement.appendChild(inputsElement);
			for(BscFieldRel fieldRel : inputs){
				Element fieldRelElement = doc.createElement("fieldRel");
				inputsElement.appendChild(fieldRelElement);
				fieldRelElement.setAttribute("from", fieldRel.getFrom());
				fieldRelElement.setAttribute("to", fieldRel.getTo());
			}
		}
		List<BscFieldRel> outputs = action.getOutputs();
		if(outputs != null && (!outputs.isEmpty())){
			Element outputsElement = doc.createElement("outputs");
			actionElement.appendChild(outputsElement);
			for(BscFieldRel fieldRel : outputs){
				Element fieldRelElement = doc.createElement("fieldRel");
				outputsElement.appendChild(fieldRelElement);
				fieldRelElement.setAttribute("from", fieldRel.getFrom());
				fieldRelElement.setAttribute("to", fieldRel.getTo());
			}
		}
		Map<String, Object> params = action.getParams();
		if(params != null && (!params.isEmpty())){
			Element paramsElement = doc.createElement("params");
			actionElement.appendChild(paramsElement);
			for(Map.Entry<String, Object> param : params.entrySet()){
				Element paramElement = doc.createElement("param");
				paramsElement.appendChild(paramElement);
				paramElement.setAttribute("name", param.getKey());
				paramElement.setAttribute("value", String.valueOf(param.getValue()));
			}
		}
	}
	
	private static void setDisplay4Doc(Document doc, Node node, DisplayInfo display){
		if(null == display){
			return;
		}
		Element displayElement = doc.createElement("display");
		node.appendChild(displayElement);
		displayElement.setAttribute("x", String.valueOf(display.getX()));
		displayElement.setAttribute("y", String.valueOf(display.getY()));
		displayElement.setAttribute("width", String.valueOf(display.getWidth()));
		displayElement.setAttribute("height", String.valueOf(display.getHeight()));
	}
	
	public static BscFlow toBscFlow(InputStream in) throws Exception{
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = docBuilder.parse(in);
		Element root = doc.getDocumentElement();
		BscFlow flow = new BscFlow();
		flow.setCode(root.getAttribute("code"));
		flow.setName(root.getAttribute("name"));
		flow.setDesc(root.getAttribute("desc"));
		List<BscField> inputFields = new ArrayList<BscField>();
		List<BscField> outputFields = new ArrayList<BscField>();
		flow.setInputFields(inputFields);
		flow.setOutputFields(outputFields);
		List<BscFlowEndAction> endActions = new ArrayList<BscFlowEndAction>();
		List<BscFlowAction> actions = new ArrayList<BscFlowAction>();
		flow.setEndActions(endActions);
		flow.setActions(actions);
		NodeList nodes = root.getChildNodes();
		for(int i = 0, len = nodes.getLength(); i < len; i++){
			Node node = nodes.item(i);
			if("startAction".equals(node.getNodeName())){
				BscFlowStartAction startAction = new BscFlowStartAction();
				if(flow.getStartAction() != null){
					throw new Exception("duplicate startAction");
				}
				setAction(node, startAction);
				flow.setStartAction(startAction);
			}else if("endActions".equals(node.getNodeName())){
				NodeList childNodes = node.getChildNodes();
				for(int j = 0, lenJ = childNodes.getLength(); j < lenJ; j++){
					Node childNode = childNodes.item(j);
					if(Node.TEXT_NODE == childNode.getNodeType()){
						continue;
					}
					if("endAction".equals(childNode.getNodeName())){
						BscFlowEndAction endAction = new BscFlowEndAction();
						endActions.add(endAction);
						setAction(childNode, endAction);
					}
				}
			}else if("actions".equals(node.getNodeName())){
				NodeList childNodes = node.getChildNodes();
				for(int j = 0, lenJ = childNodes.getLength(); j < lenJ; j++){
					Node childNode = childNodes.item(j);
					if(Node.TEXT_NODE == childNode.getNodeType()){
						continue;
					}
					if("action".equals(childNode.getNodeName())){
						BscFlowAction action = new BscFlowAction();
						actions.add(action);
						setAction(childNode, action);
					}
				}
			}else if("inputFields".equals(node.getNodeName())){
				NodeList childNodes = node.getChildNodes();
				for(int j = 0, lenJ = childNodes.getLength(); j < lenJ; j++){
					Node childNode = childNodes.item(j);
					if(Node.TEXT_NODE == childNode.getNodeType()){
						continue;
					}
					if("field".equals(childNode.getNodeName())){
						BscField field = new BscField();
						field.setCode(getNodeAttrValue(childNode, "code"));
						field.setName(getNodeAttrValue(childNode, "name"));
						field.setDesc(getNodeAttrValue(childNode, "desc"));
						field.setFormat(getNodeAttrValue(childNode, "format"));
						inputFields.add(field);
					}
				}
			}else if("outputFields".equals(node.getNodeName())){
				NodeList childNodes = node.getChildNodes();
				for(int j = 0, lenJ = childNodes.getLength(); j < lenJ; j++){
					Node childNode = childNodes.item(j);
					if(Node.TEXT_NODE == childNode.getNodeType()){
						continue;
					}
					if("field".equals(childNode.getNodeName())){
						BscField field = new BscField();
						field.setCode(getNodeAttrValue(childNode, "code"));
						field.setName(getNodeAttrValue(childNode, "name"));
						field.setDesc(getNodeAttrValue(childNode, "desc"));
						field.setFormat(getNodeAttrValue(childNode, "format"));
						outputFields.add(field);
					}
				}
			}else if("display".equals(node.getNodeName())){
				DisplayInfo display = getDisplayBySelf(node);
				flow.setDisplay(display);
			}
		}
		setActionDirectionRel(flow);
		return flow;
	}
	
	private static void setActionDirectionRel(BscFlow flow){
		Map<String, BscFlowAction> cacheMap = new HashMap<String, BscFlowAction>();
		for(BscFlowAction action : flow.getActions()){
			cacheMap.put(action.getCode(), action);
		}
		for(BscFlowAction action : flow.getEndActions()){
			cacheMap.put(action.getCode(), action);
		}
		if(flow.getStartAction() != null && flow.getStartAction().getDirections() != null){
			for(BscFlowDirection direction : flow.getStartAction().getDirections()){
				((BscFlowDirection) direction).setSourceAction(flow.getStartAction());
				BscFlowAction targetAction = cacheMap.get(direction.getActionCode());
				if(targetAction != null){
					if(targetAction instanceof BscFlowAction){
						((BscFlowAction) targetAction).addSourceDirection(direction);
					}else if(targetAction instanceof BscFlowEndAction){
						((BscFlowEndAction) targetAction).addSourceDirection(direction);
					}
					((BscFlowDirection) direction).setTargetAction(targetAction);
				}
			}
		}
		if(flow.getActions() != null){
			for(BscFlowAction action : flow.getActions()){
				if(action.getDirections() != null){
					for(BscFlowDirection direction : action.getDirections()){
						((BscFlowDirection) direction).setSourceAction(action);
						BscFlowAction targetAction = cacheMap.get(direction.getActionCode());
						if(targetAction != null){
							if(targetAction instanceof BscFlowAction){
								((BscFlowAction) targetAction).addSourceDirection(direction);
							}else if(targetAction instanceof BscFlowEndAction){
								((BscFlowEndAction) targetAction).addSourceDirection(direction);
							}
							((BscFlowDirection) direction).setTargetAction(targetAction);
						}
					}
				}
			}
		}
	}
	
	private static void setAction(Node node, BscFlowAction action){
		action.setCode(getNodeAttrValue(node, "code"));
		action.setName(getNodeAttrValue(node, "name"));
		action.setDesc(getNodeAttrValue(node, "desc"));
		action.setComponentCode(getNodeAttrValue(node, "componentCode"));
		if(action instanceof BscFlowAction){
			((BscFlowAction) action).setDisplay(getDisplay(node));
		}else if(action instanceof BscFlowStartAction){
			((BscFlowStartAction) action).setDisplay(getDisplay(node));
		}else if(action instanceof BscFlowEndAction){
			((BscFlowEndAction) action).setDisplay(getDisplay(node));
		}
		
		NodeList childNodes = node.getChildNodes();
		for(int i = 0, len = childNodes.getLength(); i < len; i++){
			Node childNode = childNodes.item(i);
			if("directions".equals(childNode.getNodeName())){
				List<BscFlowDirection> directions = new ArrayList<BscFlowDirection>();
				action.setDirections(directions);
				NodeList directionNodes = childNode.getChildNodes();
				for(int j = 0, lenJ = directionNodes.getLength(); j < lenJ; j++){
					Node directionNode = directionNodes.item(j);
					if(Node.TEXT_NODE == directionNode.getNodeType()){
						continue;
					}
					if("direction".equals(directionNode.getNodeName())){
						BscFlowDirection direction = new BscFlowDirection();
						directions.add(direction);
						direction.setActionCode(getNodeAttrValue(directionNode, "actionCode"));
						direction.setExpression(getNodeAttrValue(directionNode, "condition"));
						direction.setDesc(getNodeAttrValue(directionNode, "desc"));
					}
				}
				//Collections.sort(directions, directionComparator);
			}else if("inputs".equals(childNode.getNodeName())){
				List<BscFieldRel> inputs = new ArrayList<BscFieldRel>();
				action.setInputs(inputs);
				NodeList inputNodes = childNode.getChildNodes();
				for(int j = 0, lenJ = inputNodes.getLength(); j < lenJ; j++){
					Node inputNode = inputNodes.item(j);
					if(Node.TEXT_NODE == inputNode.getNodeType()){
						continue;
					}
					if("fieldRel".equals(inputNode.getNodeName())){
						BscFieldRel fieldRel = new BscFieldRel();
						fieldRel.setFrom(getNodeAttrValue(inputNode, "from"));
						fieldRel.setTo(getNodeAttrValue(inputNode, "to"));
						inputs.add(fieldRel);
					}
				}
			}else if("outputs".equals(childNode.getNodeName())){
				List<BscFieldRel> outputs = new ArrayList<BscFieldRel>();
				action.setOutputs(outputs);
				NodeList outputNodes = childNode.getChildNodes();
				for(int j = 0, lenJ = outputNodes.getLength(); j < lenJ; j++){
					Node outputNode = outputNodes.item(j);
					if(Node.TEXT_NODE == outputNode.getNodeType()){
						continue;
					}
					if("fieldRel".equals(outputNode.getNodeName())){
						BscFieldRel fieldRel = new BscFieldRel();
						fieldRel.setFrom(getNodeAttrValue(outputNode, "from"));
						fieldRel.setTo(getNodeAttrValue(outputNode, "to"));
						outputs.add(fieldRel);
					}
				}
			}else if("params".equals(childNode.getNodeName())){
				Map<String, Object> params = new HashMap<String, Object>();
				action.setParams(params);
				NodeList paramNodes = childNode.getChildNodes();
				for(int j = 0, lenJ = paramNodes.getLength(); j < lenJ; j++){
					Node paramNode = paramNodes.item(j);
					if(Node.TEXT_NODE == paramNode.getNodeType()){
						continue;
					}
					if("param".equals(paramNode.getNodeName())){
						params.put(getNodeAttrValue(paramNode, "name"),
								getNodeAttrValue(paramNode, "value"));
					}
					if(action instanceof BscFlowAction
							&& BscFlowAction.ACTION_TYPE_CALL.equals(params.get(BscFlowAction.ACTION_PARAM_ACTION_TYPE))){
						((BscFlowAction) action).setType(BscFlowAction.ACTION_TYPE_CALL);
					}
				}
			}
		}
	}
	
	private static String getNodeAttrValue(Node node, String name){
		NamedNodeMap attrMap = node.getAttributes();
		if(null == attrMap){
			return null;
		}
		Node attr = attrMap.getNamedItem(name);
		if(null == attr){
			return null;
		}
		return attr.getNodeValue();
	}
	
	private static DisplayInfo getDisplay(Node node){
		NodeList childNodes = node.getChildNodes();
		for(int i = 0, len = childNodes.getLength(); i < len; i++){
			Node childNode = childNodes.item(i);
			if("display".equals(childNode.getNodeName())){
				return getDisplayBySelf(childNode);
			}
		}
		return null;
	}
	
	private static DisplayInfo getDisplayBySelf(Node node){
		DisplayInfo display = new DisplayInfo();
		int x = 0;
		int y = 0;
		int width = 80;
		int height = 30;
		try{
			x = Integer.parseInt(getNodeAttrValue(node, "x"));
		}catch(Exception e){}
		try{
			y = Integer.parseInt(getNodeAttrValue(node, "y"));
		}catch(Exception e){}
		try{
			width = Integer.parseInt(getNodeAttrValue(node, "width"));
		}catch(Exception e){}
		try{
			height = Integer.parseInt(getNodeAttrValue(node, "height"));
		}catch(Exception e){}
		display.setX(x);
		display.setY(y);
		display.setWidth(width);
		display.setHeight(height);
		return display;
	}

	public static void main(String[] args) throws Exception {
//		BscFlow flow = new BscFlow();
//		flow.setCode("ss");
//		DisplayInfo display = new DisplayInfo();
//		display.setX(32);
//		flow.setDisplay(display);
//		BscFlowEndAction endAction = new BscFlowEndAction();
//		endAction.setCode("end1");
//		List<BscFlowEndAction> endActions = new ArrayList<BscFlowEndAction>();
//		flow.setEndActions(endActions);
//		endActions.add(endAction);
		
		BscFlow flow = toBscFlow(new FileInputStream("D:/document/workspace_sf/BSC/src/flows/flow2.xml"));
		toXML(flow, System.out);
	}
}
