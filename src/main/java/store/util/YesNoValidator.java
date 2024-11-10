package store.util;

import store.exception.ErrorMessage;

public class YesNoValidator {
    public static boolean validateYN(String input) {
        if (!checkYesOrNoFormat(input)) {
            throw new IllegalArgumentException(ErrorMessage.ETC_ERROR.getErrorMessage());
        }

        return input.equals("Y");
    }

    private static boolean checkYesOrNoFormat(String input) {
        // Y 또는 N만 유효한 입력으로 허용
        return input.equals("Y") || input.equals("N");
    }
}
