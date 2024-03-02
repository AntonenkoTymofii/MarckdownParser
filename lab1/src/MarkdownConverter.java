import java.io.*;
import java.util.regex.Pattern;

public class MarkdownConverter {
    private final Pattern boldRegEx = Pattern.compile("\\*\\*(.*?)\\*\\*", Pattern.DOTALL);
    private final Pattern italicRegEx = Pattern.compile("_(.*?)_", Pattern.DOTALL);
    private final Pattern monoRegEx = Pattern.compile("`(.*?)`", Pattern.DOTALL);
    private final Pattern preFormRegEx = Pattern.compile("```(.*?)```", Pattern.DOTALL);

    public String readMarkdownFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = "<p>";
            while ((line = reader.readLine()) != null) {
                content.append("<p>").append(line).append("</p>\n");
            }
        }

        return content.toString();
    }

    public String convertMarkdownToHTML(String markdownContent) {
        markdownContent = boldRegEx.matcher(markdownContent).replaceAll("<b>$1</b>");

        markdownContent = italicRegEx.matcher(markdownContent).replaceAll("<i>$1</i>");

        markdownContent = preFormRegEx.matcher(markdownContent).replaceAll("<pre>$1</pre>");

        markdownContent = monoRegEx.matcher(markdownContent).replaceAll("<tt>$1</tt>");

        return markdownContent;
    }

    public void writeHTMLToFile(String filePath, String htmlContent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(htmlContent);
        }
    }
}
