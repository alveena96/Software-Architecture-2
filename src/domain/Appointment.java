package domain;

public class Appointment {
    private String id, patientId, clinicianId, facilityId, date, time, durationMinutes, type, status, reason, notes, createdDate, lastModified;
    public Appointment(String id,String patientId,String clinicianId,String facilityId,String date,String time,String durationMinutes,
                       String type,String status,String reason,String notes,String createdDate,String lastModified){
        this.id=id; this.patientId=patientId; this.clinicianId=clinicianId; this.facilityId=facilityId; this.date=date; this.time=time;
        this.durationMinutes=durationMinutes; this.type=type; this.status=status; this.reason=reason; this.notes=notes;
        this.createdDate=createdDate; this.lastModified=lastModified;
    }
    public String id(){return id;} public void setId(String v){id=v;}
    public String patientId(){return patientId;} public void setPatientId(String v){patientId=v;}
    public String clinicianId(){return clinicianId;} public void setClinicianId(String v){clinicianId=v;}
    public String facilityId(){return facilityId;} public void setFacilityId(String v){facilityId=v;}
    public String date(){return date;} public void setDate(String v){date=v;}
    public String time(){return time;} public void setTime(String v){time=v;}
    public String durationMinutes(){return durationMinutes;} public void setDurationMinutes(String v){durationMinutes=v;}
    public String type(){return type;} public void setType(String v){type=v;}
    public String status(){return status;} public void setStatus(String v){status=v;}
    public String reason(){return reason;} public void setReason(String v){reason=v;}
    public String notes(){return notes;} public void setNotes(String v){notes=v;}
    public String createdDate(){return createdDate;} public void setCreatedDate(String v){createdDate=v;}
    public String lastModified(){return lastModified;} public void setLastModified(String v){lastModified=v;}
}
