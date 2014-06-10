package com.ipz2014.android.model;

import java.io.Serializable;

public class GetUserFeedbackResponse extends BaseResponse implements Serializable {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
