import org.example.InvalidFormatException;
import org.example.MarkdownConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

class MarkdownConverterTest {
    MarkdownConverter markdownConverterTest;

    @BeforeEach
    void setUp(){
        markdownConverterTest = new MarkdownConverter();
    }

    @Test
    void readMarkdownFileTestHtml() throws IOException {
        Assertions.assertEquals(
                "<p>\nHi, my name is **Tim**!\n</p>",
                markdownConverterTest.readMarkdownFile(
                        "src/test/resources/TEST.md", "html"
                )
        );
    }
    @Test
    void readMarkdownFileTestNull() throws IOException {
        Assertions.assertEquals(
                "<p>\nHi, my name is **Tim**!\n</p>",
                markdownConverterTest.readMarkdownFile(
                        "src/test/resources/TEST.md", null
                )
        );
    }
    @Test
    void readMarkdownFileTestAnsi() throws IOException {
        Assertions.assertEquals(
                "Hi, my name is **Tim**!\n",
                markdownConverterTest.readMarkdownFile(
                        "src/test/resources/TEST.md", "ansi"
                )
        );
    }

    @Test
    void readMarkdownFileTestUnknownPath() {
        Assertions.assertThrows(
                IOException.class,
                () -> markdownConverterTest.readMarkdownFile(
                        "src/test/resources/TEST", null
                )
        );
    }

