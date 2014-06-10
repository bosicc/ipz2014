package com.ipz2014.server.responses;

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
