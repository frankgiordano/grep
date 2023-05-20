package com.search;

import java.util.HashMap;
import java.util.Map;

/**
 * This performs a Boyer Moore search of a pattern within a string of text data
 * and returning the position location of the pattern within the string if any.
 * <p>
 * Boyer-Moore algorithm, which looks first for the final letter of the target string,
 * and uses a lookup table to tell it how far ahead it can skip in the input whenever
 * it finds a non-matching character.
 *
 * <p>
 * 0(n/m) sublinear runtime but worst case can be O(n*m)
 *
 * @author Frank Giordano
 */
public class BmSearch {

    private final String pattern;
    private final Map<Character, Integer> misMatchShiftsTable = new HashMap<>();

    public BmSearch(String pattern) {
        this.pattern = pattern;
        this.compileMisMatchShiftsTable();
    }

    private void compileMisMatchShiftsTable() {
        final var lengthOfPattern = pattern.length();
        for (int i = 0; i < lengthOfPattern; i++) {
            misMatchShiftsTable.put(pattern.charAt(i), Math.max(1, lengthOfPattern - i - 1));
        }
    }

    public int findPosition(String text) {
        final var lengthOfPattern = pattern.length();
        final var lengthOfText = text.length();
        int numOfSkips;

        for (var i = 0; i <= lengthOfText - lengthOfPattern; i += numOfSkips) {
            numOfSkips = 0;
            for (var j = lengthOfPattern - 1; j >= 0; j--) {  // check starting from right to left
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    if (misMatchShiftsTable.get(text.charAt(i + j)) != null) {
                        numOfSkips = misMatchShiftsTable.get(text.charAt(i + j));
                    } else {
                        numOfSkips = misMatchShiftsTable.size();
                    }
                    break;
                }
            }

            if (numOfSkips == 0) {  // means we found the matching position
                return i;
            }
        }

        return lengthOfText; // meaning have not found a match
    }

}
