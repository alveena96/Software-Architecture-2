package controller;

import domain.Staff;
import persistence.AppRepository;

import java.util.List;

public class StaffController {
    private final AppRepository repo = AppRepository.get();
    public List<Staff> all(){ return repo.staff(); }

    public Staff blank() {
        String id = repo.nextStaffId();
        return new Staff(id,"","","","","","","","", AppRepository.today(),"","");
    }

    public void add(Staff s){ repo.staff().add(s); }
    public void set(int idx, Staff s){ repo.staff().set(idx,s); }
    public void remove(int idx){ repo.staff().remove(idx); }
}
