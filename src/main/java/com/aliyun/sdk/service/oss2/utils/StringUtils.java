package com.aliyun.sdk.service.oss2.utils;

import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Objects;

/**
 * Operations on {@link String} that are {@code null} safe.
 */
public class StringUtils {

    /**
     * The empty String {@code ""}.
     */
    public static final String EMPTY = "";

    /**
     * {@code StringUtils} instances should NOT be constructed in
     * standard programming.
     */
    private StringUtils() {
    }

    /**
     * Checks if a CharSequence is empty ("") or null.
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Checks if a CharSequence is empty (""), null or whitespace only.
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     */
    public static boolean isBlank(final CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return true;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true if the given value is either null or the empty string
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * The String is trimmed using {@link String#trim()}.
     * Trim removes start and end characters &lt;= 32.
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed string, {@code null} if null String input
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    /**
     * Compares two Strings, returning {@code true} if they represent
     * equal sequences of characters.
     *
     * @param cs1 the first String, may be {@code null}
     * @param cs2 the second String, may be {@code null}
     * @return {@code true} if the Strings are equal (case-sensitive), or both {@code null}
     * @see Object#equals(Object)
     */
    public static boolean equals(final String cs1, final String cs2) {
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        return cs1.equals(cs2);
    }

    /**
     * Joins the strings in parts with joiner between each string
     *
     * @param joiner the string to insert between the strings in parts
     * @param parts  the parts to join
     * @return A string that consists of the elements of values by the separator character.
     */
    public static String join(String joiner, String... parts) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            builder.append(parts[i]);
            if (i < parts.length - 1) {
                builder.append(joiner);
            }
        }
        return builder.toString();
    }

