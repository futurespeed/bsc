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
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import org.fs.bsc.flow.editor.BscFlowEditor;
import org.fs.bsc.flow.editor.model.BscFlow;
import org.fs.bsc.flow.editor.support.XmlBscFlowTransformer;
import org.fs.bsc.flow.editor.ui.tools.GroupPanel;
import org.fs.bsc.flow.editor.ui.tools.IconLabel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public BscFlowEditorUI(BscFlowEditor editor, Project project, Module module, VirtualFile virtualFile) {
        this.project = project;
        this.module = module;
        this.file = virtualFile;

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
        designPanel = new BscFlowDesignPanel();
        designWrapperPanel.add(designPanel, BorderLayout.CENTER);


        java.util.List<GroupPanel.TogglePanel> groups = new ArrayList<>();
        List<IconLabel> baseGroupItems = new ArrayList<>();
        baseGroupItems.add(new IconLabel(AllIcons.General.Mouse, "select", "选择", code -> {
            Messages.showMessageDialog(code,"Sample", Messages.getInformationIcon());
        }));
        baseGroupItems.add(new IconLabel(AllIcons.General.Locate, "connect", "连接", code -> {
            Messages.showMessageDialog(code,"Sample", Messages.getInformationIcon());
        }));
        GroupPanel.TogglePanel baseGroup = new GroupPanel.TogglePanel("base", "基本", baseGroupItems, true, false);
        groups.add(baseGroup);

        //FIXME
        for(int i = 0; i < 8; i++) {
            List<IconLabel> items = new ArrayList<>();
            for(int j = 0; j < 5; j++){
                items.add(new IconLabel(AllIcons.General.Settings, "code_" + i + "_" + j, "功能" + i, code -> {
                    Messages.showMessageDialog(code,"Sample", Messages.getInformationIcon());
                }));
            }
            groups.add(new GroupPanel.TogglePanel("base" + i, "功能组" + i, items , false, true));// FIXME
        }

        groupPanel = new GroupPanel(groups);

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
                refresh();
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

    public void refreshAndSave() {
        //TODO
    }

    public void refresh() {
        //TODO
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
}
