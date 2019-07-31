package org.fs.bsc.flow.editor.ui;

import com.intellij.designer.DesignerEditorPanelFacade;
import com.intellij.designer.LightFillLayout;
import com.intellij.designer.ModuleProvider;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ThreeComponentsSplitter;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.openapi.vfs.VirtualFile;
import org.fs.bsc.flow.editor.BscFlowEditor;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class BscFlowEditorUI extends JPanel implements DesignerEditorPanelFacade, DataProvider, ModuleProvider {

    private static final Logger LOG = Logger.getInstance("#" + BscFlowEditorUI.class.getName());

    private final ThreeComponentsSplitter splitter;
    private final Module module;
    private final Project project;
    private final VirtualFile file;
    private final Document document;
    //private final DocumentListener myDocumentListener;

    public BscFlowEditorUI(BscFlowEditor editor, Project project, Module module, VirtualFile virtualFile){
        this.project = project;
        this.module = module;
        this.file = virtualFile;
        splitter = new ThreeComponentsSplitter();

        splitter.setDividerWidth(0);
        splitter.setDividerMouseZoneSize(Registry.intValue("ide.splitter.mouseZone"));
        add(splitter, "Center");

        JPanel contentPanel = new JPanel(new LightFillLayout());
        JLabel toolbar = new JLabel();
        toolbar.setVisible(false);
        contentPanel.add(toolbar);

        JLabel testLabel = new JLabel("BSC Test");
        testLabel.setText("BSC Test");
        contentPanel.add(testLabel);
        splitter.setInnerComponent(contentPanel);

        this.document = FileDocumentManager.getInstance().getDocument(file);
    }

    @Override
    public ThreeComponentsSplitter getContentSplitter() {
        return splitter;
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

    public void refreshAndSave(){
        //TODO
    }

    public void refresh(){
        //TODO
        //this.repaint();
    }

    public void save(){
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
