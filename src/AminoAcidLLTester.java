import org.junit.Test;

public class AminoAcidLLTester {
    @Test
    public void sortOfSort1() {//Tests the general functionality of the program
        int[] A = {2, 7, 1, 3, 0, 9, 6, 5};
        int[] results = {6, 5, 1, 0, 2, 3, 7, 9};
        SortOfSort.sortOfSort(A);
        assertArrayEquals(results, A);
    }
}
