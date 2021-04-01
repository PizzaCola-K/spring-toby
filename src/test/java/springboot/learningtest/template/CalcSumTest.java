package springboot.learningtest.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class CalcSumTest {
    private Calculator calculator;
    private String numFilePath;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
        numFilePath = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(numFilePath)).isEqualTo(10);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(numFilePath)).isEqualTo(24);
    }

    @Test
    public void concatenateString() throws IOException {
        assertThat(calculator.concatenate(numFilePath)).isEqualTo("1234");
    }
}
