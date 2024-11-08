package store.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EntityValidator {
    private static final String PROMOTION_DATE_FORMAT = "yyyy-MM-dd";
    public static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 잘못된 파일 형식입니다.");
        }
    }

    public static void validateString(String input) {
        if (input.isBlank()){
            throw new IllegalArgumentException("[ERROR] 잘못된 파일 형식입니다.");
        }
    }

    public static void validateInt(int input) {
        if (input <= 0){
            throw new IllegalArgumentException("[ERROR] 잘못된 파일 형식입니다.");
        }
    }

    public static LocalDate parseDate(String input){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PROMOTION_DATE_FORMAT);
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("[ERROR] 잘못된 파일 형식입니다.");
        }
    }
}
