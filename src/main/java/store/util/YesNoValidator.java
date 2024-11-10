package store.util;

import store.exception.ErrorMessage;

public class YesNoValidator {
    public static boolean isYes(String input){
        if (input.equals("Y")){
            return true;
        }
        if (input.equals("N")){
            return false;
        }
        throw new IllegalArgumentException(ErrorMessage.ETC_ERROR.getErrorMessage());
    }
}
