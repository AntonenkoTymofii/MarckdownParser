import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownConverter {
    private final Pattern boldRegEx = Pattern.compile("(?<!\\S)\\*\\*(\\S+?(?=\\s|))\\*\\*", Pattern.DOTALL);
    private final Pattern italicRegEx = Pattern.compile("(?<!\\S)_(\\S+?(?=\\s|))_", Pattern.DOTALL);
    private final Pattern monoRegEx = Pattern.compile("(?<!\\S)`(\\S+?(?=\\s|))`", Pattern.DOTALL);
    private final Pattern preFormRegEx = Pattern.compile("(```)(.*?)(```)", Pattern.DOTALL);
    private final Pattern invalidRegEx = Pattern.compile("\\*\\*`_(.*?)_`\\*\\*", Pattern.DOTALL);
    private final Pattern notFinalFormatRegEx = Pattern.compile("(?<=\\s)_\\w+\\b|(?<=\\s)\\*\\*\\w+\\b|(?<=\\s)`\\w+\\b", Pattern.DOTALL);

    public String readMarkdownFile(String filePath, String format) throws IOException {
        StringBuilder content = new StringBuilder();
        if (format == null || format.equals("html")){
            content.append("<p>\n");

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!(line.isEmpty())) {
                        content.append(line).append("\n");
                    } else {
                        content.append("</p>\n<p>\n");
                    }
                }
            }
            content.append("</p>");

        } else if (format.equals("ansi") || format.equals("ANSI")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!(line.isEmpty())) {
                        content.append(line).append("\n");
                    } else {
                        content.append("\n");
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Unsupported output format: " + format);
        }

        return content.toString();
    }

    public String convertMarkdownToHTML(String markdownContent, String format) throws InvalidFormatException {
        if(invalidRegEx.matcher(markdownContent).find()){
            throw new InvalidFormatException("Некоректний формат даних");
        }

        Matcher preFormMatcher = preFormRegEx.matcher(markdownContent);
        StringBuilder result = new StringBuilder();

        if (format == null || format.equals("html")){
            markdownContent = boldRegEx.matcher(markdownContent).replaceAll("<b>$1</b>");

            markdownContent = italicRegEx.matcher(markdownContent).replaceAll("<i>$1</i>");

            while (preFormMatcher.find()) {
                markdownContent = monoRegEx.matcher(markdownContent).replaceAll("<tt>$1</tt>");
                if(notFinalFormatRegEx.matcher(markdownContent).find()){
                    throw new InvalidFormatException("Нескінчене форматування");
                }
                preFormMatcher.appendReplacement(result, "<pre>" + preFormMatcher.group(2)
                        .replaceAll("<b>(.*?)</b>", "**$1**")
                        .replaceAll("<i>(.*?)</i>", "_$1_")
                        .replaceAll("<tt>(.*?)</tt>", "`$1`")
                        + "</pre>");
            }

            preFormMatcher.appendTail(result);
            markdownContent = result.toString();

            markdownContent = monoRegEx.matcher(markdownContent).replaceAll("<tt>$1</tt>");

        } else {
            markdownContent = boldRegEx.matcher(markdownContent).replaceAll("\u001b[1m$1\u001b[22m");

            markdownContent = italicRegEx.matcher(markdownContent).replaceAll("\u001b[3m$1\u001b[23m");

            while (preFormMatcher.find()) {
                markdownContent = monoRegEx.matcher(markdownContent).replaceAll("\u001b[7m$1\u001b[27m");
                if(notFinalFormatRegEx.matcher(markdownContent).find()){
                    throw new InvalidFormatException("Нескінчене форматування");
                }
                preFormMatcher.appendReplacement(result, "\u001b[7m" + preFormMatcher.group(2)
                        .replaceAll("\\u001b\\[1m(.*?)\\u001b\\[22m", "**$1**")
                        .replaceAll("\\u001b\\[3m(.*?)\\u001b\\[23m", "_$1_")
                        .replaceAll("\\u001b\\[7m(.*?)\\u001b\\[27m", "`$1`")
                        + "\u001b[27m");
            }

            preFormMatcher.appendTail(result);
            markdownContent = result.toString();

            markdownContent = monoRegEx.matcher(markdownContent).replaceAll("\u001b[7m$1\u001b[27m");
        }

        return markdownContent;
    }

    private void writeHTMLToFile(String filePath, String htmlContent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(htmlContent);
        }
    }

    public void writeOutput(String outputContent, String outputPath, String format) throws IOException {
        if (format == null || format.equals("html")) {
            writeHTMLToFile(outputPath, outputContent);
            System.out.println("HTML content saved to '" + outputPath + "'");
        } else if (format.equals("ansi")) {
            System.out.println(outputContent);
        }
    }
}
