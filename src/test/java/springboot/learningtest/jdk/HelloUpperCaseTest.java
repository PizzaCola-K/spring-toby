package springboot.learningtest.jdk;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HelloUpperCaseTest {

    @Test
    void proxiedHello() {
        Hello proxiedHello = new HelloUpperCase(new HelloTarget());
        assertThat(proxiedHello.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(proxiedHello.sayHi("Toby")).isEqualTo("HI TOBY");
        assertThat(proxiedHello.sayThankYou("Toby")).isEqualTo("THANK YOU TOBY");
    }
}
