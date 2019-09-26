package org.fs.bsc.flow.editor.ui;

import com.intellij.designer.ModuleProvider;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import org.fs.bsc.flow.editor.BscFlowEditor;
import org.fs.bsc.flow.editor.command.AddActionCommand;
import org.fs.bsc.flow.editor.command.AddConnectionCommand;
import org.fs.bsc.flow.editor.command.CommandManager;
import org.fs.bsc.flow.editor.command.DeleteActionCommand;
import org.fs.bsc.flow.editor.model.*;
import org.fs.bsc.flow.editor.selection.Selectable;
import org.fs.bsc.flow.editor.selection.SelectionManager;
import org.fs.bsc.flow.editor.support.XmlBscComponentTransformer;
import org.fs.bsc.flow.editor.support.XmlBscFlowTransformer;
import org.fs.bsc.flow.editor.ui.tools.GroupPanel;
import org.fs.bsc.flow.editor.ui.tools.IconLabel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BscFlowEditorUI extends JPanel implements DataProvider, ModuleProvider {

    private static final Logger LOG = Logger.getInstance("#" + BscFlowEditorUI.class.getName());

    private final Module module;
    private final Project project;
    private final VirtualFile file;
    private final Document document;

    private final JTextField flowCodeField;
    private final JTextField flowNameField;
    private final JTextArea flowDescArea;
    private final JTextArea codeArea;
    private final BscFlowDesignPanel designPanel;
    private final GroupPanel groupPanel;

    private final BscFlow flow;

    private final CommandManager commandManager;

    private final SelectionManager selectionManager;

    private final Map<String, BscComponent> componentMap;

    public BscFlowEditorUI(BscFlowEditor editor, Project project, Module module, VirtualFile virtualFile) {
        this.project = project;
        this.module = module;
        this.file = virtualFile;

        commandManager = new CommandManager();
        selectionManager = new SelectionManager();
        componentMap = new HashMap<>();
        flowCodeField = new JTextField();
        flowCodeField.setEditable(false);
        flowNameField = new JTextField();
        flowDescArea = new JTextArea();
        flowDescArea.setLineWrap(true);
        flowDescArea.setRows(100);

        codeArea = new JTextArea();
        codeArea.setEditable(false);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setSize(300, 200);

        JPanel infoIdPanel = new JPanel(new GridLayout(1, 2));
        infoIdPanel.add(new JLabel("Flow Code"));
        infoIdPanel.add(flowCodeField);
        infoPanel.add(infoIdPanel);

        JPanel infoNamePanel = new JPanel(new GridLayout(1, 2));
        infoNamePanel.add(new JLabel("Flow Name"));
        infoNamePanel.add(flowNameField);
        infoPanel.add(infoNamePanel);

        JPanel infoDescPanel = new JPanel(new GridLayout(1, 2));
        infoDescPanel.add(new JLabel("Flow Description"));
        JScrollPane descScrollPane = new JBScrollPane(flowDescArea);
        descScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        descScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        infoDescPanel.add(descScrollPane);
        infoPanel.add(infoDescPanel);

        JTabbedPane tabbedPane = new JBTabbedPane(JTabbedPane.BOTTOM);
        JPanel infoWrapperPanel = new JPanel(new BorderLayout());
        infoWrapperPanel.add(infoPanel, BorderLayout.CENTER);
        tabbedPane.addTab("Information", infoWrapperPanel);
        tabbedPane.addTab("Parameters", new JPanel());

        JPanel designWrapperPanel = new JPanel();
        designWrapperPanel.setLayout(new BorderLayout());
        designPanel = new BscFlowDesignPanel(this);
        designWrapperPanel.add(designPanel, BorderLayout.CENTER);


        groupPanel = createGroupPanel();

        JScrollPane toolsPanel = new JBScrollPane(groupPanel);
        toolsPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        toolsPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        toolsPanel.setPreferredSize(new Dimension(140, 0));

        designWrapperPanel.add(toolsPanel, BorderLayout.WEST);
        tabbedPane.addTab("Flow Design", designWrapperPanel);

        JScrollPane codeWrapperPanel = new JBScrollPane(codeArea);
        codeWrapperPanel.setBorder(null);
        codeWrapperPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        codeWrapperPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tabbedPane.addTab("Code Preview", codeWrapperPanel);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);


        this.document = FileDocumentManager.getInstance().getDocument(file);
        document.addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
//                refresh();
            }
        });

        this.flow = readFlow(this.document);
        designPanel.setFlow(this.flow);

        refresh();
    }

    @Override
    public Module getModule() {
        return module;
    }

    @Override
    public Project getProject() {
        return project;
    }

    @Nullable
    @Override
    public Object getData(String dataId) {
        //TODO
        return null;
    }

    public BscFlow readFlow(Document document) {
        try {
            return XmlBscFlowTransformer.toBscFlow(new ByteArrayInputStream(document.getText().getBytes("UTF-8")));
        } catch (Exception e) {
            throw new IllegalArgumentException("Fail to read BSC Flow file !", e);
        }
    }

    public void refresh() {
        String content = document.getText();
        flowCodeField.setText(flow.getCode());
        flowNameField.setText(flow.getName());
        flowDescArea.setText(flow.getDesc());
        codeArea.setText(content);
        designPanel.refresh();
    }

    public void save() {
        CommandProcessor.getInstance().executeCommand(this.getProject(), () -> {
            ApplicationManager.getApplication().runWriteAction(() -> {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                XmlBscFlowTransformer.toXML(flow, out);
                String newText;
                try {
                    newText = new String(out.toByteArray(), "UTF-8");
                    newText = newText.replaceAll("\\r", "");
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalArgumentException(e);
                }
                document.setText(newText);
            });
        }, "BSC Flow Save", new Object());
    }

    private GroupPanel createGroupPanel() {
        IconLabel.ClickAction clickAction = code -> {
            if ("select".equals(code)) {
                commandManager.setCurrentCommand(null);
                selectionManager.cleanSelection();
            } else if ("connect".equals(code)) {
                AddConnectionCommand command = new AddConnectionCommand();
                command.setFlow(flow);
                commandManager.setCurrentCommand(command);
            } else if ("delete".equals(code)) {
                if(selectionManager.hasSelection()) {
                    for(Selectable selectable : selectionManager.getSelections()) {
                        if(selectable instanceof BscFlowActionPart) {
                            BscFlowActionPart part = (BscFlowActionPart) selectable;
                            DeleteActionCommand command = new DeleteActionCommand();
                            command.setAction(part.getAction());
                            command.setFlow(flow);
                            command.execute();
                        }
                    }
                    refresh();
                }
            } else if ("start".equals(code)) {
                AddActionCommand command = new AddActionCommand();
                command.setFlow(flow);
                BscFlowAction action = new BscFlowStartAction();
                command.setAction(action);
                action.setComponentCode(code);
                commandManager.setCurrentCommand(command);
            } else if ("end".equals(code)) {
                AddActionCommand command = new AddActionCommand();
                command.setFlow(flow);
                BscFlowAction action = new BscFlowEndAction();
                action.setComponentCode(code);
                command.setAction(action);
                commandManager.setCurrentCommand(command);
            } else if ("call".equals(code)) {
                AddActionCommand command = new AddActionCommand();
                command.setFlow(flow);
                BscFlowAction action = new BscFlowAction();
                action.setComponentCode(BscFlowAction.ACTION_TYPE_CALL);
                action.setName("Call Action");
                command.setAction(action);
                commandManager.setCurrentCommand(command);
            } else {
                AddActionCommand command = new AddActionCommand();
                command.setFlow(flow);
                BscFlowAction action = new BscFlowAction();
                action.setComponentCode(code);
                action.setName(componentMap.get(code).getName());
                command.setAction(action);
                commandManager.setCurrentCommand(command);
            }
        };
        java.util.List<GroupPanel.TogglePanel> groups = new ArrayList<>();
        List<IconLabel> baseGroupItems = new ArrayList<>();
        baseGroupItems.add(new IconLabel(AllIcons.General.Mouse, "select", "Select", clickAction));
        baseGroupItems.add(new IconLabel(AllIcons.General.Locate, "connect", "Connect", clickAction));
        baseGroupItems.add(new IconLabel(AllIcons.General.Remove, "delete", "Delete", clickAction));
        GroupPanel.TogglePanel baseGroup = new GroupPanel.TogglePanel("base", "base", baseGroupItems, true, false);
        groups.add(baseGroup);

        List<IconLabel> baseComponentGroupItems = new ArrayList<>();
        baseComponentGroupItems.add(new IconLabel(AllIcons.Actions.Execute, "start", "Start Action", clickAction));
        baseComponentGroupItems.add(new IconLabel(AllIcons.Actions.Suspend, "end", "End Action", clickAction));
        baseComponentGroupItems.add(new IconLabel(AllIcons.Actions.Lightning, "call", "Call Action", clickAction));
        GroupPanel.TogglePanel baseComponentGroup =
                new GroupPanel.TogglePanel("baseComponent", "Base Components", baseComponentGroupItems, false, false);
        groups.add(baseComponentGroup);

        List<BscComponent> components;
        try (InputStream in = new FileInputStream(new File(file.getParent().getPath() + File.separator + "bsc_components.xml"))) {
            components = XmlBscComponentTransformer.toActionComponent(in);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        List<IconLabel> items = new ArrayList<>();
        for (BscComponent component : components) {
            componentMap.put(component.getCode(), component);
            items.add(new IconLabel(AllIcons.General.Settings, component.getCode(), component.getName(), clickAction));
        }
        groups.add(new GroupPanel.TogglePanel("extended", "Extended Components", items, false, false));

        return new GroupPanel(groups);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
    }
}
