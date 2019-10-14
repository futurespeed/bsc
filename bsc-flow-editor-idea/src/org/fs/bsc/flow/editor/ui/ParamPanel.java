package org.fs.bsc.flow.editor.ui;

import com.intellij.icons.AllIcons;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.ui.table.JBTable;
import org.fs.bsc.flow.editor.model.BscField;
import org.fs.bsc.flow.editor.ui.tools.IconLabel;

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
        inputTable.getModel().addTableModelListener(e -> {
            ui.save();
        });
        outputTable = new JBTable(new ParamTableModel(ui.getFlow().getOutputFields()));
        outputTable.getModel().addTableModelListener(e -> {
            ui.save();
        });

        JPanel inputTitlePanel = new JPanel();
        inputTitlePanel.setLayout(new BoxLayout(inputTitlePanel, BoxLayout.LINE_AXIS));
        inputTitlePanel.add(new JLabel("Inputs:   "));
        inputTitlePanel.add(new IconLabel(AllIcons.General.Add, "add", "Add", code -> {
            ui.getFlow().getInputFields().add(new BscField());
        }));
        inputTitlePanel.add(new JLabel("  "));
        inputTitlePanel.add(new IconLabel(AllIcons.General.Remove, "remove", "Remove", code -> {
            int rowIdx = inputTable.getSelectedRow();
            if(rowIdx < 0) {
                return;
            }
            ui.getFlow().getInputFields().remove(rowIdx);
            inputTable.clearSelection();
        }));
        inputTitlePanel.add(new JLabel("  "));
        add(inputTitlePanel);
        inputTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        add(inputTable.getTableHeader());
        add(inputTable);


        add(new JLabel(" "));
        JPanel outputTitlePanel = new JPanel();
        outputTitlePanel.setLayout(new BoxLayout(outputTitlePanel, BoxLayout.LINE_AXIS));
        outputTitlePanel.add(new JLabel("Outputs:   "));
        outputTitlePanel.add(new IconLabel(AllIcons.General.Add, "add", "Add", code -> {
            ui.getFlow().getOutputFields().add(new BscField());
        }));
        outputTitlePanel.add(new JLabel("  "));
        outputTitlePanel.add(new IconLabel(AllIcons.General.Remove, "remove", "Remove", code -> {
            int rowIdx = outputTable.getSelectedRow();
            if(rowIdx < 0) {
                return;
            }
            ui.getFlow().getOutputFields().remove(rowIdx);
            outputTable.clearSelection();
        }));
        outputTitlePanel.add(new JLabel("  "));
        add(outputTitlePanel);

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
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
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

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            BscField field = fields.get(rowIndex);
            if (0 == columnIndex) {
                field.setCode(String.valueOf(aValue));
            }
            if (1 == columnIndex) {
                field.setName(String.valueOf(aValue));
            }
            if (2 == columnIndex) {
                field.setDesc(String.valueOf(aValue));
            }
            fireTableDataChanged();
        }

        public List<BscField> getFields() {
            return fields;
        }

        public void setFields(List<BscField> fields) {
            this.fields = fields;
        }
    }
}
