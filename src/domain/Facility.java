package domain;

public class Facility {
    private String id, name, type, address, postcode, phone, email, openingHours, managerName, capacity, specialitiesOffered;

    public Facility(String id, String name, String type, String address, String postcode, String phone, String email,
                    String openingHours, String managerName, String capacity, String specialitiesOffered) {
        this.id=id; this.name=name; this.type=type; this.address=address; this.postcode=postcode; this.phone=phone; this.email=email;
        this.openingHours=openingHours; this.managerName=managerName; this.capacity=capacity; this.specialitiesOffered=specialitiesOffered;
    }

    public String id(){return id;} public void setId(String v){id=v;}
    public String name(){return name;} public void setName(String v){name=v;}
    public String type(){return type;} public void setType(String v){type=v;}
    public String address(){return address;} public void setAddress(String v){address=v;}
    public String postcode(){return postcode;} public void setPostcode(String v){postcode=v;}
    public String phone(){return phone;} public void setPhone(String v){phone=v;}
    public String email(){return email;} public void setEmail(String v){email=v;}
    public String openingHours(){return openingHours;} public void setOpeningHours(String v){openingHours=v;}
    public String managerName(){return managerName;} public void setManagerName(String v){managerName=v;}
    public String capacity(){return capacity;} public void setCapacity(String v){capacity=v;}
    public String specialitiesOffered(){return specialitiesOffered;} public void setSpecialitiesOffered(String v){specialitiesOffered=v;}
}
