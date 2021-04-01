package springboot.learningtest.template;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;


class CalcSumTest {
    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum(getClass().getClassLoader().getResource("numbers.txt").getPath());
        assertThat(sum).isEqualTo(10);
    }
}
