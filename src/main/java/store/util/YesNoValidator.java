package store.util;

import store.exception.ErrorMessage;

public class YesNoValidator {
    public static boolean validateYN(String input) {
        if (input == null || !checkYesOrNoFormat(input)) {
            throw new IllegalArgumentException(ErrorMessage.ETC_ERROR.getErrorMessage());
        }

        return input.equals("Y");
    }

    private static boolean checkYesOrNoFormat(String input) {
        return input.equals("Y") || input.equals("N");
    }
}
