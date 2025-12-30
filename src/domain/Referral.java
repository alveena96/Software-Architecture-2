package domain;

public class Referral {
    private String id, patientId, fromClinicianId, toClinicianId, fromFacilityId, toFacilityId, referralDate,
            urgency, reason, summary, investigations, status, appointmentId, notes, createdDate, lastUpdated;

    public Referral(String id,String patientId,String fromClinicianId,String toClinicianId,String fromFacilityId,String toFacilityId,
                    String referralDate,String urgency,String reason,String summary,String investigations,String status,
                    String appointmentId,String notes,String createdDate,String lastUpdated){
        this.id=id; this.patientId=patientId; this.fromClinicianId=fromClinicianId; this.toClinicianId=toClinicianId;
        this.fromFacilityId=fromFacilityId; this.toFacilityId=toFacilityId; this.referralDate=referralDate;
        this.urgency=urgency; this.reason=reason; this.summary=summary; this.investigations=investigations; this.status=status;
        this.appointmentId=appointmentId; this.notes=notes; this.createdDate=createdDate; this.lastUpdated=lastUpdated;
    }
    public String id(){return id;} public void setId(String v){id=v;}
    public String patientId(){return patientId;} public void setPatientId(String v){patientId=v;}
    public String fromClinicianId(){return fromClinicianId;} public void setFromClinicianId(String v){fromClinicianId=v;}
    public String toClinicianId(){return toClinicianId;} public void setToClinicianId(String v){toClinicianId=v;}
    public String fromFacilityId(){return fromFacilityId;} public void setFromFacilityId(String v){fromFacilityId=v;}
    public String toFacilityId(){return toFacilityId;} public void setToFacilityId(String v){toFacilityId=v;}
    public String referralDate(){return referralDate;} public void setReferralDate(String v){referralDate=v;}
    public String urgency(){return urgency;} public void setUrgency(String v){urgency=v;}
    public String reason(){return reason;} public void setReason(String v){reason=v;}
    public String summary(){return summary;} public void setSummary(String v){summary=v;}
    public String investigations(){return investigations;} public void setInvestigations(String v){investigations=v;}
    public String status(){return status;} public void setStatus(String v){status=v;}
    public String appointmentId(){return appointmentId;} public void setAppointmentId(String v){appointmentId=v;}
    public String notes(){return notes;} public void setNotes(String v){notes=v;}
    public String createdDate(){return createdDate;} public void setCreatedDate(String v){createdDate=v;}
    public String lastUpdated(){return lastUpdated;} public void setLastUpdated(String v){lastUpdated=v;}
}
