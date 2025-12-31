package controller;

import domain.Patient;
import persistence.AppRepository;

import java.util.List;

public class PatientController {
    private final AppRepository repo = AppRepository.get();
    public List<Patient> all(){ return repo.patients(); }

    public Patient blank() {
        String id = repo.nextPatientId();
        return new Patient(id,"","","","","","","","","","","",AppRepository.today(),"");
    }
    public void add(Patient p){ repo.patients().add(p); }
    public void set(int idx, Patient p){ repo.patients().set(idx,p); }
    public void remove(int idx){ repo.patients().remove(idx); }
}
