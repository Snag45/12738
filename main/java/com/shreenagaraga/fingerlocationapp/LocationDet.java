package com.shreenagaraga.fingerlocationapp;

/**
 * Created by Shreenag Araga on 27-03-2018.
 */

public class LocationDet {

        String address;
        String address1;
        String city;
        String country;
        String state;
        String postal_code;

        public LocationDet(){}

        public LocationDet(String address,
                String address1,
                String city,
                String country,
                String state,
                String postal_code) {

            this.address=address1;
            this.address1=address1;
            this.country=country;
            this.city=city;
            this.state=state;
            this.postal_code=postal_code;
        }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }
}
