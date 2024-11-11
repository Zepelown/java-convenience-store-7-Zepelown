package store.util;

import store.exception.ErrorMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EntityValidator {
    private static final String PROMOTION_DATE_FORMAT = "yyyy-MM-dd";
    private static final String KOREA_AND_ENGLISH_REGEX = "/^[ㄱ-ㅎ가-힣a-zA-Z0-9]+$/";

    public static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_ERROR.getErrorMessage());
        }
    }

    public static void validateString(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_ERROR.getErrorMessage());
        }
    }

    public static void validateKoreanAndEnglishOnly(String input) {
        if (input == null || !input.matches(KOREA_AND_ENGLISH_REGEX)){
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_ERROR.getErrorMessage());
        }
    }

    public static void validateInt(int input) {
        if (input <= 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_ERROR.getErrorMessage());
        }
    }

    public static LocalDate parseDate(String input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PROMOTION_DATE_FORMAT);
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_ERROR.getErrorMessage());
        }
    }
}
