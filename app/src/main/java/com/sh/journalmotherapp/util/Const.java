package com.sh.journalmotherapp.util;

public class Const {

    public static final String POST_SELECTED = "POST_SELECTED";


    public static class KEY_SHARE_PREFERENCE {
        public static final String USER_LOGIN = "UserLogin";
        public static final String GET_NUMBER_HOSPITAL_PREFIX = "GetNumberHospital_";
    }

    public static class VisitExamStatus {
        public static final String CANCELED = "CANCELED";
        public static final String REGISTER = "REGISTER";
        public static final String DONE = "DONE";
    }

    public static class FirebaseRef {
        public static final String USERS = "USERS";
        public static final String CATEGORIES = "CATEGORIES";
        public static final String NEWS = "NEWS";
        public static final String POSTS = "POSTS";
        public static final String COMMENTS = "COMMENTS";
        public static final String MEMORIES = "MEMORIES";

        public static final String NEWS_FOOD = "NEWS_FOOD";
        public static final String NEWS_MOM = "NEWS_MOM";
        public static final String NEWS_BABY = "NEWS_BABY";
    }

    public static class Post {
        public static final int MAX_TEXT_LENGTH_IN_LIST = 300; //characters
        public static final int MAX_POST_TITLE_LENGTH = 255; //characters
        public static final int POST_AMOUNT_ON_PAGE = 10;
    }

    public static class PushNotification {
        public static final int LARGE_ICONE_SIZE = 256; //px
    }

    public static class Profile {
        public static final int MAX_AVATAR_SIZE = 1280; //px, side of square
        public static final int MIN_AVATAR_SIZE = 100; //px, side of square
        public static final int MAX_NAME_LENGTH = 120;
    }


}
