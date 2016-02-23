package com.horcu.apps.peez.common.models.league;
/**
 * Created by hacz on 10/7/2015.
 */
public class Venue {

    private String id;

    private String country;

    private String name;

    private String city;

    private String state;

    private Long capacity;

    private String surface;

    private String type;

    private String zip;

    private String address;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Venue withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public Venue withCountry(String country) {
        this.country = country;
        return this;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Venue withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    public Venue withCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

    public Venue withState(String state) {
        this.state = state;
        return this;
    }

    /**
     * @return The capacity
     */
    public Long getCapacity() {
        return capacity;
    }

    /**
     * @param capacity The capacity
     */
    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Venue withCapacity(Long capacity) {
        this.capacity = capacity;
        return this;
    }

    /**
     * @return The surface
     */
    public String getSurface() {
        return surface;
    }

    /**
     * @param surface The surface
     */
    public void setSurface(String surface) {
        this.surface = surface;
    }

    public Venue withSurface(String surface) {
        this.surface = surface;
        return this;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public Venue withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    public Venue withZip(String zip) {
        this.zip = zip;
        return this;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public Venue withAddress(String address) {
        this.address = address;
        return this;
    }

}
