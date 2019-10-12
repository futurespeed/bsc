package org.fs.bsc.flow.editor.ui;

import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.ui.table.JBTable;
import org.fs.bsc.flow.editor.model.BscField;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class ParamPanel extends JPanel {
    private JTable inputTable;
    private JTable outputTable;

    private BscFlowEditorUI ui;

    public ParamPanel(BscFlowEditorUI ui) {
        this.ui = ui;
    }

    public void init() {
        setLayout(new VerticalLayout(0));
        inputTable = new JBTable(new ParamTableModel(ui.getFlow().getInputFields()));
        outputTable = new JBTable(new ParamTableModel(ui.getFlow().getOutputFields()));

        add(new JLabel("Inputs:"));
        inputTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        add(inputTable.getTableHeader());
        add(inputTable);
        add(new JLabel("outputs:"));
        outputTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        add(outputTable.getTableHeader());
        add(outputTable);
    }

    public static class ParamTableModel extends AbstractTableModel {

        private List<BscField> fields;
        private static final String[] COLS = {"code", "name", "desc"};

        public ParamTableModel(List<BscField> fields) {
            this.fields = fields;
        }

        @Override
        public int getRowCount() {
            return fields.size();
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
        public Object getValueAt(int rowIndex, int columnIndex) {
            BscField field = fields.get(rowIndex);
            if (0 == columnIndex) {
                return field.getCode();
            }
            if (1 == columnIndex) {
                return field.getName();
            }
            if (2 == columnIndex) {
                return field.getDesc();
            }
            return null;
        }

        public List<BscField> getFields() {
            return fields;
        }

        public void setFields(List<BscField> fields) {
            this.fields = fields;
        }
    }
}
