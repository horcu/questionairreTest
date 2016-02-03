package com.horcu.apps.peez.backend.models.misc;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by hacz on 11/7/2015.
 */
@Entity
public class BetStruct {

    @Id
    private String Id;

    @Index
    private String structure;

    public BetStruct(){}

    public String getStructure() {
        return structure;
    }
}
