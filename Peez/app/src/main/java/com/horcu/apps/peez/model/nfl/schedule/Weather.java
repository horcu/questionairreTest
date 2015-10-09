package com.horcu.apps.peez.model.nfl.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hcummings on 10/9/2015.
 */
public class Weather {
    @SerializedName("temperature")
    @Expose
    private Object temperature;
    @SerializedName("condition")
    @Expose
    private Object condition;
    @SerializedName("humidity")
    @Expose
    private Object humidity;
    @SerializedName("wind")
    @Expose
    private Wind wind;

    /**
     * No args constructor for use in serialization
     *
     */
    public Weather() {
    }

    /**
     *
     * @param wind
     * @param humidity
     * @param condition
     * @param temperature
     */
    public Weather(Object temperature, Object condition, Object humidity, Wind wind) {
        this.temperature = temperature;
        this.condition = condition;
        this.humidity = humidity;
        this.wind = wind;
    }

    /**
     *
     * @return
     * The temperature
     */
    public Object getTemperature() {
        return temperature;
    }

    /**
     *
     * @param temperature
     * The temperature
     */
    public void setTemperature(Object temperature) {
        this.temperature = temperature;
    }

    public Weather withTemperature(Object temperature) {
        this.temperature = temperature;
        return this;
    }

    /**
     *
     * @return
     * The condition
     */
    public Object getCondition() {
        return condition;
    }

    /**
     *
     * @param condition
     * The condition
     */
    public void setCondition(Object condition) {
        this.condition = condition;
    }

    public Weather withCondition(Object condition) {
        this.condition = condition;
        return this;
    }

    /**
     *
     * @return
     * The humidity
     */
    public Object getHumidity() {
        return humidity;
    }

    /**
     *
     * @param humidity
     * The humidity
     */
    public void setHumidity(Object humidity) {
        this.humidity = humidity;
    }

    public Weather withHumidity(Object humidity) {
        this.humidity = humidity;
        return this;
    }

    /**
     *
     * @return
     * The wind
     */
    public Wind getWind() {
        return wind;
    }

    /**
     *
     * @param wind
     * The wind
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Weather withWind(Wind wind) {
        this.wind = wind;
        return this;
    }

}
