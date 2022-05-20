package com.ucommerce.codegen.builders.java;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Helper methods useful for working with {@link Method} objects.
 */
public class MethodHelper {
    private static Logger logger = LoggerFactory.getLogger(MethodHelper.class);
    private static Set<Class> SIMPLE_TYPES = Set.of(LocalDate.class, LocalDateTime.class, LocalTime.class, Date.class, Calendar.class,
            String.class, Boolean.class, Byte.class, Character.class, Float.class, Integer.class, Long.class, Short.class, Double.class);


    private static final Pattern WORD_FINDER = Pattern.compile("(([A-Z]?[a-z]+)|([A-Z]))");

    /**
     * Indicates if a method as non-simple parameters (objects).
     * The 8 standard primitives in Java are simple parameters, as is a list of others (such as dates).
     *
     * @param method the method to analyse.
     * @return true if complex are found, false if the method only has simple parameter types.
     */
    public static boolean hasComplexParameters(Method method) {

        boolean complexFound = false;
        for (Parameter parameter : method.getParameters()) {
            complexFound = isComplex(parameter);
            if (complexFound == true) {
                break;
            }
        }

        return complexFound;
    }

    public static boolean isComplex(Parameter parameter) {
        if (parameter.getType().isPrimitive()) {
            return false;
        } else if (SIMPLE_TYPES.contains(parameter.getType())) {
            return false;
        } else if (parameter.getType().isEnum()) {
            return false;
        } else if (parameter.getType().isRecord()) {
            logger.info("Parameter is complex " + parameter.getName() + ":" + parameter.getType().getSimpleName());
            return true;
        } else {
            logger.info("Parameter is complex " + parameter.getName() + ":" + parameter.getType().getSimpleName());
            return true;
        }
    }

    public static boolean isSimple(Parameter parameter) {
        return !isComplex(parameter);
    }

    public static boolean hasVoidReturnType(Method method) {
        return Void.class.equals(method.getReturnType());
    }

    /**
     * Source : https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-regex-2/src/main/java/com/baeldung/regex/camelcasetowords/CamelCaseToWords.java
     * Find the words in mixed case string like ThisIsText or HereIsSomeText
     *
     * @param text the text to parse
     * @return the list of words to process
     */
    public static List<String> findWordsInMixedCase(String text) {
        Matcher matcher = WORD_FINDER.matcher(text);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }


}
