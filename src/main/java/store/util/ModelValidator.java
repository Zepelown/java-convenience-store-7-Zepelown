package store.util;

public class ModelValidator {
    public static int parseInt(String input,String errorMessage) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void validateString(String input, String errorMessage) {
        if (input.isBlank()){
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void validateInt(int input, String errorMessage) {
        if (input <= 0){
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
