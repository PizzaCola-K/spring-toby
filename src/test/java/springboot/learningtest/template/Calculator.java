package springboot.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            Integer sum = 0;
            String line = null;
            while((line = br.readLine()) != null) {
                sum += Integer.valueOf(line);
            }
            return sum;
        }
    }
}
