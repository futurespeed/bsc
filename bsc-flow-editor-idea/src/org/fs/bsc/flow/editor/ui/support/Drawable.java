package org.fs.bsc.flow.editor.ui.support;

import java.awt.*;

public interface Drawable {
    void drawStart(Point point);
    void drawStop(Point point);
    void drawMove(Point point);
}
