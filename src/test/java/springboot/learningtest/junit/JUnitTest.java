package springboot.learningtest.junit;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class JUnitTest {
    static JUnitTest testObject;

    @Test
    public void test1() {
        assertThat(this).isNotSameAs(testObject);
        testObject = this;
    }


    @Test
    public void test2() {
        assertThat(this).isNotSameAs(testObject);
        testObject = this;
    }

    @Test
    public void test3() {
        assertThat(this).isNotSameAs(testObject);
        testObject = this;
    }
}
