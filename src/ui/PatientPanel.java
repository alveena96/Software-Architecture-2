package ui;

import controller.PatientController;
import domain.Patient;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.function.Consumer;

public class PatientPanel extends JPanel {
    private final PatientController controller;
    private final Consumer<String> status;
    private final Runnable persistToCsv;
    private final Runnable reloadFromCsv;

    private final PatientTableModel model;
    private final JTable table;
    private final JTextField search = new JTextField(22);

    public PatientPanel(PatientController controller, Consumer<String> status, Runnable persistToCsv, Runnable reloadFromCsv) {
        super(new BorderLayout(10,10));
        this.controller = controller;
        this.status = status;
        this.persistToCsv = persistToCsv;
        this.reloadFromCsv = reloadFromCsv;

        model = new PatientTableModel(controller);
        table = new JTable(model);
        Ui.style(table);

        TableRowSorter<PatientTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JPanel header = new JPanel(new BorderLayout(10,10));
        JLabel title = new JLabel("Patients");
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
        JButton refresh = new JButton("Refresh");
        actions.add(edit); actions.add(del); actions.add(refresh);

        add.addActionListener(e -> onAdd());
        edit.addActionListener(e -> onEdit());
        del.addActionListener(e -> onDelete());
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
        Patient seed = controller.blank();
        PatientDialog dlg = new PatientDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Patient", seed);
        dlg.setVisible(true);
        Patient r = dlg.result();
        if (r != null) {
            controller.add(r);
            persistToCsv.run();
            reloadView();
            status.accept("Saved new patient: " + r.id());
        }
    }

    private void onEdit() {
        int row = selectedModelRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a patient first."); return; }
        Patient existing = controller.all().get(row);
        PatientDialog dlg = new PatientDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Patient", existing);
        dlg.setVisible(true);
        Patient r = dlg.result();
        if (r != null) {
            controller.set(row, r);
            persistToCsv.run();
            reloadView();
            status.accept("Saved patient changes: " + r.id());
        }
    }

    private void onDelete() {
        int row = selectedModelRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a patient first."); return; }
        Patient p = controller.all().get(row);
        int ok = JOptionPane.showConfirmDialog(this, "Delete " + p.id() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            controller.remove(row);
            persistToCsv.run();
            reloadView();
            status.accept("Deleted patient: " + p.id());
        }
    }

    public void reloadView() {
        model.fireTableDataChanged();
        status.accept("Patients: " + controller.all().size());
    }

    static class PatientTableModel extends AbstractTableModel {
        private final PatientController controller;
        private final String[] cols = {"ID","First name","Last name","DOB","NHS","Gender","Phone","Email","Postcode"};
        PatientTableModel(PatientController controller){ this.controller=controller; }
        @Override public int getRowCount(){ return controller.all().size(); }
        @Override public int getColumnCount(){ return cols.length; }
        @Override public String getColumnName(int c){ return cols[c]; }
        @Override public Object getValueAt(int row, int col){
            Patient p = controller.all().get(row);
            return switch(col){
                case 0 -> p.id();
                case 1 -> p.firstName();
                case 2 -> p.lastName();
                case 3 -> p.dob();
                case 4 -> p.nhs();
                case 5 -> p.gender();
                case 6 -> p.phone();
                case 7 -> p.email();
                case 8 -> p.postcode();
                default -> "";
            };
        }
    }
}
