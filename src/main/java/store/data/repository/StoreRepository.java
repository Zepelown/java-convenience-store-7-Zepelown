package store.data.repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

interface StoreRepository {
    String FILE_DELIMITER = ",";

    default BufferedReader createBufferedReader(String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("[ERROR]" + fileName + " 파일을 찾을 수 없습니다.");
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
