package com.osuskin.cloud.enums;

public enum OsuGameModeEnum implements CodeEnum{
    STD(0,"主模式");

    OsuGameModeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    public int getCode() {
        return code;
    }


    public String getName() {
        return name;
    }


}
