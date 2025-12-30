package domain;

public class Prescription {
    private String id, patientId, clinicianId, appointmentId, prescriptionDate, medicationName, dosage, frequency,
            durationDays, quantity, instructions, pharmacyName, status, issueDate, collectionDate;

    public Prescription(String id,String patientId,String clinicianId,String appointmentId,String prescriptionDate,String medicationName,
                        String dosage,String frequency,String durationDays,String quantity,String instructions,String pharmacyName,
                        String status,String issueDate,String collectionDate){
        this.id=id; this.patientId=patientId; this.clinicianId=clinicianId; this.appointmentId=appointmentId;
        this.prescriptionDate=prescriptionDate; this.medicationName=medicationName; this.dosage=dosage; this.frequency=frequency;
        this.durationDays=durationDays; this.quantity=quantity; this.instructions=instructions; this.pharmacyName=pharmacyName;
        this.status=status; this.issueDate=issueDate; this.collectionDate=collectionDate;
    }
    public String id(){return id;} public void setId(String v){id=v;}
    public String patientId(){return patientId;} public void setPatientId(String v){patientId=v;}
    public String clinicianId(){return clinicianId;} public void setClinicianId(String v){clinicianId=v;}
    public String appointmentId(){return appointmentId;} public void setAppointmentId(String v){appointmentId=v;}
    public String prescriptionDate(){return prescriptionDate;} public void setPrescriptionDate(String v){prescriptionDate=v;}
    public String medicationName(){return medicationName;} public void setMedicationName(String v){medicationName=v;}
    public String dosage(){return dosage;} public void setDosage(String v){dosage=v;}
    public String frequency(){return frequency;} public void setFrequency(String v){frequency=v;}
    public String durationDays(){return durationDays;} public void setDurationDays(String v){durationDays=v;}
    public String quantity(){return quantity;} public void setQuantity(String v){quantity=v;}
    public String instructions(){return instructions;} public void setInstructions(String v){instructions=v;}
    public String pharmacyName(){return pharmacyName;} public void setPharmacyName(String v){pharmacyName=v;}
    public String status(){return status;} public void setStatus(String v){status=v;}
    public String issueDate(){return issueDate;} public void setIssueDate(String v){issueDate=v;}
    public String collectionDate(){return collectionDate;} public void setCollectionDate(String v){collectionDate=v;}
}
