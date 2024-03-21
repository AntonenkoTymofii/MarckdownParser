import org.example.MarkdownConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class MarkdownConverterTest {
    MarkdownConverter markdownConverterTest = new MarkdownConverter();

    @Test
    void readMarkdownFileTestHtml() throws IOException {
        Assertions.assertEquals("<p>\nHi, my name is **Tim**!\n</p>",
                markdownConverterTest.readMarkdownFile("D:\\homework\\4 sem\\SDMT\\MarckdownParser\\src\\test\\resources\\TEST.md", "html"));
    }
    @Test
    void readMarkdownFileTestNull() throws IOException {
        Assertions.assertEquals("<p>\nHi, my name is **Tim**!\n</p>",
                markdownConverterTest.readMarkdownFile("D:\\homework\\4 sem\\SDMT\\MarckdownParser\\src\\test\\resources\\TEST.md", null));
    }
    @Test
    void readMarkdownFileTestAnsi() throws IOException {
        Assertions.assertEquals("Hi, my name is **Tim**!\n",
                markdownConverterTest.readMarkdownFile("D:\\homework\\4 sem\\SDMT\\MarckdownParser\\src\\test\\resources\\TEST.md", "ansi"));
        Assertions.assertEquals("Hi, my name is **Tim**!\n",
                markdownConverterTest.readMarkdownFile("D:\\homework\\4 sem\\SDMT\\MarckdownParser\\src\\test\\resources\\TEST.md", "ANSI"));
    }

    @Test
    void convertMarkdownToHTML() {
    }


    @Test
    void writeOutput() {
    }
}