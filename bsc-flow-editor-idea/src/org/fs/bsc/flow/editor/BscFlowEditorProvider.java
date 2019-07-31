package org.fs.bsc.flow.editor;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class BscFlowEditorProvider implements FileEditorProvider, DumbAware {

    private static final Logger LOG = Logger.getInstance("#" + BscFlowEditorProvider.class.getName());

    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return "bsc".equals(virtualFile.getExtension());
    }

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        LOG.assertTrue(accept(project, virtualFile));
        BscFlowEditor bscFlowEditor = new BscFlowEditor(project, virtualFile);
        return bscFlowEditor;
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return "bsc-flow-editor";
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }
}
