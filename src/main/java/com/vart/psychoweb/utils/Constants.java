package com.vart.psychoweb.utils;

import java.util.regex.Pattern;

public class Constants {
    private Constants() {
    }

    public static final Pattern keyPattern = Pattern.compile("^.{8}-.{4}-.{4}-.{4}-.{12}$");
    public static final String ERROR_MESSAGE = "Error message: ";
    public static final String USER_PHONE_EXISTING_ERROR = "User already exist with phone: ";
    public static final String USER_EMAIL_EXISTING_ERROR = "User already exist with email: ";
    public static final String ROLE_NOT_FOUND_EXCEPTION = "The role doesn't exist with the name: ";
    public static final String USER_NOT_FOUND_EXCEPTION = "User doesn't exist with: ";
    public static final String USER_DATA_NULL_EXCEPTION = "User data to create is null";
    public static final String CONSULTATION_NOT_FOUND_EXCEPTION = "Consultation doesn't exist with id: ";


}

