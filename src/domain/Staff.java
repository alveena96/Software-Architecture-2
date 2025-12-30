package domain;

public class Staff {
    private String id, firstName, lastName, role, department, facilityId, phone, email,
            employmentStatus, startDate, lineManager, accessLevel;

    public Staff(String id, String firstName, String lastName, String role, String department, String facilityId,
                 String phone, String email, String employmentStatus, String startDate, String lineManager, String accessLevel) {
        this.id=id; this.firstName=firstName; this.lastName=lastName; this.role=role; this.department=department; this.facilityId=facilityId;
        this.phone=phone; this.email=email; this.employmentStatus=employmentStatus; this.startDate=startDate; this.lineManager=lineManager; this.accessLevel=accessLevel;
    }

    public String id(){return id;} public void setId(String v){id=v;}
    public String firstName(){return firstName;} public void setFirstName(String v){firstName=v;}
    public String lastName(){return lastName;} public void setLastName(String v){lastName=v;}
    public String role(){return role;} public void setRole(String v){role=v;}
    public String department(){return department;} public void setDepartment(String v){department=v;}
    public String facilityId(){return facilityId;} public void setFacilityId(String v){facilityId=v;}
    public String phone(){return phone;} public void setPhone(String v){phone=v;}
    public String email(){return email;} public void setEmail(String v){email=v;}
    public String employmentStatus(){return employmentStatus;} public void setEmploymentStatus(String v){employmentStatus=v;}
    public String startDate(){return startDate;} public void setStartDate(String v){startDate=v;}
    public String lineManager(){return lineManager;} public void setLineManager(String v){lineManager=v;}
    public String accessLevel(){return accessLevel;} public void setAccessLevel(String v){accessLevel=v;}
}
