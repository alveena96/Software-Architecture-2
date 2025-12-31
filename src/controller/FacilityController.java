package controller;

import domain.Facility;
import persistence.AppRepository;

import java.util.List;

public class FacilityController {
    private final AppRepository repo = AppRepository.get();
    public List<Facility> all(){ return repo.facilities(); }

    public Facility blank() {
        String id = repo.nextFacilityId();
        return new Facility(id,"","","","","","","","","","");
    }

    public void add(Facility f){ repo.facilities().add(f); }
    public void set(int idx, Facility f){ repo.facilities().set(idx,f); }
    public void remove(int idx){ repo.facilities().remove(idx); }
}