    @Test
    void readMarkdownFileTestUnknownFormat() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> markdownConverterTest.readMarkdownFile(
                        "src/test/resources/TEST.md", "pddh"
                )
        );
    }

    @Test
    void convertMarkdownTestInvalidFormat() {
        Assertions.assertThrows(
                InvalidFormatException.class,
                () -> markdownConverterTest.convertMarkdown(
                        "**`_Hello_`**", null
                )
        );

        Assertions.assertThrows(
                InvalidFormatException.class,
                () -> markdownConverterTest.convertMarkdown(
                        "**`_Hello_`**", "html"
                )
        );

        Assertions.assertThrows(
                InvalidFormatException.class,
                () -> markdownConverterTest.convertMarkdown(
                        "**`_Hello_`**", "ansi"
                )
        );
    }

    @Test
    void convertMarkdownTestNotFinalFormat() {
        Assertions.assertThrows(InvalidFormatException.class,
                () -> markdownConverterTest.convertMarkdown(
                        "<p>\n**Hello dhfjjd**\n</p>\n<p>\n```\n**Hello dhfjjd\n```\n</p>",
                        null
                )
        );

        Assertions.assertThrows(InvalidFormatException.class,
                () -> markdownConverterTest.convertMarkdown(
                        "<p>\n**Hello dhfjjd**\n</p>\n<p>\n```\n**Hello dhfjjd\n```\n</p>",
                        "html"
                )
        );

        Assertions.assertThrows(InvalidFormatException.class,
                () -> markdownConverterTest.convertMarkdown(
                        "<p>\n**Hello dhfjjd**\n</p>\n<p>\n```\n**Hello dhfjjd\n```\n</p>",
                        "ansi"
                )
        );

    }

    @Test
    void convertMarkdownTestHtmlBold() throws InvalidFormatException {
        Assertions.assertEquals(
                "<p>\nHi, my name is <b>Tim</b>!\n</p>",
                markdownConverterTest.convertMarkdown(
                    "<p>\nHi, my name is **Tim**!\n</p>", null
                )
        );

        Assertions.assertEquals(
                "<p>\nHi, my name is <b>Tim</b>!\n</p>",
                markdownConverterTest.convertMarkdown(
                     "<p>\nHi, my name is **Tim**!\n</p>", "html"
                )
        );
    }

    @Test
    void convertMarkdownTestHtmlItalic() throws InvalidFormatException {
        Assertions.assertEquals(
                "<p>\nHi, my name is <i>Tim</i>!\n</p>",
                markdownConverterTest.convertMarkdown(
                    "<p>\nHi, my name is _Tim_!\n</p>", null
                )
        );

        Assertions.assertEquals(
                "<p>\nHi, my name is <i>Tim</i>!\n</p>",
                markdownConverterTest.convertMarkdown(
                     "<p>\nHi, my name is _Tim_!\n</p>", "html"
                )
        );
    }

    @Test
    void convertMarkdownTestHtmlMono() throws InvalidFormatException {
        Assertions.assertEquals(
                "<p>\nHi, my name is <tt>Tim</tt>!\n</p>",
                markdownConverterTest.convertMarkdown(
                    "<p>\nHi, my name is `Tim`!\n</p>", null
                )
        );

        Assertions.assertEquals(
                "<p>\nHi, my name is <tt>Tim</tt>!\n</p>",
                markdownConverterTest.convertMarkdown(
                     "<p>\nHi, my name is `Tim`!\n</p>", "html"
                )
        );
    }

    @Test
    void convertMarkdownTestHtmlPre() throws InvalidFormatException {
        Assertions.assertEquals(
                "<p>\n<pre>\nHi, my name is **Tim**!\n</pre>\n</p>",
                markdownConverterTest.convertMarkdown(
                    "<p>\n```\nHi, my name is **Tim**!\n```\n</p>", null
                )
        );

        Assertions.assertEquals(
                "<p>\n<pre>\nHi, my name is **Tim**!\n</pre>\n</p>",
                markdownConverterTest.convertMarkdown(
                        "<p>\n```\nHi, my name is **Tim**!\n```\n</p>", "html"
                )
        );
    }

    @Test
    void convertMarkdownTestHtml() throws InvalidFormatException {
        Assertions.assertEquals(
                "<p>\n<b>Hi</b>\n<i>Hi</i>\n<tt>Hi</tt>\n<pre>\nHi, my name is **Tim**!\n</pre>\n</p>",
                markdownConverterTest.convertMarkdown(
                    "<p>\n**Hi**\n_Hi_\n`Hi`\n```\nHi, my name is **Tim**!\n```\n</p>", null
                )
        );

        Assertions.assertEquals(
                "<p>\n<b>Hi</b>\n<i>Hi</i>\n<tt>Hi</tt>\n<pre>\nHi, my name is **Tim**!\n</pre>\n</p>",
                markdownConverterTest.convertMarkdown(
                    "<p>\n**Hi**\n_Hi_\n`Hi`\n```\nHi, my name is **Tim**!\n```\n</p>", "html"
                )
        );
    }

    //-----------------------------------------------------------------------------------------------------

    @Test
    void convertMarkdownTestAnsiBold() throws InvalidFormatException {
        Assertions.assertEquals(
                "Hi, my name is \u001b[1mTim\u001b[22m!",
                markdownConverterTest.convertMarkdown(
                    "Hi, my name is **Tim**!", "ansi"
                )
        );
    }

    @Test
    void convertMarkdownTestAnsiItalic() throws InvalidFormatException {
        Assertions.assertEquals(
                "Hi, my name is \u001b[3mTim\u001b[23m!",
                markdownConverterTest.convertMarkdown(
                        "Hi, my name is _Tim_!", "ansi"
                )
        );
    }

    @Test
    void convertMarkdownTestAnsiMono() throws InvalidFormatException {
        Assertions.assertEquals(
                "Hi, my name is \u001b[7mTim\u001b[27m!",
                markdownConverterTest.convertMarkdown(
                        "Hi, my name is `Tim`!", "ansi"
                )
        );
    }

    @Test
    void convertMarkdownTestAnsiPre() throws InvalidFormatException {
        Assertions.assertEquals(
                "\u001b[7m\nHi, my name is **Tim**!\n\u001b[27m",
                markdownConverterTest.convertMarkdown(
                    "```\nHi, my name is **Tim**!\n```", "ansi"
                )
        );
    }

    @Test
    void convertMarkdownTestAnsi() throws InvalidFormatException {
        Assertions.assertEquals(
                "\u001b[1mHi\u001b[22m\n\u001b[3mHi\u001b[23m\n\u001b[7mHi\u001b[27m\n\u001b[7m\nHi, my name is **Tim**!\n\u001b[27m",
                markdownConverterTest.convertMarkdown(
                    "**Hi**\n_Hi_\n`Hi`\n```\nHi, my name is **Tim**!\n```", "ansi"
                )
        );
    }


    @Test
    void writeOutputHtml() throws IOException {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        markdownConverterTest.writeOutput(
                """
                        <p>
                        <b>Hi</b>
                        <i>Hi</i>
                        <tt>Hi</tt>
                        <pre>
                        Hi, my name is **Tim**!
                        </pre>
                        </p>""",
                "src/test/resources/test.html", "html");

        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/test.html"));
        StringBuilder fileContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            fileContent.append(line).append("\n");
        }
        reader.close();

        Assertions.assertEquals(
                """
                        <p>
                        <b>Hi</b>
                        <i>Hi</i>
                        <tt>Hi</tt>
                        <pre>
                        Hi, my name is **Tim**!
                        </pre>
                        </p>""",
                fileContent.toString().trim());

        Assertions.assertEquals("HTML content saved to 'src/test/resources/test.html'",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void writeOutputNull() throws IOException {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        markdownConverterTest.writeOutput(
                """
                        <p>
                        <b>Hi</b>
                        <i>Hi</i>
                        <tt>Hi</tt>
                        <pre>
                        Hi, my name is **Tim**!
                        </pre>
                        </p>""",
                "src/test/resources/test.html", null);

        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/test.html"));
        StringBuilder fileContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            fileContent.append(line).append("\n");
        }
        reader.close();

        Assertions.assertEquals(
                """
                        <p>
                        <b>Hi</b>
                        <i>Hi</i>
                        <tt>Hi</tt>
                        <pre>
                        Hi, my name is **Tim**!
                        </pre>
                        </p>""",
                fileContent.toString().trim());

        Assertions.assertEquals("HTML content saved to 'src/test/resources/test.html'",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void writeOutputAnsi() throws IOException {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        markdownConverterTest.writeOutput(
                """
                        Hi, my name is \u001b[1mTim\u001b[22m!
                                                
                        Hi, my name is \u001b[3mTim\u001b[23m!
                                                
                        Hi, my name is \u001b[7mTim\u001b[27m!
                                                
                        \u001b[7m
                        Hi, my name is **Tim**!
                        \u001b[27m""",
                "src/test/resources/test.html", "ansi");


        Assertions.assertEquals(
                """
                        Hi, my name is \u001b[1mTim\u001b[22m!
                                                
                        Hi, my name is \u001b[3mTim\u001b[23m!
                                                
                        Hi, my name is \u001b[7mTim\u001b[27m!
                                                
                        \u001b[7m
                        Hi, my name is **Tim**!
                        \u001b[27m""",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void writeOutputThrows() {

        Assertions.assertThrows(IOException.class, () -> markdownConverterTest.writeOutput(
                """
                        <p>
                        <b>Hi</b>
                        <i>Hi</i>
                        <tt>Hi</tt>
                        <pre>
                        Hi, my name is **Tim**!
                        </pre>
                        </p>""",
                "src/tst/resos/test.htl", null));
    }

}