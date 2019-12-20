package admin.model;

public class User {

    //{"email":"mix7reload@gmail.com ","id":"0EaBsE2IitYHgz52mNOXmWp4Jey2","nombre":"Edgar Andres Angrino","telefono":31555555555}

    /**
     * User Data
     */
    private String UID;
    private String name;
    private String id;
    private String profession;
    private String email;
    private String mobile_number;
    private String telephone;
    private String state;
    private String city;

    /**
     * Company Data
     */
    private String name_company;
    private String telephone_company;
    private String address_company;

    private String ref;

    /**
     * Constructor
     */
    public User() {

    }

    /**
     * Setters And Getters
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName_company() {
        return name_company;
    }

    public void setName_company(String name_company) {
        this.name_company = name_company;
    }

    public String getTelephone_company() {
        return telephone_company;
    }

    public void setTelephone_company(String telephone_company) {
        this.telephone_company = telephone_company;
    }

    public String getAddress_company() {
        return address_company;
    }

    public void setAddress_company(String address_company) {
        this.address_company = address_company;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
