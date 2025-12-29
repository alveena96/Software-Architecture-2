package ui;

import domain.Appointment;
import persistence.AppRepository;

import javax.swing.*;
import java.awt.*;

public class AppointmentDialog extends JDialog {
    private Appointment result;

    private final JTextField patientId = new JTextField(18);
    private final JTextField clinicianId = new JTextField(18);
    private final JTextField facilityId = new JTextField(18);
    private final JTextField date = new JTextField(18);
    private final JTextField time = new JTextField(18);
    private final JTextField duration = new JTextField(18);
    private final JTextField status = new JTextField(18);
    private final JTextField reason = new JTextField(18);

    public AppointmentDialog(Frame owner, String title, Appointment seed) {
        super(owner, title, true);

        patientId.setText(seed.patientId());
        clinicianId.setText(seed.clinicianId());
        facilityId.setText(seed.facilityId());
        date.setText(seed.date());
        time.setText(seed.time());
        duration.setText(seed.durationMinutes());
        status.setText(seed.status());
        reason.setText(seed.reason());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        int r=0;
        row(form,gc,r++,"Patient ID*",patientId);
        row(form,gc,r++,"Clinician ID",clinicianId);
        row(form,gc,r++,"Facility ID",facilityId);
        row(form,gc,r++,"Date",date);
        row(form,gc,r++,"Time",time);
        row(form,gc,r++,"Duration",duration);
        row(form,gc,r++,"Status",status);
        row(form,gc,r++,"Reason",reason);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        save.addActionListener(e -> {
            if (patientId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Patient ID is required.");
                return;
            }
            String now = AppRepository.today();
            result = new Appointment(
                seed.id(),
                patientId.getText().trim(),
                clinicianId.getText().trim(),
                facilityId.getText().trim(),
                date.getText().trim(),
                time.getText().trim(),
                duration.getText().trim(),
                seed.type(),
                status.getText().trim(),
                reason.getText().trim(),
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

    public Appointment result(){ return result; }
}
