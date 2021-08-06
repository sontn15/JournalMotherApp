package com.sh.journalmotherapp.constant;

public enum UserKernelStatusEnum {

    SINGLE("Single"),
    MARRIED("Married"),
    SEPARATED("Separated"),
    DIVORCED("Divorced");

    private final String name;

    UserKernelStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
