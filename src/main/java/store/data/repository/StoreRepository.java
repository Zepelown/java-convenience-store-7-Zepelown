package store.data.repository;

import store.exception.ErrorMessage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

interface StoreRepository {
    default BufferedReader createBufferedReader(String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_ERROR.getErrorMessage());
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
