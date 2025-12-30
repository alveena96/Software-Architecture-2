package domain;

public class Clinician {
    private String id, firstName, lastName, title, speciality, gmcNumber, phone, email,
            workplaceId, workplaceType, employmentStatus, startDate;

    public Clinician(String id, String firstName, String lastName, String title, String speciality, String gmcNumber,
                     String phone, String email, String workplaceId, String workplaceType,
                     String employmentStatus, String startDate) {
        this.id=id; this.firstName=firstName; this.lastName=lastName; this.title=title; this.speciality=speciality;
        this.gmcNumber=gmcNumber; this.phone=phone; this.email=email; this.workplaceId=workplaceId; this.workplaceType=workplaceType;
        this.employmentStatus=employmentStatus; this.startDate=startDate;
    }

    public String id(){return id;} public void setId(String v){id=v;}
    public String firstName(){return firstName;} public void setFirstName(String v){firstName=v;}
    public String lastName(){return lastName;} public void setLastName(String v){lastName=v;}
    public String title(){return title;} public void setTitle(String v){title=v;}
    public String speciality(){return speciality;} public void setSpeciality(String v){speciality=v;}
    public String gmcNumber(){return gmcNumber;} public void setGmcNumber(String v){gmcNumber=v;}
    public String phone(){return phone;} public void setPhone(String v){phone=v;}
    public String email(){return email;} public void setEmail(String v){email=v;}
    public String workplaceId(){return workplaceId;} public void setWorkplaceId(String v){workplaceId=v;}
    public String workplaceType(){return workplaceType;} public void setWorkplaceType(String v){workplaceType=v;}
    public String employmentStatus(){return employmentStatus;} public void setEmploymentStatus(String v){employmentStatus=v;}
    public String startDate(){return startDate;} public void setStartDate(String v){startDate=v;}
}
