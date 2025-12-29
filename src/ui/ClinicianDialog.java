package ui;

import domain.Clinician;
import persistence.AppRepository;

import javax.swing.*;
import java.awt.*;

public class ClinicianDialog extends JDialog {
    private Clinician result;

    private final JTextField first = new JTextField(18);
    private final JTextField last = new JTextField(18);
    private final JTextField title = new JTextField(18);
    private final JTextField speciality = new JTextField(18);
    private final JTextField phone = new JTextField(18);
    private final JTextField email = new JTextField(18);
    private final JTextField status = new JTextField(18);

    public ClinicianDialog(Frame owner, String titleText, Clinician seed) {
        super(owner, titleText, true);

        first.setText(seed.firstName());
        last.setText(seed.lastName());
        title.setText(seed.title());
        speciality.setText(seed.speciality());
        phone.setText(seed.phone());
        email.setText(seed.email());
        status.setText(seed.employmentStatus());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        int r=0;
        row(form,gc,r++,"First name*",first);
        row(form,gc,r++,"Last name*",last);
        row(form,gc,r++,"Title",title);
        row(form,gc,r++,"Speciality",speciality);
        row(form,gc,r++,"Phone",phone);
        row(form,gc,r++,"Email",email);
        row(form,gc,r++,"Employment status",status);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        save.addActionListener(e -> {
            if (first.getText().trim().isEmpty() || last.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "First name and last name are required.");
                return;
            }
            result = new Clinician(
                seed.id(),
                first.getText().trim(),
                last.getText().trim(),
                title.getText().trim(),
                speciality.getText().trim(),
                seed.gmcNumber(),
                phone.getText().trim(),
                email.getText().trim(),
                seed.workplaceId(),
                seed.workplaceType(),
                status.getText().trim(),
                seed.startDate().isEmpty() ? AppRepository.today() : seed.startDate()
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

    public Clinician result(){ return result; }
}
