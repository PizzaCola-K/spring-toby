package springboot.learningtest.template;

import java.io.IOException;

public interface BufferedReaderCallback {
    Integer doSomethingWithLine(String line, Integer value) throws IOException;
}
