package com.aliyun.sdk.service.oss2.internal;

import java.util.function.BiFunction;

/**
 * A generic key-value container class used to hold two related objects
 */
class Pair<T, V> {
    /**
     * The first element, typically represents a key or primary object
     */
    private final T first;

    /**
     * The second element, typically represents a value or secondary information
     */
    private final V second;

    /**
     * Constructs a new Pair instance with the given elements
     *
     * @param first  The first element
     * @param second The second element
     */
    public Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Static factory method to create a new Pair instance
     *
     * @param first  The first element
     * @param second The second element
     * @return A newly created {@link Pair} instance
     */
    public static <T, V> Pair<T, V> of(T first, V second) {
        return new Pair<>(first, second);
    }

    /**
     * Retrieves the first element of the pair
     *
     * @return Returns the first element of type
     */
    public T first() {
        return first;
    }

    /**
     * Retrieves the second element of the pair
     *
     * @return Returns the second element of type
     */
    public V second() {
        return second;
    }

    /**
     * Applies the given function to both elements and returns the transformed result
     *
     * @param function The bi-function to be applied
     * @return The result of applying the function to both elements
     */
    public <ReturnT> ReturnT apply(BiFunction<T, V, ReturnT> function) {
        return function.apply(first, second);
    }

    /**
     * Compares this Pair with the specified object for equality
     *
     * @param obj The object to compare with
     * @return true if the given object is a Pair with equal elements
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }

        Pair other = (Pair) obj;
        return other.first.equals(first) && other.second.equals(second);
    }

    /**
     * Returns a hash code value for this Pair
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        return getClass().hashCode() + first.hashCode() + second.hashCode();
    }

    /**
     * Returns a string representation of this Pair
     *
     * @return A string representation containing both elements
     */
    @Override
    public String toString() {
        return "Pair(first=" + first + ", second=" + second + ")";
    }
}
