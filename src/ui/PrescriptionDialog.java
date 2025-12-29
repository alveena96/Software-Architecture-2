package ui;

import domain.Prescription;
import persistence.AppRepository;

import javax.swing.*;
import java.awt.*;

public class PrescriptionDialog extends JDialog {
    private Prescription result;

    private final JTextField patientId = new JTextField(18);
    private final JTextField medication = new JTextField(18);
    private final JTextField dosage = new JTextField(18);
    private final JTextField frequency = new JTextField(18);
    private final JTextField duration = new JTextField(18);
    private final JTextField instructions = new JTextField(18);
    private final JTextField pharmacy = new JTextField(18);
    private final JTextField status = new JTextField(18);

    public PrescriptionDialog(Frame owner, String title, Prescription seed) {
        super(owner, title, true);

        patientId.setText(seed.patientId());
        medication.setText(seed.medicationName());
        dosage.setText(seed.dosage());
        frequency.setText(seed.frequency());
        duration.setText(seed.durationDays());
        instructions.setText(seed.instructions());
        pharmacy.setText(seed.pharmacyName());
        status.setText(seed.status());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        int r=0;
        row(form,gc,r++,"Patient ID*",patientId);
        row(form,gc,r++,"Medication*",medication);
        row(form,gc,r++,"Dosage",dosage);
        row(form,gc,r++,"Frequency",frequency);
        row(form,gc,r++,"Duration (days)",duration);
        row(form,gc,r++,"Instructions",instructions);
        row(form,gc,r++,"Pharmacy",pharmacy);
        row(form,gc,r++,"Status",status);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        save.addActionListener(e -> {
            if (patientId.getText().trim().isEmpty() || medication.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Patient ID and Medication are required.");
                return;
            }
            String today = AppRepository.today();
            result = new Prescription(
                seed.id(),
                patientId.getText().trim(),
                seed.clinicianId(),
                seed.appointmentId(),
                seed.prescriptionDate().isEmpty() ? today : seed.prescriptionDate(),
                medication.getText().trim(),
                dosage.getText().trim(),
                frequency.getText().trim(),
                duration.getText().trim(),
                seed.quantity(),
                instructions.getText().trim(),
                pharmacy.getText().trim(),
                status.getText().trim(),
                seed.issueDate().isEmpty() ? today : seed.issueDate(),
                seed.collectionDate()
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

    public Prescription result(){ return result; }
}
