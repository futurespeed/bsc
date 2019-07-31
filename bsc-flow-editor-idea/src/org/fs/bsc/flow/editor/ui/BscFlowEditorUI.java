package org.fs.bsc.flow.editor.ui;

import com.intellij.designer.DesignerEditorPanelFacade;
import com.intellij.designer.LightFillLayout;
import com.intellij.designer.ModuleProvider;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.diagnostic.Logger;
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

    public BscFlowEditorUI(BscFlowEditor editor, Project project, Module module, VirtualFile virtualFile){
        this.project = project;
        this.module = module;
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
    public Object getData(String s) {
        //TODO
        return null;
    }
}
