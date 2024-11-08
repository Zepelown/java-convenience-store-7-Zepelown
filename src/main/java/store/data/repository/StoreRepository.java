package store.data.repository;

import store.data.entity.Product;
import store.data.entity.Promotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
