package com.horcu.apps.peez.data;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Horatio on 3/28/2016.
 */
public class DbHelper {

    private Realm realm;

    public DbHelper(Realm realm){
        this.realm = realm;
    }

    public  Boolean saveToDb(RealmObject obj){
        try {
            realm.beginTransaction();
            realm.copyToRealm(obj);
            realm.commitTransaction();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
