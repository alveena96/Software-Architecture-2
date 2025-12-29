package ui;

import domain.Referral;
import persistence.AppRepository;

import javax.swing.*;
import java.awt.*;

public class ReferralDialog extends JDialog {
    private Referral result;

    private final JTextField patientId = new JTextField(18);
    private final JTextField urgency = new JTextField(18);
    private final JTextField reason = new JTextField(18);
    private final JTextField summary = new JTextField(18);
    private final JTextField investigations = new JTextField(18);
    private final JTextField status = new JTextField(18);

    public ReferralDialog(Frame owner, String title, Referral seed) {
        super(owner, title, true);

        patientId.setText(seed.patientId());
        urgency.setText(seed.urgency());
        reason.setText(seed.reason());
        summary.setText(seed.summary());
        investigations.setText(seed.investigations());
        status.setText(seed.status());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        int r=0;
        row(form,gc,r++,"Patient ID*",patientId);
        row(form,gc,r++,"Urgency",urgency);
        row(form,gc,r++,"Reason*",reason);
        row(form,gc,r++,"Summary",summary);
        row(form,gc,r++,"Investigations",investigations);
        row(form,gc,r++,"Status",status);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        save.addActionListener(e -> {
            if (patientId.getText().trim().isEmpty() || reason.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Patient ID + Reason are required.");
                return;
            }
            String now = AppRepository.today();
            result = new Referral(
                seed.id(),
                patientId.getText().trim(),
                seed.fromClinicianId(),
                seed.toClinicianId(),
                seed.fromFacilityId(),
                seed.toFacilityId(),
                seed.referralDate().isEmpty() ? now : seed.referralDate(),
                urgency.getText().trim(),
                reason.getText().trim(),
                summary.getText().trim(),
                investigations.getText().trim(),
                status.getText().trim(),
                seed.appointmentId(),
                seed.notes(),
                seed.createdDate().isEmpty() ? now : seed.createdDate(),
                now
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

    public Referral result(){ return result; }
}
