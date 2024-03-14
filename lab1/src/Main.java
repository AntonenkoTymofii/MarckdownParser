import java.io.IOException;

public class Main {

    private static final MarkdownConverter markdownConverter = new MarkdownConverter();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Main <input_file> [--out <output_file>]");
            System.exit(1);
        }

        String inputPath = args[0];
        String outputPath = null;

//        String inputPath = "D:\\homework\\4 sem\\SDMT\\MarckdownParser\\lab1\\src\\TEST.md";
//        String outputPath = "D:\\homework\\4 sem\\SDMT\\MarckdownParser\\lab1\\src\\test.html";

        if (args.length == 3 && args[1].equals("--out")) {
            outputPath = args[2];
        }

        try {
            String markdownContent = markdownConverter.readMarkdownFile(inputPath);
            String htmlContent = markdownConverter.convertMarkdownToHTML(markdownContent);

            if (outputPath != null) {
                markdownConverter.writeHTMLToFile(outputPath, htmlContent);
                System.out.println("HTML content saved to '" + outputPath + "'");
            } else {
                System.out.println(htmlContent);
            }

        } catch (InvalidFormatException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}