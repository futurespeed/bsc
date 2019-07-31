package org.fs.bsc.flow.editor;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.fs.bsc.flow.editor.support.BscFileType;
import org.jetbrains.annotations.NotNull;

public class BscFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(BscFileType.INSTANCE);
    }
}
