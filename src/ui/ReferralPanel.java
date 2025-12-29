package ui;

import controller.ReferralController;
import domain.Referral;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.util.function.Consumer;

public class ReferralPanel extends JPanel {
    private final ReferralController controller;
    private final Consumer<String> status;
    private final Runnable persistToCsv;
    private final Runnable reloadFromCsv;

    private final ReferralTableModel model;
    private final JTable table;
    private final JTextField search = new JTextField(22);

    public ReferralPanel(ReferralController controller, Consumer<String> status, Runnable persistToCsv, Runnable reloadFromCsv) {
        super(new BorderLayout(10,10));
        this.controller = controller;
        this.status = status;
        this.persistToCsv = persistToCsv;
        this.reloadFromCsv = reloadFromCsv;

        model = new ReferralTableModel(controller);
        table = new JTable(model);
        Ui.style(table);

        TableRowSorter<ReferralTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JPanel header = new JPanel(new BorderLayout(10,10));
        JLabel title = new JLabel("Referrals");
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
        JButton export = new JButton("Export Letter");
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
        Referral seed = controller.blank();
        ReferralDialog dlg = new ReferralDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Referral", seed);
        dlg.setVisible(true);
        Referral r = dlg.result();
        if (r != null) {
            controller.add(r);
            persistToCsv.run();
            reloadView();
            status.accept("Saved new referral: " + r.id());
        }
    }

    private void onEdit() {
        int row = selectedModelRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a referral first."); return; }
        Referral existing = controller.all().get(row);
        ReferralDialog dlg = new ReferralDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Referral", existing);
        dlg.setVisible(true);
        Referral r = dlg.result();
        if (r != null) {
            controller.set(row, r);
            persistToCsv.run();
            reloadView();
            status.accept("Saved referral changes: " + r.id());
        }
    }

    private void onDelete() {
        int row = selectedModelRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a referral first."); return; }
        Referral r = controller.all().get(row);
        int ok = JOptionPane.showConfirmDialog(this, "Delete " + r.id() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            controller.remove(row);
            persistToCsv.run();
            reloadView();
            status.accept("Deleted referral: " + r.id());
        }
    }

    private void onExport() {
        int row = selectedModelRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a referral first."); return; }
        Referral r = controller.all().get(row);
        try {
            File f = controller.exportLetter(r);
            JOptionPane.showMessageDialog(this, "Exported:\n" + f.getPath());
            status.accept("Exported " + r.id());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage());
        }
    }

    public void reloadView() {
        model.fireTableDataChanged();
        status.accept("Referrals: " + controller.all().size());
    }

    static class ReferralTableModel extends AbstractTableModel {
        private final ReferralController controller;
        private final String[] cols = {"ID","Patient ID","Urgency","Status","Date","Reason"};
        ReferralTableModel(ReferralController controller){ this.controller=controller; }
        @Override public int getRowCount(){ return controller.all().size(); }
        @Override public int getColumnCount(){ return cols.length; }
        @Override public String getColumnName(int c){ return cols[c]; }
        @Override public Object getValueAt(int row, int col){
            Referral r = controller.all().get(row);
            return switch(col){
                case 0 -> r.id();
                case 1 -> r.patientId();
                case 2 -> r.urgency();
                case 3 -> r.status();
                case 4 -> r.referralDate();
                case 5 -> r.reason();
                default -> "";
            };
        }
    }
}
