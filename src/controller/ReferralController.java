package controller;

import domain.Patient;
import domain.Referral;
import persistence.AppRepository;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class ReferralController {
    private final AppRepository repo = AppRepository.get();
    public List<Referral> all(){ return repo.referrals(); }

    public Referral blank() {
        String id = repo.nextReferralId();
        String today = AppRepository.today();
        return new Referral(id,"","","","","",today,"Routine","","","","Draft","","",today,today);
    }

    public void add(Referral r){ repo.referrals().add(r); }
    public void set(int idx, Referral r){ repo.referrals().set(idx,r); }
    public void remove(int idx){ repo.referrals().remove(idx); }

    public File exportLetter(Referral r) throws Exception {
        Patient pt = repo.patients().stream().filter(p -> p.id().equals(r.patientId())).findFirst().orElse(null);
        if (pt == null) throw new IllegalArgumentException("Patient not found: " + r.patientId());

        File dir = new File("output/referrals");
        dir.mkdirs();
        File f = new File(dir, "referral_" + r.id() + ".txt");

        try (FileWriter fw = new FileWriter(f)) {
            fw.write("REFERRAL LETTER\n");
            fw.write("Referral ID: " + r.id() + "\n");
            fw.write("Date: " + r.referralDate() + " | Urgency: " + r.urgency() + "\n\n");
            fw.write("Patient: " + pt.firstName() + " " + pt.lastName() + " (" + pt.id() + ")\n");
            fw.write("DOB: " + pt.dob() + " | NHS: " + pt.nhs() + "\n");
            fw.write("Postcode: " + pt.postcode() + "\n\n");
            fw.write("Reason: " + r.reason() + "\n");
            fw.write("Summary: " + r.summary() + "\n");
            fw.write("Investigations: " + r.investigations() + "\n\n");
            fw.write("Status: " + r.status() + "\n");
        }
        return f;
    }
}
