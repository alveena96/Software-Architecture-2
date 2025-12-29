package ui;

import domain.Patient;

import javax.swing.*;
import java.awt.*;

public class PatientDialog extends JDialog {
    private Patient result;

    private final JTextField first = new JTextField(18);
    private final JTextField last = new JTextField(18);
    private final JTextField dob = new JTextField(18);
    private final JTextField nhs = new JTextField(18);
    private final JTextField gender = new JTextField(18);
    private final JTextField phone = new JTextField(18);
    private final JTextField email = new JTextField(18);
    private final JTextField postcode = new JTextField(18);

    public PatientDialog(Frame owner, String title, Patient seed) {
        super(owner, title, true);

        first.setText(seed.firstName());
        last.setText(seed.lastName());
        dob.setText(seed.dob());
        nhs.setText(seed.nhs());
        gender.setText(seed.gender());
        phone.setText(seed.phone());
        email.setText(seed.email());
        postcode.setText(seed.postcode());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        int r=0;
        row(form,gc,r++,"First name*",first);
        row(form,gc,r++,"Last name*",last);
        row(form,gc,r++,"DOB",dob);
        row(form,gc,r++,"NHS",nhs);
        row(form,gc,r++,"Gender",gender);
        row(form,gc,r++,"Phone",phone);
        row(form,gc,r++,"Email",email);
        row(form,gc,r++,"Postcode",postcode);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        save.addActionListener(e -> {
            if (first.getText().trim().isEmpty() || last.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "First + last name required.");
                return;
            }
            result = new Patient(
                seed.id(),
                first.getText().trim(), last.getText().trim(),
                dob.getText().trim(), nhs.getText().trim(), gender.getText().trim(),
                phone.getText().trim(), email.getText().trim(),
                seed.address(), postcode.getText().trim(),
                seed.emergencyName(), seed.emergencyPhone(),
                seed.registrationDate(), seed.gpSurgeryId()
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

    private void row(JPanel p, GridBagConstraints gc, int r, String label, JTextField f) {
        gc.gridx=0; gc.gridy=r; gc.weightx=0; p.add(new JLabel(label),gc);
        gc.gridx=1; gc.weightx=1; p.add(f,gc);
    }

    public Patient result(){ return result; }
}
