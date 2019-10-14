package org.fs.bsc.flow.editor.ui;

import com.intellij.ui.components.panels.VerticalLayout;
import org.fs.bsc.flow.editor.model.BscFlowDirection;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyEvent;

public class DirectionParamDialog extends JDialog {

    private BscFlowEditorUI ui;

    private BscFlowDirection direction;

    private BscFlowDirectionPart directionPart;

    private JTextField nameField;

    private JTextField expressionField;

    public DirectionParamDialog(BscFlowEditorUI ui, BscFlowDirection direction, BscFlowDirectionPart directionPart) {
        super((Frame) null, true);
        this.ui = ui;
        this.direction = direction;
        this.directionPart = directionPart;
    }

    public void init() {
        setTitle("Direction Parameters");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new VerticalLayout(0));
        getRootPane().registerKeyboardAction(e -> {
            dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        nameField = new JTextField();
        nameField.setText(direction.getDesc());
        nameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void docChange(javax.swing.event.DocumentEvent e) {
                javax.swing.text.Document document = e.getDocument();
                try {
                    direction.setDesc(document.getText(0, document.getLength()));
                    directionPart.repaint();
                    ui.save();
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                docChange(e);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                docChange(e);
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                docChange(e);
            }
        });

        expressionField = new JTextField();
        expressionField.setText(direction.getExpression());
        expressionField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void docChange(javax.swing.event.DocumentEvent e) {
                javax.swing.text.Document document = e.getDocument();
                try {
                    direction.setExpression(document.getText(0, document.getLength()));
                    ui.save();
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                docChange(e);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                docChange(e);
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                docChange(e);
            }
        });

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(new JLabel("Direction Description"));
        namePanel.add(nameField);

        JPanel expressionPanel = new JPanel();
        expressionPanel.setLayout(new BoxLayout(expressionPanel, BoxLayout.X_AXIS));
        expressionPanel.add(new JLabel("Direction Expression"));
        expressionPanel.add(expressionField);

        add(namePanel);
        add(expressionPanel);
    }
}
