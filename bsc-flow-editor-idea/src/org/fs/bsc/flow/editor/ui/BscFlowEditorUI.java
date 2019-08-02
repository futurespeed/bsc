package org.fs.bsc.flow.editor.ui;

import com.intellij.designer.ModuleProvider;
import com.intellij.openapi.actionSystem.DataProvider;
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
import org.fs.bsc.flow.editor.model.BscFlow;
import org.fs.bsc.flow.editor.support.XmlBscFlowTransformer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;

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


        designPanel = new BscFlowDesignPanel();
        JScrollPane designWrapperPanel = new JBScrollPane(designPanel);
        designWrapperPanel.setBorder(null);
        designWrapperPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        designWrapperPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
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
        //TODO
//        LOG.debug("save(): group ID=" + this.myNextSaveGroupId);
//        CommandProcessor.getInstance().executeCommand(this.getProject(), () -> {
//            ApplicationManager.getApplication().runWriteAction(() -> {
//                this.myInsideChange = true;
//
//                try {
//                    BscFlowWriter writer = new BscFlowWriter();
//                    this.getRootContainer().write(writer);
//                    String newText = writer.getText();
//                    String oldText = this.document.getText();
//
//                    try {
//                        GuiEditor.ReplaceInfo replaceInfo = findFragmentToChange(oldText, newText);
//                        if (replaceInfo.getStartOffset() != -1) {
//                            this.document.replaceString(replaceInfo.getStartOffset(), replaceInfo.getEndOffset(), replaceInfo.getReplacement());
//                        }
//                    } catch (Exception var8) {
//                        LOG.error(var8);
//                        this.document.replaceString(0, oldText.length(), newText);
//                    }
//                } finally {
//                    this.myInsideChange = false;
//                }
//
//            });
//        }, "UI Designer Save", this.myNextSaveGroupId);
//        this.myNextSaveGroupId = new Object();
//        this.fireHierarchyChanged();
    }
}
