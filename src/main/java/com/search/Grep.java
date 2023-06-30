package com.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of a basic grep linux like command.
 * <p>
 * This program follows GNU grep logic flow. It reads raw data into a large buffer,
 * searches the buffer using Boyer-Moore, and only when it finds a match
 * does it go and look for the bounding newlines.
 *
 * @author Frank Giordano
 */
public class Grep {

    private static String fileLocation;
    private static String pattern;
    private static BmSearch search;

    private static String getFileContent(File file) throws IOException {
        final var content = new StringBuilder();
        try (final var br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static List<String> search(String content) {
        final var results = new ArrayList<String>();
        var index = search.findPosition(content);
        while (index != 0) {
            final var entireLine = new StringBuilder();
            for (var i = index - 1; i >= 0; i--) {
                if (content.charAt(i) == '\n') {
                    break;
                }
                entireLine.append(content.charAt(i));
            }
            entireLine.reverse();

            final var foundStr = content.substring(index);
            for (var i = 0; i < foundStr.length(); i++) {
                if (foundStr.charAt(i) == '\n') {
                    break;
                }
                entireLine.append(foundStr.charAt(i));
            }

            if (entireLine.length() > 0) {
                results.add(entireLine.toString());
            }

            final var newIndex = index + pattern.length();
            if (newIndex > content.length()) {
                break;
            }
            content = content.substring(index + pattern.length());
            index = search.findPosition(content);
        }
        return results;
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
        search = new BmSearch(pattern);
    }

    public static void main(String[] args) throws IOException {
        try {
            setup(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        final var file = new File(fileLocation);

        if (file.isDirectory()) {
            List<String> files;
            files = Stream.of(file.listFiles())
                    .filter(item -> !item.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toList());

            files.sort(Comparator.naturalOrder());
            for (final var name : files) {
                search(getFileContent(new File(name)))
                        .forEach(item -> System.out.println(name + ":" + item));
            }
            return;
        }

        if (file.exists()) {
            search(getFileContent(file)).forEach(System.out::println);
        }
    }

}