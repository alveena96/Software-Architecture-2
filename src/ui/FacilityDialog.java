package ui;

import domain.Facility;

import javax.swing.*;
import java.awt.*;

public class FacilityDialog extends JDialog {
    private Facility result;

    private final JTextField name = new JTextField(22);
    private final JTextField type = new JTextField(22);
    private final JTextField postcode = new JTextField(22);
    private final JTextField phone = new JTextField(22);
    private final JTextField email = new JTextField(22);
    private final JTextField manager = new JTextField(22);

    public FacilityDialog(Frame owner, String titleText, Facility seed) {
        super(owner, titleText, true);

        name.setText(seed.name());
        type.setText(seed.type());
        postcode.setText(seed.postcode());
        phone.setText(seed.phone());
        email.setText(seed.email());
        manager.setText(seed.managerName());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        int r=0;
        row(form,gc,r++,"Facility name*",name);
        row(form,gc,r++,"Facility type",type);
        row(form,gc,r++,"Postcode",postcode);
        row(form,gc,r++,"Phone",phone);
        row(form,gc,r++,"Email",email);
        row(form,gc,r++,"Manager",manager);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        save.addActionListener(e -> {
            if (name.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Facility name is required.");
                return;
            }
            result = new Facility(
                seed.id(),
                name.getText().trim(),
                type.getText().trim(),
                seed.address(),
                postcode.getText().trim(),
                phone.getText().trim(),
                email.getText().trim(),
                seed.openingHours(),
                manager.getText().trim(),
                seed.capacity(),
                seed.specialitiesOffered()
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

    public Facility result(){ return result; }
}
