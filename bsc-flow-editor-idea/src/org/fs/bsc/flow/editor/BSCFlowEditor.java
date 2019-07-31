package org.fs.bsc.flow.editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.PossiblyDumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;
import org.fs.bsc.flow.editor.ui.BscFlowEditorUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class BscFlowEditor extends UserDataHolderBase implements FileEditor, PossiblyDumbAware {

    private final VirtualFile file;

    private final BscFlowEditorUI ui;

    public BscFlowEditor(@NotNull Project project, @NotNull VirtualFile virtualFile){
        VirtualFile vf = virtualFile instanceof LightVirtualFile ? ((LightVirtualFile)virtualFile).getOriginalFile() : virtualFile;
        Module module = ModuleUtilCore.findModuleForFile(vf, project);
        if (module == null) {
            throw new IllegalArgumentException("No module for file " + virtualFile + " in project " + project);
        } else {
            this.file = virtualFile;
            this.ui = new BscFlowEditorUI(this, project, module, virtualFile);
        }
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return ui;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setState(@NotNull FileEditorState fileEditorState) {

    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return "bsc".equals(file.getExtension());
    }

    @Override
    public void selectNotify() {

    }

    @Override
    public void deselectNotify() {

    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Override
    public void dispose() {

    }
}
