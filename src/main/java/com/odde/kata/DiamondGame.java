package com.odde.kata;

import java.util.Collections;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.iterate;

public class DiamondGame {
    public String diamond(char c) {
        return iterate(c - 'A', i -> i >= 'A' - c, i -> i - 1)
                .mapToObj(i -> bodyLine(c - Math.abs(i), c))
                .collect(Collectors.joining("\n"));
    }

    private String bodyLine(int lineChar, int endChar) {
        return nSpace(endChar - lineChar) + (lineChar == 'A' ? (char) lineChar
                : (char) lineChar + nSpace((lineChar - 'A') * 2 - 1) + (char) lineChar);
    }

    private String nSpace(int n) {
        return String.join("", Collections.nCopies(n, " "));
    }
}
