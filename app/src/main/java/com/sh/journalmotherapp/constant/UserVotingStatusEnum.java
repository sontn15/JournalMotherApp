package com.sh.journalmotherapp.constant;

public enum UserVotingStatusEnum {

    FIRST_TRIMESTER("First trimester – conception to 12 weeks"),
    SECOND_TRIMESTER("Second trimester – 12 to 24 weeks"),
    THIRD_TRIMESTER("Third trimester – 24 to 40 weeks"),
    POSTPARTUM("Postpartum period (6 weeks after birth)"),
    THREE_TO_SIX("3 to 6 months after birth"),
    NEW_NORMAL_MOM("New normal mom (6 to 18 months of postpartum)"),
    ;

    private final String name;

    UserVotingStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
