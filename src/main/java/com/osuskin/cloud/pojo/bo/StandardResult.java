package com.osuskin.cloud.pojo.bo;

import lombok.Data;

@Data
public class StandardResult<T> {
    private boolean success;
    private String msg;
    private T data;

    public StandardResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public StandardResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public StandardResult(boolean success) {
        this.success = success;
    }

    public static StandardResult create(){
        return new StandardResult(false);
    }

}
