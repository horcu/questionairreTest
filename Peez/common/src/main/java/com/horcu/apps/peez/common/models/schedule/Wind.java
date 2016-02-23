package com.horcu.apps.peez.common.models.schedule;

/**
 * Created by hcummings on 10/9/2015.
 */
public class Wind {

    private Object speed;
    private Object direction;

    /**
     * No args constructor for use in serialization
     *
     */
    public Wind() {
    }

    /**
     *
     * @param speed
     * @param direction
     */
    public Wind(Object speed, Object direction) {
        this.speed = speed;
        this.direction = direction;
    }

    /**
     *
     * @return
     * The speed
     */
    public Object getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed
     * The speed
     */
    public void setSpeed(Object speed) {
        this.speed = speed;
    }

    public Wind withSpeed(Object speed) {
        this.speed = speed;
        return this;
    }

    /**
     *
     * @return
     * The direction
     */
    public Object getDirection() {
        return direction;
    }

    /**
     *
     * @param direction
     * The direction
     */
    public void setDirection(Object direction) {
        this.direction = direction;
    }

    public Wind withDirection(Object direction) {
        this.direction = direction;
        return this;
    }

}
