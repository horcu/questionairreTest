package com.horcu.apps.peez.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Horatio on 3/28/2016.
 */
public class FeedEntry extends RealmObject {

    @Required
    private String feedId;
    private String jsonFeed;

    public FeedEntry(){}

    public FeedEntry(String feedId, String jsonFeed) {
        this.feedId = feedId;
        this.jsonFeed = jsonFeed;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getJsonFeed() {
        return jsonFeed;
    }

    public void setJsonFeed(String jsonFeed) {
        this.jsonFeed = jsonFeed;
    }
}
