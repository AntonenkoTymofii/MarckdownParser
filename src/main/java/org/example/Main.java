package org.example;

import java.io.IOException;

public class Main {

    private static final MarkdownConverter markdownConverter = new MarkdownConverter();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Main <input_file> [--out <output_file>] [--format=<value>]");
            System.exit(1);
        }

        String inputPath = args[0];
        String outputPath = null;
        String format = null;

        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("--out")) {
                outputPath = args[i + 1];
                i++;
            } else if (args[i].startsWith("--format=")) {
                format = args[i].substring(9);
            }
        }

        try {
            String markdownContent = markdownConverter.readMarkdownFile(inputPath, format);
            String outputContent = markdownConverter.convertMarkdown(markdownContent, format);
            markdownConverter.writeOutput(outputContent, outputPath, format);

        } catch (IOException | IllegalArgumentException | InvalidFormatException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}