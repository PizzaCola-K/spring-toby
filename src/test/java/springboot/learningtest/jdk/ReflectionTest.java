package springboot.learningtest.jdk;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

public class ReflectionTest {
    @Test
    void invokeMethod() throws Exception {
        String name = "Spring";

        assertThat(name).hasSize(6);
        Method lengthMethod = String.class.getMethod("length");
        assertThat((int) lengthMethod.invoke(name)).isEqualTo(6);

        assertThat(name.charAt(0)).isEqualTo('S');
        Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertThat((Character) charAtMethod.invoke(name, 0)).isEqualTo('S');
    }
}
