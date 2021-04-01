package springboot.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filePath) throws IOException {
        return fileReadTemplate(filePath, br -> {
            Integer sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.valueOf(line);
            }
            return sum;
        });
    }

    public Integer calcMultiply(String filePath) throws IOException {
        return fileReadTemplate(filePath, br -> {
            Integer mulipliy = 1;
            String line = null;
            while ((line = br.readLine()) != null) {
                mulipliy *= Integer.valueOf(line);
            }
            return mulipliy;
        });
    }

    public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int result = callback.doSomethingWithReader(br);
            return result;
        }
    }
}
