package assign04;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
/**
 * 
 */
public class IntegerStringUtility {
	
	/**
	 * Uses insertion sort to sort in place the input array using the input comparator
	 * @param <E>, the type of the objects in the array
	 * @param array, the array to sort
	 * @param comparator, the functor to sort by
	 */
	public static <E> void insertionSort(E[] array, Comparator<? super E> comparator) {
		if (array == null || array.length < 1)
			return;
		for (int i = 1; i < array.length; i++) {
            E current = array[i];
            int j = i - 1;

            while (j >= 0 && comparator.compare(array[j], current) > 0) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = current;
		}
	}
	
	/**
	 * Returns the largest value in thge array as ordered by the comparator
	 * @param <E>, the type of the objects in the array
	 * @param array, the array to sort
	 * @param comparator, the functor to sort by
	 * @return, the largest object in the array
	 */
	public static <E> E findMax(E[] array, Comparator<? super E> comparator) {
		if (array == null || array.length == 0) {
            throw new NoSuchElementException("Array must not be null or empty");
        }
		E[] copy = Arrays.copyOf(array, array.length);
		IntegerStringUtility.insertionSort(copy, comparator);
		return copy[copy.length - 1];
	}
}

