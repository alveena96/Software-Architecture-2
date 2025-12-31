package controller;

import domain.Patient;
import domain.Prescription;
import persistence.AppRepository;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class PrescriptionController {
    private final AppRepository repo = AppRepository.get();
    public List<Prescription> all(){ return repo.prescriptions(); }

    public Prescription blank() {
        String id = repo.nextPrescriptionId();
        String today = AppRepository.today();
        return new Prescription(id,"","","",today,"","","","","","","","Draft",today,"");
    }

    public void add(Prescription p){ repo.prescriptions().add(p); }
    public void set(int idx, Prescription p){ repo.prescriptions().set(idx,p); }
    public void remove(int idx){ repo.prescriptions().remove(idx); }

    public File exportTxt(Prescription pr) throws Exception {
        Patient pt = repo.patients().stream().filter(p -> p.id().equals(pr.patientId())).findFirst().orElse(null);
        if (pt == null) throw new IllegalArgumentException("Patient not found: " + pr.patientId());

        File dir = new File("output/prescriptions");
        dir.mkdirs();
        File f = new File(dir, "prescription_" + pr.id() + ".txt");

        try (FileWriter fw = new FileWriter(f)) {
            fw.write("PRESCRIPTION\n");
            fw.write("ID: " + pr.id() + "\n");
            fw.write("Patient: " + pt.firstName() + " " + pt.lastName() + " (" + pt.id() + ")\n");
            fw.write("Medication: " + pr.medicationName() + "\n");
            fw.write("Dosage: " + pr.dosage() + "\n");
            fw.write("Frequency: " + pr.frequency() + "\n");
            fw.write("Duration: " + pr.durationDays() + " days\n");
            fw.write("Instructions: " + pr.instructions() + "\n");
            fw.write("Pharmacy: " + pr.pharmacyName() + "\n");
            fw.write("Status: " + pr.status() + "\n");
        }
        return f;
    }
}
