package ui;

import domain.Staff;
import persistence.AppRepository;

import javax.swing.*;
import java.awt.*;

public class StaffDialog extends JDialog {
    private Staff result;

    private final JTextField first = new JTextField(18);
    private final JTextField last = new JTextField(18);
    private final JTextField role = new JTextField(18);
    private final JTextField dept = new JTextField(18);
    private final JTextField facilityId = new JTextField(18);
    private final JTextField phone = new JTextField(18);
    private final JTextField email = new JTextField(18);
    private final JTextField access = new JTextField(18);

    public StaffDialog(Frame owner, String titleText, Staff seed) {
        super(owner, titleText, true);

        first.setText(seed.firstName());
        last.setText(seed.lastName());
        role.setText(seed.role());
        dept.setText(seed.department());
        facilityId.setText(seed.facilityId());
        phone.setText(seed.phone());
        email.setText(seed.email());
        access.setText(seed.accessLevel());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        int r=0;
        row(form,gc,r++,"First name*",first);
        row(form,gc,r++,"Last name*",last);
        row(form,gc,r++,"Role*",role);
        row(form,gc,r++,"Department",dept);
        row(form,gc,r++,"Facility ID",facilityId);
        row(form,gc,r++,"Phone",phone);
        row(form,gc,r++,"Email",email);
        row(form,gc,r++,"Access level",access);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        save.addActionListener(e -> {
            if (first.getText().trim().isEmpty() || last.getText().trim().isEmpty() || role.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "First, last, and role are required.");
                return;
            }
            result = new Staff(
                seed.id(),
                first.getText().trim(),
                last.getText().trim(),
                role.getText().trim(),
                dept.getText().trim(),
                facilityId.getText().trim(),
                phone.getText().trim(),
                email.getText().trim(),
                seed.employmentStatus(),
                seed.startDate().isEmpty() ? AppRepository.today() : seed.startDate(),
                seed.lineManager(),
                access.getText().trim()
            );
            dispose();
        });

        cancel.addActionListener(e -> dispose());

        gc.gridx=0; gc.gridy=r; gc.weightx=0; form.add(save,gc);
        gc.gridx=1; form.add(cancel,gc);

        setContentPane(form);
        pack();
        setLocationRelativeTo(owner);
    }

    private void row(JPanel p, GridBagConstraints gc, int r, String label, JTextField field) {
        gc.gridx=0; gc.gridy=r; gc.weightx=0; p.add(new JLabel(label), gc);
        gc.gridx=1; gc.weightx=1; p.add(field, gc);
    }

    public Staff result(){ return result; }
}
