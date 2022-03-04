package br.com.monitoratec.farol.util;

import java.util.Iterator;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestUtils {
    private TestUtils() {
        throw new AssertionError();
    }

    /**
     * Validate if two iterables contain equal elements; they must be of the same size and every pair of elements must be
     * validated by parameter {@code validator}. Their types don't need to be the same, so this method is useful for
     * performing comparisons between these types (e.g. checking if their fields have the same values).
     *
     * @param expected  The expected elements.
     * @param actual    The actual elements.
     * @param validator A void function accepting each pair of elements for performing the proper validations.
     * @param <T>       The type of the expected iterable.
     * @param <U>       The type of the actual iterable.
     */
    public static <T, U> void assertIterablesMatch(Iterable<T> expected, Iterable<U> actual, BiConsumer<T, U> validator) {
        //Skip if the objects are the same
        if (expected == actual) {
            return;
        }

        //Validate each element
        final Iterator<T> expectedIt = expected.iterator();
        final Iterator<U> actualIt = actual.iterator();

        while (expectedIt.hasNext() && actualIt.hasNext()) {
            final T expectedElement = expectedIt.next();
            final U actualElement = actualIt.next();

            validator.accept(expectedElement, actualElement);
        }

        //Assert both iterators have finished (i.e. both iterables were of the same size)
        assertFalse(expectedIt.hasNext());
        assertFalse(actualIt.hasNext());
    }
}
