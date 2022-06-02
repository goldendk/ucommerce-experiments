package com.ucommerce.codegen.builders.java;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SourceBuilderUtil {
    private static String SPACES = "                                                                      "; // cool, right ?

    public static String addIndentation(String s, int spacesCount) {
        String spaces = SPACES.substring(0, spacesCount);
        String[] split = s.split("\n");
        String indentedMethod = Arrays.stream(split)
                .map(methodLine -> methodLine.length() == 0 ? methodLine : spaces + methodLine)
                .collect(Collectors.joining("\n"));
        return indentedMethod;
    }
}
