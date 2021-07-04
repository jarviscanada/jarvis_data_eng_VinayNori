package ca.jrvs.apps.trading.model.domain;

import java.sql.Date;

public class Trader implements Entity<Integer> {
    private Integer id;
    private String firstName;
    private String lastName;
    private String country;
    private String email;
    private Date dob;

    public Trader() {
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{"
                + "                        \"id\":\"" + id + "\""
                + ",\n                         \"firstName\":\"" + firstName + "\""
                + ",\n                         \"lastName\":\"" + lastName + "\""
                + ",\n                         \"country\":\"" + country + "\""
                + ",\n                         \"email\":\"" + email + "\""
                + ",\n                         \"dob\":\"" + dob + "\""
                + "}";
    }
}