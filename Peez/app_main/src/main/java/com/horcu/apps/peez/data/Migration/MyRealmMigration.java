package com.horcu.apps.peez.data.Migration;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by Horatio on 3/28/2016.
 */
public class MyRealmMigration implements RealmMigration {
    //TODO set up what to do during migration  Here is an example

    @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {


            // DynamicRealm exposes an editable schema
            RealmSchema schema = realm.getSchema();

            // Migrate to version 1: Add a new class.
            // Example:
            // public Person extends RealmObject {
            //     private String name;
            //     private int age;
            //     // getters and setters left out for brevity
            // }
            if (oldVersion == 0) {
                schema.create("Person")
                        .addField("name", String.class)
                        .addField("age", int.class);
                oldVersion++;
            }

            // Migrate to version 2: Add a primary key + object references
            // Example:
            // public Person extends RealmObject {
            //     private String name;
            //     @PrimaryKey
            //     private int age;
            //     private Dog favoriteDog;
            //     private RealmList<Dog> dogs;
            //     // getters and setters left out for brevity
            // }
            if (oldVersion == 1) {
                schema.get("Person")
                        .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                        .addRealmObjectField("favoriteDog", schema.get("Dog"))
                        .addRealmListField("dogs", schema.get("Dog"));
                oldVersion++;
            }
        }
}
