package dev.dankov.primes.config;

public class Constants
{
    // Error messages
    public static final String ACCESS_DENIED_MESSAGE = "Access Denied";
    public static final String CHAT_CONFLICT_MESSAGE = "Chat between %s and %s already exists";
    public static final String CHAT_NOT_FOUND_MESSAGE = "Chat was not found";
    public static final String INVALID_ENUM_MESSAGE = "No constant with text %s found";
    public static final String USER_NOT_FOUND_MESSAGE = "User was not found";

    // Property validation constants
    public static final int MESSAGE_MAX_LENGTH = 1000;
    public static final int USERNAME_MIN_LENGTH = 6;
    public static final int USERNAME_MAX_LENGTH = 20;

    // Property validation errors
    public static final String ERROR_MESSAGE = "Error message must not be empty";
    public static final String MESSAGE_TEXT_MESSAGE = "Message text must not be empty";
    public static final String MESSAGE_LENGTH_MESSAGE = "Message must not be longer than 1000 characters";
    public static final String PASSWORD_MESSAGE = "Password must not be empty";
    public static final String USERNAME_MESSAGE = "Username must not be empty";
    public static final String USERNAME_LENGTH_MESSAGE = "Username must be between 6 and 20 characters long";
    public static final String USERNAME_PATTERN_MESSAGE = "Username must contain only letters, digits, and underscores";
    public static final String USERNAME_TAKEN_MESSAGE = "User with username '%s' already exists";
    public static final String UUID_MESSAGE = "User ID must be a valid UUID";

    // Property validation patterns
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]+$";
}
