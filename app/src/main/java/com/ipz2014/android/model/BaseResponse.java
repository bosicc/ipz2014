package com.ipz2014.android.model;

public class BaseResponse implements IBaseResponse {
    public boolean success;
    public int errorCode;

    public boolean isSuccess() {
        return success;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}
