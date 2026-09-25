package assign04;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Unit Tests for the IntegerStringUtility Class.
 *
 *@author Camden Lyon & Ibrahim Alasady
 *@Version 2026-09-24
 */

public class IntegerStringUtilityTest {

  @Test
  public void testInsertionSortIntegerArray() {
    Integer[] arr = {5, 2, 8, 1, 3};
    Comparator<Integer> comp = Integer::compareTo;

    IntegerStringUtility.insertionSort(arr, comp);

    assertArrayEquals(new Integer[]{ 1, 2, 3, 5, 8 }, arr);
  }

  @Test
  public void testInsertionSortyEmptyAndNullArray() {
    Integer[] empty = {};
    Comparator<Integer> comp = Integer::compareTo;

    // Shouldn't throw any exceptions
    IntegerStringUtility.insertionSort(empty, comp);
    IntegerStringUtility.insertionSort(null, comp);

    assertEquals(0, empty.length);
  }

  @Test
  public void testFindMaxNormalArray() {
    String[] strings = {"10", "100", "2"};
    Comparator<String> comp = new IntegerStringUtility.StringNumericalValueComparator();

    String max = IntegerStringUtility.findMax(strings, comp);

    assertEquals("100", max);
  }

  @Test
  public void testFindMaxThrowsNoSuchElementExceptionOnEmpty() {
    String[] empty = {};
    Comparator<String> comp = new IntegerStringUtility.StringNumericalValueComparator();

    assertThrows(NoSuchElementException.class, () -> {
      IntegerStringUtility.findMax(empty, comp);
    });
  }

  @Test
  public void testNumericalComparatorDifferentLengths() {
    Comparator<String> comp = new IntegerStringUtility.StringNumericalValueComparator();

    // "100" more than "20" based on the length
    assertTrue(comp.compare("100", "20") > 0);
    assertTrue(comp.compare("20", "100") < 0);
  }

  @Test
  public void testNumericalComparatorLeadingZeros() {
    Comparator<String> comp = new IntegerStringUtility.StringNumericalValueComparator();

    assertTrue(comp.compare("007", "05") > 0);
    assertEquals(0, comp.compare("010", "10"));
  }

  @Test
  public void testNumericalComparatorThrowsOnInvalidInput() {
    Comparator<String> comp = new IntegerStringUtility.StringNumericalValueComparator();

    assertThrows(IllegalArgumentException.class, () -> comp.compare("abc", "123"));
    assertThrows(IllegalArgumentException.class, () -> comp.compare("000", "123"));   

  }

  @Test
  public void testSimilarityComparatorAnagrams() {
    Comparator<String> comp = new IntegerStringUtility.StringSimilarityComparator();

    assertEquals(0, comp.compare("123","321"));
    assertEquals(0, comp.compare("001", "100"));
  }

  @Test
  public void testSimilarityComparatorDifferentLengths() {
    Comparator<String> comp = new IntegerStringUtility.StringSimilarityComparator();

    assertTrue(comp.compare("1234", "123") > 0);
  }

  @Test
  public void testSimilarityGroupComparatorByLengthThenValue() {
    Comparator<String[]> groupComp = new IntegerStringUtility.StringSimilarityGroupComparator();

    String[] group1 = {"12", "21"};
    String[] group2  = {"123", "321", "312"};

    assertTrue(groupComp.compare(group2, group1) > 0);
  }

  @Test
  public void testSimilarityGroupComparatorEqualLengthDifferentMaxValue() {
    Comparator<String[]> groupComp = new IntegerStringUtility.StringSimilarityGroupComparator();

    String[] groupA = {"12", "21"};
    String[] groupB = {"13", "31"};

    assertTrue(groupComp.compare(groupB, groupA) > 0);
  }

  @Test
  public void testGetSimilarityGroupsBasic() {
    String[] input = {"12", "34", "21", "43", "99"};

    String [][] groups = IntegerStringUtility.getSimilarityGroups(input);


    assertEquals(3, groups.length);
    assertArrayEquals(new String[]{"12", "21"}, groups[0]);
    assertArrayEquals(new String[]{"34", "43"}, groups[1]);
    assertArrayEquals(new String[]{"99"}, groups[2]);
  }

  @Test
  public void testGetSimilarityGroupsEmpty() {
    String[][] groups = IntegerStringUtility.getSimilarityGroups(new String[0]);
    assertEquals(0, groups.length);
  }

  @Test
  public void testFindMaximumSimiliarityGroup() {
    int[] input = {12, 21, 13, 31, 100};

    String[] result = IntegerStringUtility.findMaximumSimilarityGroup(input);

    assertArrayEquals(new String[]{"13", "31"}, result);
  }

  @Test
  public void testFindmaximumSimilarityGroupThrowsOnEmpty() {
    assertThrows(NoSuchElementException.class, () -> {
      IntegerStringUtility.findMaximumSimilarityGroup(new int[0]);
    });
  }
}
    
