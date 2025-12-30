package persistence;

import domain.*;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * Singleton repository: one shared source of truth (in-memory lists) + CSV persistence.
 * Controllers mutate lists; UI triggers save/reload.
 */
public final class AppRepository {
    private static AppRepository instance;
    public static synchronized AppRepository get() {
        if (instance == null) instance = new AppRepository();
        return instance;
    }
    private AppRepository() {}

    private final List<Patient> patients = new ArrayList<>();
    private final List<Clinician> clinicians = new ArrayList<>();
    private final List<Facility> facilities = new ArrayList<>();
    private final List<Staff> staff = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();
    private final List<Prescription> prescriptions = new ArrayList<>();
    private final List<Referral> referrals = new ArrayList<>();

    public List<Patient> patients(){ return patients; }
    public List<Clinician> clinicians(){ return clinicians; }
    public List<Facility> facilities(){ return facilities; }
    public List<Staff> staff(){ return staff; }
    public List<Appointment> appointments(){ return appointments; }
    public List<Prescription> prescriptions(){ return prescriptions; }
    public List<Referral> referrals(){ return referrals; }

    public void clearAll() {
        patients.clear(); clinicians.clear(); facilities.clear(); staff.clear();
        appointments.clear(); prescriptions.clear(); referrals.clear();
    }

    public void load(String folder) throws Exception {
        clearAll();

        for (String[] a : CsvUtil.read(new File(folder,"patients.csv"), true)) {
            if (a.length >= 14) patients.add(new Patient(a[0],a[1],a[2],a[3],a[4],a[5],a[6],a[7],a[8],a[9],a[10],a[11],a[12],a[13]));
        }
        for (String[] a : CsvUtil.read(new File(folder,"clinicians.csv"), true)) {
            if (a.length >= 12) clinicians.add(new Clinician(a[0],a[1],a[2],a[3],a[4],a[5],a[6],a[7],a[8],a[9],a[10],a[11]));
        }
        for (String[] a : CsvUtil.read(new File(folder,"facilities.csv"), true)) {
            if (a.length >= 11) facilities.add(new Facility(a[0],a[1],a[2],a[3],a[4],a[5],a[6],a[7],a[8],a[9],a[10]));
        }
        for (String[] a : CsvUtil.read(new File(folder,"staff.csv"), true)) {
            if (a.length >= 12) staff.add(new Staff(a[0],a[1],a[2],a[3],a[4],a[5],a[6],a[7],a[8],a[9],a[10],a[11]));
        }
        for (String[] a : CsvUtil.read(new File(folder,"appointments.csv"), true)) {
            if (a.length >= 13) appointments.add(new Appointment(a[0],a[1],a[2],a[3],a[4],a[5],a[6],a[7],a[8],a[9],a[10],a[11],a[12]));
        }
        for (String[] a : CsvUtil.read(new File(folder,"prescriptions.csv"), true)) {
            if (a.length >= 15) prescriptions.add(new Prescription(a[0],a[1],a[2],a[3],a[4],a[5],a[6],a[7],a[8],a[9],a[10],a[11],a[12],a[13],a[14]));
        }
        for (String[] a : CsvUtil.read(new File(folder,"referrals.csv"), true)) {
            if (a.length >= 16) referrals.add(new Referral(a[0],a[1],a[2],a[3],a[4],a[5],a[6],a[7],a[8],a[9],a[10],a[11],a[12],a[13],a[14],a[15]));
        }
    }

