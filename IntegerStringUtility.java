package assign04;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A utility class that deals with long positive integers represented as strings
 * 
 * @author Camden Lyon & Ibrahim Alasady
 * @version 2026-09-24
 */
public class IntegerStringUtility {

	/**
	 * Uses insertion sort to sort in place the input array using the input
	 * comparator
	 * 
	 * @param <E>,        the type of the objects in the array
	 * @param array,      the array to sort
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
	 * Returns the largest value in the array as ordered by the comparator
	 * 
	 * @param <E>,        the type of the objects in the array
	 * @param array,      the array to sort
	 * @param comparator, the functor to sort by @return, the largest object in the
	 *                    array
	 */
	public static <E> E findMax(E[] array, Comparator<? super E> comparator) {
		if (array == null || array.length == 0) {
			throw new NoSuchElementException("Array must not be null or empty");
		}
		E[] copy = Arrays.copyOf(array, array.length);
		IntegerStringUtility.insertionSort(copy, comparator);
		return copy[copy.length - 1];
	}

	/**
	 * A Comparator that compares strings containing positive integers based on
	 * their value
	 */
	public static class StringNumericalValueComparator implements Comparator<String> {

		@Override
		public int compare(String str1, String str2) {
			String num1 = CleanPosInt(str1);
			String num2 = CleanPosInt(str2);

			if (num1 == null || num2 == null)
				throw new IllegalArgumentException("Both strings must be positive ints");

			if (num1.length() != num2.length()) {
				return num1.length() - num2.length();
			}

			for (int i = 0; i < num1.length(); i++) {
				char c1 = num1.charAt(i);
				char c2 = num2.charAt(i);
				if (c1 != c2) {
					return c1 - c2;
				}
			}
			return 0;

		}

		/**
		 * Private helper method to check for a positive integer and clean zeroes off
		 * 
		 * @param string, the string to check and clean
		 * @return the input string cleaned of leading zeroes, and null if it isn't a
		 *         positive int
		 */
		private String CleanPosInt(String string) {
			if (string == null || string.length() == 0)
				return null;
			for (int i = 0; i < string.length(); i++) {
				if (!Character.isDigit(string.charAt(i))) {
					return null;
				}
			}
			int firstNonZero = 0;
			while (firstNonZero < string.length() && string.charAt(firstNonZero) == '0') {
				firstNonZero++;
			}
			if (firstNonZero == string.length()) {
				return null;
			}
			return string.substring(firstNonZero);

		}

	}

	/**
	 * A Comparator that compares strings containing positive integers based on
	 * their similarity
	 */
	public static class StringSimilarityComparator implements Comparator<String> {

		@Override
		public int compare(String str1, String str2) {
			if (str1 == null || str2 == null)
				throw new IllegalArgumentException("Cannot be Null");
			if (str1.length() != str2.length()) {
				return str1.length() - str2.length();
			}
			Character[] chars1 = toCharacterArray(str1);
			Character[] chars2 = toCharacterArray(str2);

			insertionSort(chars1, (c1, c2) -> c1.compareTo(c2));
			insertionSort(chars2, (c1, c2) -> c1.compareTo(c2));

			for (int i = 0; i < chars1.length; i++) {
				int comp = chars1[i].compareTo(chars2[i]);
				if (comp != 0) {
					return comp;
				}
			}

			return 0;

		}

		private Character[] toCharacterArray(String string) {
			Character[] charArray = new Character[string.length()];
			for (int i = 0; i < string.length(); i++) {
				charArray[i] = string.charAt(i);
			}
			return charArray;
		}
	}

	/**
	 * A Comparator that compares groups of similar strings containing positive
	 * integers
	 */
	public static class StringSimilarityGroupComparator implements Comparator<String[]> {

		@Override
		public int compare(String[] arr1, String[] arr2) {
			if (arr1 == null || arr2 == null)
				throw new IllegalArgumentException("arrays must not be null");
			if (arr1.length != arr2.length)
				return arr1.length - arr2.length;
			if (arr1.length == 0)
				return 0;
			StringNumericalValueComparator comp = new StringNumericalValueComparator();
			String str1 = IntegerStringUtility.findMax(arr1, comp);
			String str2 = IntegerStringUtility.findMax(arr2, comp);

			return comp.compare(str1, str2);
		}

	}

	/**
	 * creates a 2D array of the sorted groups
	 * 
	 * @param array, the array of integer strings to be grouped @return, the 2D
	 *               array of integer strings
	 */
	public static String[][] getSimilarityGroups(String[] array) {
		if (array == null || array.length == 0) {
			return new String[0][0];
		}
		String[] copy = Arrays.copyOf(array, array.length);

		Comparator<String> similarityComp = new StringSimilarityComparator();
		IntegerStringUtility.insertionSort(copy, similarityComp);

		int groups = 1;
		for (int i = 1; i < copy.length; i++) {
			if (similarityComp.compare(copy[i], copy[i - 1]) != 0) {
				groups++;
			}
		}
		String[][] result = new String[groups][];
		int rowIndex = 0;
		int groupStart = 0;

		for (int i = 1; i <= copy.length; i++) {
			if (i == copy.length || similarityComp.compare(copy[i], copy[groupStart]) != 0) {
				int groupSize = i - groupStart;
				result[rowIndex] = new String[groupSize];
				System.arraycopy(copy, groupStart, result[rowIndex], 0, groupSize);

				rowIndex++;
				groupStart = i;
			}
		}

		return result;
	}

	/**
	 * Finds the largest similarity group by size, then largest value
	 * 
	 * @param array, the array of values to sort into similarity groups
	 * @return an array of strings containing the largest similarity group
	 */
	public static String[] findMaximumSimilarityGroup(int[] array) {
		if (array == null || array.length == 0) {
			throw new NoSuchElementException("Array must comntain elements");
		}

		String[] stringArray = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			stringArray[i] = String.valueOf(array[i]);
		}
		String[][] groups = getSimilarityGroups(stringArray);
		Comparator<String[]> groupComp = new StringSimilarityGroupComparator();
		return findMax(groups, groupComp);
	}

}
