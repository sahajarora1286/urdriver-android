package com.sahajarora.urdriver;

/**
 * Created by sahajarora on 16-05-15.
 */
public class Booking {
    private String date, time, latitude, longitude;
    private String carModel, transmission;
    private String pickupAddress;

    public String getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(String pickupLat) {
        this.pickupLat = pickupLat;
    }

    public String getPickupLong() {
        return pickupLong;
    }

    public void setPickupLong(String pickupLong) {
        this.pickupLong = pickupLong;
    }

    public String getDropoffLat() {
        return dropoffLat;
    }

    public void setDropoffLat(String dropoffLat) {
        this.dropoffLat = dropoffLat;
    }

    public String getDropoffLong() {
        return dropoffLong;
    }

    public void setDropoffLong(String dropoffLong) {
        this.dropoffLong = dropoffLong;
    }

    private String pickupLat, pickupLong, dropoffLat, dropoffLong;

    public String getDropoffAddress() {
        return dropoffAddress;
    }

    public void setDropoffAddress(String dropoffAddress) {
        this.dropoffAddress = dropoffAddress;
    }

    private String dropoffAddress;

    public String getPickupAddress(){
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress){
        this.pickupAddress = pickupAddress;
    }

    public String getDate() {
        return date;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
