package controller;

import domain.Appointment;
import persistence.AppRepository;

import java.util.List;

public class AppointmentController {
    private final AppRepository repo = AppRepository.get();
    public List<Appointment> all(){ return repo.appointments(); }

    public Appointment blank() {
        String id = repo.nextAppointmentId();
        String today = AppRepository.today();
        return new Appointment(id,"","","",today,"","15","Consultation","Scheduled","","",today,today);
    }
    public void add(Appointment a){ repo.appointments().add(a); }
    public void set(int idx, Appointment a){ repo.appointments().set(idx,a); }
    public void remove(int idx){ repo.appointments().remove(idx); }
}
