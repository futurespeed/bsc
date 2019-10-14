package org.fs.bsc.flow.editor.ui;

import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.ui.table.JBTable;
import org.fs.bsc.flow.editor.model.BscComponent;
import org.fs.bsc.flow.editor.model.BscFlowAction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionParamDialog extends JDialog {

    private BscFlowEditorUI ui;

    private BscComponent component;

    private BscFlowAction action;

    private JTable paramTable;

    public ActionParamDialog(BscFlowEditorUI ui, BscComponent component, BscFlowAction action) {
        super((Frame) null, true);
        this.ui = ui;
        this.component = component;
        this.action = action;
    }

    public void init() {
        setTitle("[" + action.getName() + "] Parameters");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new VerticalLayout(0));
        getRootPane().registerKeyboardAction(e -> {
            dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        List<Map<String, Object>> paramDefs = new ArrayList<>();
        for (Object def : component.getParams().values()) {
            paramDefs.add((Map<String, Object>) def);
        }
        Map<String, Object> params = action.getParams();
        if (null == params) {
            params = new HashMap<>();
            action.setParams(params);
        }
        paramTable = new JBTable(new ActionParamTableModel(paramDefs, params));
        paramTable.getModel().addTableModelListener(e -> {
            ui.save();
        });
        paramTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        add(paramTable.getTableHeader());
        add(paramTable);
    }

    public static class ActionParamTableModel extends AbstractTableModel {

        private Map<String, Object> params;
        private static final String[] COLS = {"name", "parameter"};
        private List<Map<String, Object>> paramDefs;

        public ActionParamTableModel(List<Map<String, Object>> paramDefs, Map<String, Object> params) {
            this.paramDefs = paramDefs;
            this.params = params;
        }

        @Override
        public int getRowCount() {
            return paramDefs.size();
        }

        @Override
        public int getColumnCount() {
            return COLS.length;
        }

        @Override
        public String getColumnName(int column) {
            return COLS[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (0 == columnIndex) {
                return false;
            }
            return true;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Map<String, Object> def = paramDefs.get(rowIndex);
            if (0 == columnIndex) {
                return def.get("name");
            }
            if (1 == columnIndex) {
                return params.get(def.get("code"));
            }
            return null;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Map<String, Object> def = paramDefs.get(rowIndex);
            if (0 == columnIndex) {
                return;
            }
            if (1 == columnIndex) {
                params.put((String) def.get("code"), aValue);
            }
            fireTableDataChanged();
        }
    }
}
