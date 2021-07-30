package com.sh.journalmotherapp.util;

public class Const {

    public static final String BENH_VIEN_SELECTED = "BenhVienSelected";
    public static final String NHOM_BENH_SELECTED = "NhomBenhSelected";
    public static final String DOCTOR_SELECTED = "DoctorSelected";
    public static final String HOSPITAL_AUTO_NUMBER_SELECTED = "HospitalAutoNumberSelected";
    public static final String DOCTOR_SELECTED_CONFIRM = "DoctorSelectedConfirm";
    public static final String KHOA_KHAM_BENH_SELECTED = "KhoaKhamBenhSelected";
    public static final String VISIT_EXAM_HISTORY_SELECTED = "VisitExamHistorySelected";
    public static final String DOCTOR_SELECTED_TYPE = "DoctorSelectedType";
    public static final String SEARCH_DOCTOR_INFO = "SearchDoctorInfo";


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
        public static final String SUPPORTS = "SUPPORTS";
        public static final String SETTINGS = "SETTINGS";
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
