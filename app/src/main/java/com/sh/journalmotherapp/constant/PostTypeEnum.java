package com.sh.journalmotherapp.constant;

public enum PostTypeEnum {

    MEMORIES("#memories"),
    ASK_FOR_HELP("#ask_for_help");

    private final String name;

    PostTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
