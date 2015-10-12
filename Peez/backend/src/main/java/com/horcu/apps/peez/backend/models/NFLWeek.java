package com.horcu.apps.peez.backend.models;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;



import java.util.Date;

/**
 * Created by hacz on 10/10/2015.
 */
@Entity(name="NFLInfo")
public class NFLWeek {
    @Id
    private long id;

    @Index
    private int weekNumber;

    private java.util.Date dateRangeStart;

    private java.util.Date dateRangeEnd;

    private String type;

    private String games;

    public NFLWeek() {

    }


    public int getWeekNumber() {
        return weekNumber;
    }

    public Date getDateRangeStart() {
        return dateRangeStart;
    }

    public Date getDateRangeEnd() {
        return dateRangeEnd;
    }

    public String getType() {
        return type;
    }

    public String getGames() {
        return games;
    }
}
