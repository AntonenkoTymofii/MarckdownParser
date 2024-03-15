import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownConverter {
    private final Pattern boldRegEx = Pattern.compile("\\*\\*(.*?)\\*\\*", Pattern.DOTALL);
    private final Pattern italicRegEx = Pattern.compile("_(.*?)_", Pattern.DOTALL);
    private final Pattern monoRegEx = Pattern.compile("`(.*?)`", Pattern.DOTALL);
    private final Pattern preFormRegEx = Pattern.compile("(```)(.*?)(```)", Pattern.DOTALL);
    private final Pattern invalidRegEx = Pattern.compile("\\*\\*`_(.*?)_`\\*\\*", Pattern.DOTALL);
    private final Pattern notFinalFormatRegEx = Pattern.compile("_(.*)|\\*\\*(.*)|`(.*)", Pattern.DOTALL);

    public String readMarkdownFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder("<p>\n");

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

        return content.toString();
    }

    public String convertMarkdownToHTML(String markdownContent) throws InvalidFormatException {
        if(invalidRegEx.matcher(markdownContent).find()){
            throw new InvalidFormatException("Некоректний формат даних");
        }

        markdownContent = boldRegEx.matcher(markdownContent).replaceAll("<b>$1</b>");

        markdownContent = italicRegEx.matcher(markdownContent).replaceAll("<i>$1</i>");

        Matcher preFormMatcher = preFormRegEx.matcher(markdownContent);
        StringBuffer result = new StringBuffer();
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

        return markdownContent;
    }

    public void writeHTMLToFile(String filePath, String htmlContent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(htmlContent);
        }
    }
}
