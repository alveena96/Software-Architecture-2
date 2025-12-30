package domain;

public class Patient {
    private String id, firstName, lastName, dob, nhs, gender, phone, email, address, postcode,
            emergencyName, emergencyPhone, registrationDate, gpSurgeryId;

    public Patient(String id, String firstName, String lastName, String dob, String nhs, String gender,
                   String phone, String email, String address, String postcode, String emergencyName,
                   String emergencyPhone, String registrationDate, String gpSurgeryId) {
        this.id=id; this.firstName=firstName; this.lastName=lastName; this.dob=dob; this.nhs=nhs; this.gender=gender;
        this.phone=phone; this.email=email; this.address=address; this.postcode=postcode; this.emergencyName=emergencyName;
        this.emergencyPhone=emergencyPhone; this.registrationDate=registrationDate; this.gpSurgeryId=gpSurgeryId;
    }
    public String id(){return id;} public void setId(String v){id=v;}
    public String firstName(){return firstName;} public void setFirstName(String v){firstName=v;}
    public String lastName(){return lastName;} public void setLastName(String v){lastName=v;}
    public String dob(){return dob;} public void setDob(String v){dob=v;}
    public String nhs(){return nhs;} public void setNhs(String v){nhs=v;}
    public String gender(){return gender;} public void setGender(String v){gender=v;}
    public String phone(){return phone;} public void setPhone(String v){phone=v;}
    public String email(){return email;} public void setEmail(String v){email=v;}
    public String address(){return address;} public void setAddress(String v){address=v;}
    public String postcode(){return postcode;} public void setPostcode(String v){postcode=v;}
    public String emergencyName(){return emergencyName;} public void setEmergencyName(String v){emergencyName=v;}
    public String emergencyPhone(){return emergencyPhone;} public void setEmergencyPhone(String v){emergencyPhone=v;}
    public String registrationDate(){return registrationDate;} public void setRegistrationDate(String v){registrationDate=v;}
    public String gpSurgeryId(){return gpSurgeryId;} public void setGpSurgeryId(String v){gpSurgeryId=v;}
}
