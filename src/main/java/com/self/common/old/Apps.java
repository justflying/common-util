package com.self.common.old;

import org.springframework.util.StringUtils;

/**
 * Created by lichuandong on 2018/3/21 0026.
 */
public class Apps {

    private final static String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

    public static void setProfileIfNotExists(String profile) {
        if (!StringUtils.hasLength(System.getProperty(SPRING_PROFILE_ACTIVE))
                && !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
            System.setProperty(SPRING_PROFILE_ACTIVE, profile);
        }
    }

    public static String getProfile() {
        return System.getProperty(SPRING_PROFILE_ACTIVE);
    }
}
