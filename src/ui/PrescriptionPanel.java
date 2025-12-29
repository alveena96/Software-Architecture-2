package ui;

import controller.PrescriptionController;
import domain.Prescription;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.util.function.Consumer;

public class PrescriptionPanel extends JPanel {
    private final PrescriptionController controller;
    private final Consumer<String> status;
    private final Runnable persistToCsv;
    private final Runnable reloadFromCsv;

    private final PrescriptionTableModel model;
    private final JTable table;
    private final JTextField search = new JTextField(22);

    public PrescriptionPanel(PrescriptionController controller, Consumer<String> status, Runnable persistToCsv, Runnable reloadFromCsv) {
        super(new BorderLayout(10,10));
        this.controller = controller;
        this.status = status;
        this.persistToCsv = persistToCsv;
        this.reloadFromCsv = reloadFromCsv;

        model = new PrescriptionTableModel(controller);
        table = new JTable(model);
        Ui.style(table);

        TableRowSorter<PrescriptionTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JPanel header = new JPanel(new BorderLayout(10,10));
        JLabel title = new JLabel("Prescriptions");
        title.setFont(title.getFont().deriveFont(Font.BOLD, title.getFont().getSize()+4f));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));
        right.add(new JLabel("Search:"));
        right.add(search);
        JButton add = new JButton("Add");
        right.add(add);
        header.add(title, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        search.getDocument().addDocumentListener((DocListener) e -> {
            String q = search.getText().trim();
            sorter.setRowFilter(q.isEmpty() ? null : RowFilter.regexFilter("(?i)"+java.util.regex.Pattern.quote(q)));
        });

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");
        JButton export = new JButton("Export TXT");
        JButton refresh = new JButton("Refresh");
        actions.add(edit); actions.add(del); actions.add(export); actions.add(refresh);

        add.addActionListener(e -> onAdd());
        edit.addActionListener(e -> onEdit());
        del.addActionListener(e -> onDelete());
        export.addActionListener(e -> onExport());
        refresh.addActionListener(e -> reloadFromCsv.run());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount()==2) onEdit();
            }
        });

        add(header, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        reloadView();
    }

    private int selectedModelRow() {
        int vr = table.getSelectedRow();
        return vr < 0 ? -1 : table.convertRowIndexToModel(vr);
    }

    private void onAdd() {
        Prescription seed = controller.blank();
        PrescriptionDialog dlg = new PrescriptionDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Prescription", seed);
        dlg.setVisible(true);
        Prescription r = dlg.result();
        if (r != null) {
            controller.add(r);
            persistToCsv.run();
            reloadView();
            status.accept("Saved new prescription: " + r.id());
        }
    }

    private void onEdit() {
        int row = selectedModelRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a prescription first."); return; }
        Prescription existing = controller.all().get(row);
        PrescriptionDialog dlg = new PrescriptionDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Prescription", existing);
        dlg.setVisible(true);
        Prescription r = dlg.result();
        if (r != null) {
            controller.set(row, r);
            persistToCsv.run();
            reloadView();
            status.accept("Saved prescription changes: " + r.id());
        }
    }

    private void onDelete() {
        int row = selectedModelRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a prescription first."); return; }
        Prescription p = controller.all().get(row);
        int ok = JOptionPane.showConfirmDialog(this, "Delete " + p.id() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            controller.remove(row);
            persistToCsv.run();
            reloadView();
            status.accept("Deleted prescription: " + p.id());
        }
    }

    private void onExport() {
        int row = selectedModelRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a prescription first."); return; }
        Prescription p = controller.all().get(row);
        try {
            File f = controller.exportTxt(p);
            JOptionPane.showMessageDialog(this, "Exported:\n" + f.getPath());
            status.accept("Exported " + p.id());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage());
        }
    }

    public void reloadView() {
        model.fireTableDataChanged();
        status.accept("Prescriptions: " + controller.all().size());
    }

    static class PrescriptionTableModel extends AbstractTableModel {
        private final PrescriptionController controller;
        private final String[] cols = {"ID","Patient ID","Medication","Dosage","Status","Issue date"};
        PrescriptionTableModel(PrescriptionController controller){ this.controller=controller; }
        @Override public int getRowCount(){ return controller.all().size(); }
        @Override public int getColumnCount(){ return cols.length; }
        @Override public String getColumnName(int c){ return cols[c]; }
        @Override public Object getValueAt(int row, int col){
            Prescription p = controller.all().get(row);
            return switch(col){
                case 0 -> p.id();
                case 1 -> p.patientId();
                case 2 -> p.medicationName();
                case 3 -> p.dosage();
                case 4 -> p.status();
                case 5 -> p.issueDate();
                default -> "";
            };
        }
    }
}
