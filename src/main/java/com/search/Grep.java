package com.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of a basic grep linux like command.
 * <p>
 * This program follows GNU grep logic flow. It reads raw data into a large buffer,
 * searches the buffer using Boyer-Moore algorithm, and only when it finds a match
 * does it go and look for the bounding newlines.
 *
 * @author Frank Giordano
 */
public class Grep {

    private static String fileLocation;
    private static String pattern;
    private static String fileName;

    public static void main(String[] args) throws IOException {
        try {
            setup(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        final var file = new File(fileLocation);
        if (file.isDirectory()) {
            final var files = Stream.of(Objects.requireNonNull(file.listFiles()))
                    .filter(item -> !item.isDirectory())
                    .map(File::getName)
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toList());

            for (final var name : files) {
                String content = getFileContent(new File(fileLocation + "/" + name));
                fileName = name;
                search(content);
            }
            return;
        }

        if (file.exists()) {
            String content = getFileContent(file);
            fileName = file.getName();
            search(content);
        }
    }

    private static void setup(String[] args) {
        try {
            fileLocation = args[0];
        } catch (Exception e) {
            throw new IllegalArgumentException("missing first file or directory argument, try again...");
        }
        try {
            pattern = args[1];
        } catch (Exception e) {
            throw new IllegalArgumentException("missing second search pattern argument, try again...");
        }
    }

    private static String getFileContent(File file) throws IOException {
        var content = new StringBuilder();
        try (var br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static void search(final String content) {
        int rc = BasicSearch.search(content, pattern);
        if (rc > 0) {
            System.out.println("\nBasic search for " + fileName + ":\n");
            BasicSearch.lines.forEach(System.out::println);
        }

        rc = AdvanceSearch.search(content, pattern);
        if (rc > 0) {
            System.out.println("\nAdvance search for " + fileName + ":\n");
            AdvanceSearch.lines.forEach(System.out::println);
        }

    }

}
