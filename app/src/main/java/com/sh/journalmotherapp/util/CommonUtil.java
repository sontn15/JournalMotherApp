package com.sh.journalmotherapp.util;

import java.util.UUID;

public class CommonUtil {

    public static String generateUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");

        return uuid;
    }

}