    /**
     * Join any Strings.
     *
     * @param delimiter the delimiter which split two Strings, should not be {@code null}
     * @param elements  the Strings Iterable, should not be {@code null}
     * @return {@code String}
     */
    public static String join(String delimiter, Iterable<? extends String> elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        StringBuilder stringBuilder = new StringBuilder();
        for (String value : elements) {
            stringBuilder.append(value);
            stringBuilder.append(delimiter);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    /**
     * Gets a substring from the specified String avoiding exceptions.
     *
     * @param str   the String to get the substring from, may be null
     * @param start the position to start from, negative means count back from the end of the String by this many characters
     * @param end   the position to end at (exclusive), negative means count back from the end of the String by this many
     *              characters
     * @return substring from start position to end position,
     * {@code null} if null String input
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length()) {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * Converts a String to upper case as per {@link String#toUpperCase()}.
     * <p>
     * This uses "ENGLISH" as the locale.
     *
     * @param str the String to upper case, may be null
     * @return the upper cased String, {@code null} if null String input
     */
    public static String upperCase(final String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase(Locale.ENGLISH);
    }

    /**
     * Converts a String to lower case as per {@link String#toLowerCase()}.
     * <p>
     * This uses "ENGLISH" as the locale.
     *
     * @param str the String to lower case, may be null
     * @return the lower cased String, {@code null} if null String input
     */
    public static String lowerCase(final String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase(Locale.ENGLISH);
    }

    /**
     * Encode the given bytes as a string using the given charset
     *
     * @throws UncheckedIOException with a {@link CharacterCodingException} as the cause if the bytes cannot be encoded using the
     *                              provided charset.
     */
    public static String fromBytes(byte[] bytes, Charset charset) throws UncheckedIOException {
        try {
            return charset.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
        } catch (CharacterCodingException e) {
            throw new UncheckedIOException("Cannot encode string.", e);
        }
    }

    /**
     * Tests if this string starts with the specified prefix ignoring case considerations.
     *
     * @param str    the string to be tested
     * @param prefix the prefix
     * @return true if the string starts with the prefix ignoring case
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return str.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    /**
     * Convert a string to boolean safely (as opposed to the less strict {@link Boolean#parseBoolean(String)}). If a customer
     * specifies a boolean value it should be "true" or "false" (case insensitive) or an exception will be thrown.
     */
    public static boolean safeStringToBoolean(String value) {
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        }
        throw new IllegalArgumentException("Value was defined as '" + value + "', but should be 'false' or 'true'");
    }

    /**
     * Replaces a String with another String inside a larger String.
     *
     * @param originalString text to search and replace in, may be null
     * @param partToMatch    the String to search for
     * @param replacement    the String to replace it with, may be null
     * @return the text with any replacements processed,
     * {@code null} if null String input
     */
    public static String replace(String originalString, String partToMatch, String replacement) {
        StringBuilder buffer = new StringBuilder(originalString.length());
        buffer.append(originalString);

        int indexOf = buffer.indexOf(partToMatch);
        while (indexOf != -1) {
            buffer = buffer.replace(indexOf, indexOf + partToMatch.length(), replacement);
            indexOf = buffer.indexOf(partToMatch, indexOf + replacement.length());
        }

        return buffer.toString();
    }


    /**
     * Replaces all occurrences of Strings within another String.
     *
     * @param text            text to search and replace in, no-op if null
     * @param searchList      the Strings to search for, no-op if null
     * @param replacementList the Strings to replace them with, no-op if null
     * @return the text with any replacements processed, {@code null} if
     * null String input
     * @throws IllegalArgumentException if the lengths of the arrays are not the same (null is ok,
     *                                  and/or size 0)
     */
    public static String replaceEach(final String text, final String[] searchList, final String[] replacementList) {
        int searchLength = searchList.length;
        int replacementLength = replacementList.length;

        if (isNullOrEmpty(text) || (searchLength == 0 && replacementLength == 0)) {
            return text;
        }

        // make sure lengths are ok, these need to be equal
        if (searchLength != replacementLength) {
            throw new IllegalArgumentException("Search and Replace array lengths don't match: "
                    + searchLength
                    + " vs "
                    + replacementLength);
        }

        // keep track of which still have matches
        boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];

        // index on index that the match was found
        int textIndex = -1;
        int replaceIndex = -1;
        int tempIndex = -1;

        // index of replace array that will replace the search string found
        // NOTE: logic duplicated below START
        for (int i = 0; i < searchLength; i++) {
            if (noMoreMatchesForReplIndex[i] || isNullOrEmpty(searchList[i]) || replacementList[i] == null) {
                continue;
            }
            tempIndex = text.indexOf(searchList[i]);

            // see if we need to keep searching for this
            if (tempIndex == -1) {
                noMoreMatchesForReplIndex[i] = true;
            } else {
                if (textIndex == -1 || tempIndex < textIndex) {
                    textIndex = tempIndex;
                    replaceIndex = i;
                }
            }
        }
        // NOTE: logic mostly below END

        // no search strings found, we are done
        if (textIndex == -1) {
            return text;
        }

        int start = 0;

        // get a good guess on the size of the result buffer so it doesn't have to double if it goes over a bit
        int increase = 0;

        // count the replacement text elements that are larger than their corresponding text being replaced
        for (int i = 0; i < searchList.length; i++) {
            if (searchList[i] == null || replacementList[i] == null) {
                continue;
            }
            int greater = replacementList[i].length() - searchList[i].length();
            if (greater > 0) {
                increase += 3 * greater; // assume 3 matches
            }
        }
        // have upper-bound at 20% increase, then let Java take over
        increase = Math.min(increase, text.length() / 5);

        StringBuilder buf = new StringBuilder(text.length() + increase);

        while (textIndex != -1) {

            for (int i = start; i < textIndex; i++) {
                buf.append(text.charAt(i));
            }
            buf.append(replacementList[replaceIndex]);

            start = textIndex + searchList[replaceIndex].length();

            textIndex = -1;
            replaceIndex = -1;
            tempIndex = -1;
            // find the next earliest match
            // NOTE: logic mostly duplicated above START
            for (int i = 0; i < searchLength; i++) {
                if (noMoreMatchesForReplIndex[i] || searchList[i] == null ||
                        searchList[i].isEmpty() || replacementList[i] == null) {
                    continue;
                }
                tempIndex = text.indexOf(searchList[i], start);

                // see if we need to keep searching for this
                if (tempIndex == -1) {
                    noMoreMatchesForReplIndex[i] = true;
                } else {
                    if (textIndex == -1 || tempIndex < textIndex) {
                        textIndex = tempIndex;
                        replaceIndex = i;
                    }
                }
            }
            // NOTE: logic duplicated above END

        }

        int textLength = text.length();
        for (int i = start; i < textLength; i++) {
            buf.append(text.charAt(i));
        }
        return buf.toString();
    }

    /**
     * The String is trimmed using {@link String#trim()} to remove start and end characters
     * Returning an empty String ("") if the String is empty ("") after the trim or if it is null.
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed String, or an empty String if {@code null} input
     */
    public static String trimToEmpty(final String str) {
        return str == null ? EMPTY : str.trim();
    }
}