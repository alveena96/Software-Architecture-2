package ui;

import controller.*;
import persistence.AppRepository;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final JLabel status = new JLabel("Ready");
    private final AppRepository repo = AppRepository.get();

    private final PatientPanel patientPanel;
    private final ClinicianPanel clinicianPanel;
    private final FacilityPanel facilityPanel;
    private final StaffPanel staffPanel;
    private final AppointmentPanel appointmentPanel;
    private final PrescriptionPanel prescriptionPanel;
    private final ReferralPanel referralPanel;

    public MainWindow() {
        super("Clinic MVC Application");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(topBar(), BorderLayout.NORTH);

        Runnable persistToCsv = this::safeSave;
        Runnable reloadFromCsv = this::safeReload;

        patientPanel = new PatientPanel(new PatientController(), this::setStatus, persistToCsv, reloadFromCsv);
        clinicianPanel = new ClinicianPanel(new ClinicianController(), this::setStatus, persistToCsv, reloadFromCsv);
        facilityPanel = new FacilityPanel(new FacilityController(), this::setStatus, persistToCsv, reloadFromCsv);
        staffPanel = new StaffPanel(new StaffController(), this::setStatus, persistToCsv, reloadFromCsv);

        appointmentPanel = new AppointmentPanel(new AppointmentController(), this::setStatus, persistToCsv, reloadFromCsv);
        prescriptionPanel = new PrescriptionPanel(new PrescriptionController(), this::setStatus, persistToCsv, reloadFromCsv);
        referralPanel = new ReferralPanel(new ReferralController(), this::setStatus, persistToCsv, reloadFromCsv);

        JTabbedPane tabs = new JTabbedPane(JTabbedPane.LEFT);
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        tabs.addTab("Patients", patientPanel);
        tabs.addTab("Clinicians", clinicianPanel);
        tabs.addTab("Facilities", facilityPanel);
        tabs.addTab("Staff", staffPanel);
        tabs.addTab("Appointments", appointmentPanel);
        tabs.addTab("Prescriptions", prescriptionPanel);
        tabs.addTab("Referrals", referralPanel);

        add(Ui.padded(tabs), BorderLayout.CENTER);

        JPanel bar = new JPanel(new BorderLayout());
        bar.setBorder(BorderFactory.createEmptyBorder(6,12,6,12));
        bar.add(status, BorderLayout.WEST);
        add(bar, BorderLayout.SOUTH);

        refreshAll();
    }

    private JPanel topBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(10,12,10,12));

        JLabel title = new JLabel("Clinic MVC");
        title.setFont(title.getFont().deriveFont(Font.BOLD, title.getFont().getSize()+6f));

        JLabel subtitle = new JLabel("A Complete Healthcare Management Solution.");
        subtitle.setFont(subtitle.getFont().deriveFont(subtitle.getFont().getSize()-1f));

        JPanel left = new JPanel(new GridLayout(2,1));
        left.setOpaque(false);
        left.add(title);
        left.add(subtitle);

        JButton about = new JButton("About");
        JButton exit = new JButton("Exit");
        about.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Clinic MVC\n\nAuto-save behavior:\n- Add/Edit/Delete immediately saves to /data CSV\n- Refresh buttons reload from /data\n\nExports:\n- /output/prescriptions\n- /output/referrals",
                "About", JOptionPane.INFORMATION_MESSAGE));
        exit.addActionListener(e -> dispose());

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));
        right.setOpaque(false);
        right.add(about);
        right.add(exit);

        p.add(left, BorderLayout.WEST);
        p.add(right, BorderLayout.EAST);
        return p;
    }

    private void safeSave() {
        try {
            repo.save("data");
            setStatus("Saved to CSV.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Save to CSV failed: " + ex.getMessage());
            setStatus("Save failed (see message).");
        }
    }

    private void safeReload() {
        int ok = JOptionPane.showConfirmDialog(this,
                "Reload will discard unsaved in-memory changes (if any). Continue?",
                "Reload CSV", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;

        try {
            repo.load("data");
            refreshAll();
            setStatus("Reloaded from CSV.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Reload failed: " + ex.getMessage());
            setStatus("Reload failed (see message).");
        }
    }

    private void refreshAll() {
        patientPanel.reloadView();
        clinicianPanel.reloadView();
        facilityPanel.reloadView();
        staffPanel.reloadView();
        appointmentPanel.reloadView();
        prescriptionPanel.reloadView();
        referralPanel.reloadView();

        setStatus(String.format(
                "Loaded: %d patients | %d clinicians | %d facilities | %d staff | %d appts | %d prescriptions | %d referrals",
                repo.patients().size(), repo.clinicians().size(), repo.facilities().size(), repo.staff().size(),
                repo.appointments().size(), repo.prescriptions().size(), repo.referrals().size()));
    }

    private void setStatus(String s) { status.setText(s); }
}
