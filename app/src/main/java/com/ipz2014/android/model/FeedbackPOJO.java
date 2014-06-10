package com.ipz2014.android.model;

import com.github.nrudenko.orm.annotation.Table;

@Table
public class FeedbackPOJO {

    private String email;
    private String feedback;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
