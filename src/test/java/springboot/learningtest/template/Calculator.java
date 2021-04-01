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

    public String concatenate(String filePath) throws IOException {
        return lineReadTemplate(filePath, "", (line, value) -> value + line);
    }

    public <T> T lineReadTemplate(String filePath, T initVal, LineCallback<T> callback) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            T result = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                result = callback.doSomethingWithLine(line, result);
            }
            return result;
        }
    }
}
