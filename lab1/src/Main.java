import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern boldRegEx = Pattern.compile("\\*\\*(.*?)\\*\\*", Pattern.DOTALL);
    private static final Pattern italicRegEx = Pattern.compile("_(.*?)_", Pattern.DOTALL);
    private static final Pattern monoRegEx = Pattern.compile("`(.*?)`", Pattern.DOTALL);
    private static final Pattern preFormRegEx = Pattern.compile("```(.*?)```", Pattern.DOTALL);

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Main <input_file> [--out <output_file>]");
            System.exit(1);
        }

        String inputPath = args[0];
        String outputPath = null;

        if (args.length == 4 && args[1].equals("--out")) {
            outputPath = args[2];
        }

        try {
            String markdownContent = readMarkdownFile(inputPath);
            String htmlContent = convertMarkdownToHTML(markdownContent);

            if (outputPath != null) {
                writeHTMLToFile(outputPath, "<p>" + htmlContent + "</p>");
                System.out.println("HTML content saved to '" + outputPath + "'");
            } else {
                System.out.println(htmlContent);
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static String readMarkdownFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }

    private static String convertMarkdownToHTML(String markdownContent) {
        markdownContent = boldRegEx.matcher(markdownContent).replaceAll("<b>$1</b>");

        markdownContent = italicRegEx.matcher(markdownContent).replaceAll("<i>$1</i>");

        markdownContent = preFormRegEx.matcher(markdownContent).replaceAll("<pre>$1</pre>");

        markdownContent = monoRegEx.matcher(markdownContent).replaceAll("<tt>$1</tt>");

        return markdownContent;
    }

    private static void writeHTMLToFile(String filePath, String htmlContent) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(htmlContent);
        }
    }
}