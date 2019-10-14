package org.fs.bsc.flow.editor.ui.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IconLabel extends JLabel {
    private Icon icon;
    private String code;
    private String text;
    private ClickAction clickAction;

    public IconLabel(Icon icon, String code, String text, ClickAction clickAction){
        this.icon = icon;
        this.code = code;
        this.text = text;
        this.clickAction = clickAction;
        init();
    }

    protected void init(){
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setIcon(icon);
        setText(text);
        setToolTipText(text);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickAction.performed(code);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setText("<html><u>" + text + "</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setText(text);
            }
        });
    }

    public interface ClickAction{
        void performed(String code);
    }
}
