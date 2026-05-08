package com.search;

import java.util.ArrayList;
import java.util.List;

public class AdvanceSearch {

    public static final List<String> lines = new ArrayList<>();

    private static int findPosition(String text, String pattern, int start) {

        int n = text.length();
        int m = pattern.length();

        int i = start;

        while (i <= n - m) {

            int j = m - 1;

            // match right to left
            while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) {
                j--;
            }

            // full match
            if (j < 0) {
                return i;
            }

            // bad character rule shift
            char badChar = text.charAt(i + j);

            int lastOccur = lastIndexOf(pattern, badChar, j);

            int shift;
            if (lastOccur < 0) {
                shift = j + 1;
            } else {
                shift = j - lastOccur;
            }

            // IMPORTANT: always move forward at least 1
            i += Math.max(1, shift);
        }

        return -1;
    }

    // helper: rightmost occurrence before position j
    private static int lastIndexOf(String pattern, char c, int j) {
        for (int k = j; k >= 0; k--) {
            if (pattern.charAt(k) == c) {
                return k;
            }
        }
        return -1;
    }

    public static int search(String content, String pattern) {

        lines.clear();

        if (content == null || pattern == null || pattern.isEmpty()) {
            return -1;
        }

        int searchFrom = 0;

        while (searchFrom <= content.length() - pattern.length()) {

            int index = findPosition(content, pattern, searchFrom);

            if (index < 0) {
                break;
            }

            int lineStart = content.lastIndexOf('\n', index);
            lineStart = (lineStart == -1) ? 0 : lineStart + 1;

            int lineEnd = content.indexOf('\n', index);
            if (lineEnd == -1) {
                lineEnd = content.length();
            }

            lines.add(content.substring(lineStart, lineEnd));

            searchFrom = index + 1;
        }

        return lines.size();
    }

}