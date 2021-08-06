package com.sh.journalmotherapp.constant;

public enum NewsCategoryEnum {

    BABY("Baby news"),
    MOM("Mom news"),
    FOOD("Food news");

    private final String name;

    NewsCategoryEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
