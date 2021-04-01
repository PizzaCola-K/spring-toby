package springboot.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filePath) throws IOException {
        return lineReadTemplate(filePath, 0, (line, value) -> value + Integer.parseInt(line));
    }

    public Integer calcMultiply(String filePath) throws IOException {
        return lineReadTemplate(filePath, 1, (line, value) -> value * Integer.parseInt(line));
    }

    public Integer lineReadTemplate(String filePath, int initVal, BufferedReaderCallback callback) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            Integer result = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                result = callback.doSomethingWithLine(line, result);
            }
            return result;
        }
    }
}
