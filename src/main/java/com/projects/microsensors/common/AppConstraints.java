package com.projects.microsensors.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstraints {

    @UtilityClass
    public class Authentication {

        public static final String GET_USER_BY_USERNAME_QUERY = """
                SELECT username, password, enabled FROM users WHERE username = ?
                """;
        public static final String GET_AUTHORITY_BY_USERNAME_QUERY = """
                SELECT username, authority FROM authorities
                WHERE username = ?
                """;
    }

    @UtilityClass
    public class Pagination {

        public static final int PAGE_SIZE = 9;

    }

    @UtilityClass
    public class ViewName {
        public static final String MAIN = "./main";
        public static final String LOG_IN = "./log-in";
        public static final String SIGN_UP = "./sign-up";
        public static final String DASHBOARD = "./dashboard";
        public static final String TERMS_OF_SERVICE = "./terms";
        public static final String PRIVACY_POLICY = "./privacy";
    }

    @UtilityClass
    public class Path {

        public static final String MAIN_PAGE = "/main";
        public static final String LOG_IN_PAGE = "/log-in";
        public static final String DASHBOARD_PAGE = "/dashboard";
        public static final String FAVICON = "/favicon.ico";
    }

    @UtilityClass
    public class ExtendedPath {

        public static final String TERMS_OF_SERVICE = "/main/terms/**";
        public static final String PRIVACY_POLICY = "/main/privacy/**";
        public static final String MAIN_PAGE = "/main/**";
        public static final String LOG_IN_PAGE = "/log-in/**";
        public static final String SIGN_UP_PAGE = "/sign-up/**";
        public static final String DASHBOARD_PAGE = "/dashboard/**";
        public static final String ERROR_ATTRIBUTE = "?error";
        public static final String API = "/api/**";
        public static final String CSS = "/css/**";
        public static final String JS = "/js/**";
        public static final String STATIC = "/static/**";
    }
}