    public void save(String folder) throws Exception {
        CsvUtil.write(new File(folder,"patients.csv"), new String[]{
            "patient_id","first_name","last_name","date_of_birth","nhs_number","gender","phone_number","email",
            "address","postcode","emergency_contact_name","emergency_contact_phone","registration_date","gp_surgery_id"
        }, patients.stream().map(p -> new String[]{
            p.id(),p.firstName(),p.lastName(),p.dob(),p.nhs(),p.gender(),p.phone(),p.email(),
            p.address(),p.postcode(),p.emergencyName(),p.emergencyPhone(),p.registrationDate(),p.gpSurgeryId()
        }).toList());

        CsvUtil.write(new File(folder,"clinicians.csv"), new String[]{
            "clinician_id","first_name","last_name","title","speciality","gmc_number","phone_number","email",
            "workplace_id","workplace_type","employment_status","start_date"
        }, clinicians.stream().map(c -> new String[]{
            c.id(),c.firstName(),c.lastName(),c.title(),c.speciality(),c.gmcNumber(),c.phone(),c.email(),
            c.workplaceId(),c.workplaceType(),c.employmentStatus(),c.startDate()
        }).toList());

        CsvUtil.write(new File(folder,"facilities.csv"), new String[]{
            "facility_id","facility_name","facility_type","address","postcode","phone_number","email",
            "opening_hours","manager_name","capacity","specialities_offered"
        }, facilities.stream().map(f -> new String[]{
            f.id(),f.name(),f.type(),f.address(),f.postcode(),f.phone(),f.email(),
            f.openingHours(),f.managerName(),f.capacity(),f.specialitiesOffered()
        }).toList());

        CsvUtil.write(new File(folder,"staff.csv"), new String[]{
            "staff_id","first_name","last_name","role","department","facility_id","phone_number","email",
            "employment_status","start_date","line_manager","access_level"
        }, staff.stream().map(s -> new String[]{
            s.id(),s.firstName(),s.lastName(),s.role(),s.department(),s.facilityId(),s.phone(),s.email(),
            s.employmentStatus(),s.startDate(),s.lineManager(),s.accessLevel()
        }).toList());

        CsvUtil.write(new File(folder,"appointments.csv"), new String[]{
            "appointment_id","patient_id","clinician_id","facility_id","appointment_date","appointment_time","duration_minutes",
            "appointment_type","status","reason_for_visit","notes","created_date","last_modified"
        }, appointments.stream().map(a -> new String[]{
            a.id(),a.patientId(),a.clinicianId(),a.facilityId(),a.date(),a.time(),a.durationMinutes(),
            a.type(),a.status(),a.reason(),a.notes(),a.createdDate(),a.lastModified()
        }).toList());

        CsvUtil.write(new File(folder,"prescriptions.csv"), new String[]{
            "prescription_id","patient_id","clinician_id","appointment_id","prescription_date","medication_name","dosage",
            "frequency","duration_days","quantity","instructions","pharmacy_name","status","issue_date","collection_date"
        }, prescriptions.stream().map(p -> new String[]{
            p.id(),p.patientId(),p.clinicianId(),p.appointmentId(),p.prescriptionDate(),p.medicationName(),p.dosage(),
            p.frequency(),p.durationDays(),p.quantity(),p.instructions(),p.pharmacyName(),p.status(),p.issueDate(),p.collectionDate()
        }).toList());

        CsvUtil.write(new File(folder,"referrals.csv"), new String[]{
            "referral_id","patient_id","referring_clinician_id","referred_to_clinician_id","referring_facility_id",
            "referred_to_facility_id","referral_date","urgency_level","referral_reason","clinical_summary",
            "requested_investigations","status","appointment_id","notes","created_date","last_updated"
        }, referrals.stream().map(r -> new String[]{
            r.id(),r.patientId(),r.fromClinicianId(),r.toClinicianId(),r.fromFacilityId(),r.toFacilityId(),
            r.referralDate(),r.urgency(),r.reason(),r.summary(),r.investigations(),r.status(),
            r.appointmentId(),r.notes(),r.createdDate(),r.lastUpdated()
        }).toList());
    }

    public String nextId(String prefix, List<String> existing) {
        int max=0;
        for (String s: existing) {
            if (s==null) continue;
            String digits = s.replaceAll("\\D+","");
            if (!digits.isEmpty()) {
                try { max = Math.max(max, Integer.parseInt(digits)); } catch (Exception ignored) {}
            }
        }
        return String.format("%s-%04d", prefix, max+1);
    }

    public String nextPatientId(){ return nextId("PAT", patients.stream().map(Patient::id).toList()); }
    public String nextClinicianId(){ return nextId("CLN", clinicians.stream().map(Clinician::id).toList()); }
    public String nextFacilityId(){ return nextId("FAC", facilities.stream().map(Facility::id).toList()); }
    public String nextStaffId(){ return nextId("STF", staff.stream().map(Staff::id).toList()); }
    public String nextAppointmentId(){ return nextId("APT", appointments.stream().map(Appointment::id).toList()); }
    public String nextPrescriptionId(){ return nextId("PRX", prescriptions.stream().map(Prescription::id).toList()); }
    public String nextReferralId(){ return nextId("REF", referrals.stream().map(Referral::id).toList()); }

    public static String today(){ return LocalDate.now().toString(); }
}
