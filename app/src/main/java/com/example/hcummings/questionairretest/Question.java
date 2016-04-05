package com.example.hcummings.questionairretest;

/**
 * Created by hcummings on 4/4/2016.
 */
public class Question {

    private int Id;
    private String question;
    private String answer;
    private int value;
    private String airdate;
    private String created_at;
    private String updated_at;
    private int category_id;
    private String game_id;
    private String invalid_count;
    private Category category;

    public Question(){}

    public Question(int id, String question, String answer, int value, String airdate, String created_at, String updated_at, int category_id, String game_id, String invalid_count, Category category) {
        Id = id;
        this.question = question;
        this.answer = answer;
        this.value = value;
        this.airdate = airdate;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.category_id = category_id;
        this.game_id = game_id;
        this.invalid_count = invalid_count;
        this.category = category;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getAirdate() {
        return airdate;
    }

    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getInvalid_count() {
        return invalid_count;
    }

    public void setInvalid_count(String invalid_count) {
        this.invalid_count = invalid_count;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
