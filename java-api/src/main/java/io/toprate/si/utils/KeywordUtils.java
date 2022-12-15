package io.toprate.si.utils;

public class KeywordUtils {
    public static String formatInputKeyword(String keywordInput) {
        return keywordInput.replaceAll("[^A-Za-z0-9]", " ")
                .replaceAll("[-+^]*", "")
                .replaceAll("\\s+", " ")
                .toLowerCase().trim();
    }
}
