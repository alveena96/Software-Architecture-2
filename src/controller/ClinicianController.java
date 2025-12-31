package controller;

import domain.Clinician;
import persistence.AppRepository;

import java.util.List;

public class ClinicianController {
    private final AppRepository repo = AppRepository.get();
    public List<Clinician> all(){ return repo.clinicians(); }

    public Clinician blank() {
        String id = repo.nextClinicianId();
        return new Clinician(id,"","","","", "", "", "", "", "", "", AppRepository.today());
    }

    public void add(Clinician c){ repo.clinicians().add(c); }
    public void set(int idx, Clinician c){ repo.clinicians().set(idx,c); }
    public void remove(int idx){ repo.clinicians().remove(idx); }
}
