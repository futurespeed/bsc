package org.fs.bsc.flow.editor.support;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class BscFileType implements FileType {

    public static final BscFileType INSTANCE = new BscFileType();

    @NotNull
    @Override
    public String getName() {
        return "BSC_FLOW";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Business Service Component - Flow Logic";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "bsf";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return PlatformIcons.FUNCTION_ICON;
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
        return "UTF-8";
    }
}
