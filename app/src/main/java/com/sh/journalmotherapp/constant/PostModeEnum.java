package com.sh.journalmotherapp.constant;

public enum PostModeEnum {

    PUBLIC("Public"),
    PRIVATE("Private");

    private final String name;

    PostModeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
