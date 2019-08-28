package org.fs.bsc.flow.editor.ui.tools;

import com.intellij.ui.components.panels.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GroupPanel extends JPanel {
    private List<TogglePanel> groups;

    public GroupPanel(List<TogglePanel> groups){
        this.groups = groups;
        init();
    }

    protected void init(){
//        setMinimumSize(new Dimension(300, 100));
        setLayout(new VerticalLayout(3));
        for(TogglePanel group : groups){
            add(group);
        }
    }

    public static class TogglePanel extends JPanel {
        private String code;
        private String name;
        private List<IconLabel> items;
        private boolean hideTitle;
        private boolean collapse;

        private JButton button;
        private JPanel container;

        public TogglePanel(String code, String name, List<IconLabel> items, boolean hideTitle, boolean collapse) {
            this.code = code;
            this.name = name;
            this.items = items;
            this.hideTitle = hideTitle;
            this.collapse = collapse;
            init();
        }

        protected void init() {
            setLayout(new VerticalLayout(1));
            button = new JButton(name);
//            button.setSize(200, 80);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    toggle();
                }
            });

            add(button);
            if (hideTitle) {
                button.setVisible(false);
            }
            container = new JPanel();
            container.setLayout(new GridLayout(0, 1));
            add(container);
            if(collapse){
                collapse();
            }
            for (IconLabel item : items) {
                container.add(item);
            }
        }

        public List<IconLabel> getItems() {
            return items;
        }

        public void toggle() {
            if (collapse) {
                expend();
            } else {
                collapse();
            }
        }

        public void collapse() {
            container.setVisible(false);
            collapse = true;
        }

        public void expend() {
            container.setVisible(true);
            collapse = false;
        }
    }
}
