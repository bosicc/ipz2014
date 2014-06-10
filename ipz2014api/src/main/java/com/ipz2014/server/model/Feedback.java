package com.ipz2014.server.model;

import java.io.Serializable;

public class Feedback implements Serializable {

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

    @Override
    public String toString() {
        return "Question[ email='" + email + '\''
                + ", feedback='" + feedback + ']';
    }
}