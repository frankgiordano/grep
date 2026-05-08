package com.search;

import java.util.ArrayList;
import java.util.List;

public class BasicSearch {

    public static final List<String> lines = new ArrayList<>();

    public static int search(String content, String pattern) {

        lines.clear();

        if (content == null || pattern == null || pattern.isEmpty()) {
            return -1;
        }

        int searchFrom = 0;

        while (searchFrom < content.length()) {

            int index = content.indexOf(pattern, searchFrom);

            // break when no more results
            if (index < 0) {
                break;
            }

            // find the beginning of the line
            int lineStart = content.lastIndexOf('\n', index);

            // beginning starts at ZERO or after NEWLINE
            lineStart = lineStart == -1 ? 0 : lineStart + 1;

            // find the ending of the line
            int lineEnd = content.indexOf('\n', index);
            if (lineEnd == -1) {
                lineEnd = content.length();
            }

            // add line to search results
            lines.add(content.substring(lineStart, lineEnd));

            // increment searchFrom for next search
            searchFrom = index + 1;
        }

        return lines.size();
    }

}
