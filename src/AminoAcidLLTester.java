import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class AminoAcidLLTester {
    //Tests the addCodon (which is private), incrementCounts() and createFromRNASequence methods
    //Tests if codons are added correctly to the list, the correct count for an aminoacid is incremented and proper aminoacid list returned when given a string codon sequence
    //Test failed because since I created 2 different nodes, their addresses will never be the same
    @Test
    public void createFromRNASequence1() {
        AminoAcidLL expected = new AminoAcidLL("CCGUUGGCACUGUUG");
        assertEquals(expected, AminoAcidLL.createFromRNASequence("CCGUUGGCACUGUUG"));
    }

    //Uses different string input than the one above
    //Tests the addCodon (which is private), incrementCounts() and createFromRNASequence methods
    //Tests if codons are added correctly to the list, the correct count for an aminoacid is incremented and proper aminoacid list returned when given a string codon sequence
    //Test failed because since I created 2 different nodes, their addresses will never be the same
    @Test
    public void createFromRNASequence2() {
        AminoAcidLL expected = new AminoAcidLL("CGGUUGGGGCUG");
        assertEquals(expected, AminoAcidLL.createFromRNASequence("CGGUUGGGGCUG"));

    }

    //Uses different string input than the ones above
    //Tests the addCodon (which is private), incrementCounts() and createFromRNASequence methods
    //Tests if codons are added correctly to the list, the correct count for an aminoacid is incremented and proper aminoacid list returned when given a string codon sequence
    //Test failed because since I created 2 different nodes, their addresses will never be the same
    @Test
    public void createFromRNASequence3() {
        AminoAcidLL expected = new AminoAcidLL("CGGUUGGGGCUGUUU");
        assertEquals(expected, AminoAcidLL.createFromRNASequence("CGGUUGGGGCUGUUU"));
    }

    //After confirming that createFromRNASequence method works above, it can be used in this (aminoAcidList()) test
    //This test tests whether the correct char[] of aminoacids are returned in the order in which they appear in a list of aminoacids
    //This test failed.
    @Test
    public void aminoAcidList() {
        AminoAcidLL list = AminoAcidLL.createFromRNASequence("CCGUUGGCACUGUUGUUU");
        char[] expected = {'P', 'L', 'A', 'L', 'L'};
        assertArrayEquals(expected, list.aminoAcidList());
    }

    //Checks to see if proper counts array is returned of the aminoacids in the order which they within the linked list
    //Test gave a nullPointerException
    @Test
    public void aminoAcidCounts() {
        AminoAcidLL list = AminoAcidLL.createFromRNASequence("CCGUUGGCACUGUUG");
        int[] expected = {1, 1, 1, 2, 3};
        assertArrayEquals(expected, list.aminoAcidCounts());
    }

    //Tests both the totalCount() method (which is private) and aminoAcidCompare() methods
    //Tests if the total number of times an aminoacid is used is counted and if the difference in total counts for each aminoacid is properly calculated
    //Test passed, implying that test case and probably code was correct
    @Test
    public void aminoAcidCompare1() {
        AminoAcidLL list = new AminoAcidLL("CCGUUGGCACUGUUG");
        AminoAcidLL inList = new AminoAcidLL("CCGUUGGCACUGUUG");
        int expected = 0;
        assertEquals(expected, list.aminoAcidCompare(inList));
    }

    //Tests both the totalCount() method (which is private) and aminoAcidCompare() methods
    //Tests if the total number of times *different* aminoacids is used is counted and if the difference in total counts for each aminoacid is properly calculated
    //Test passed, implying that test case and probably code was correct
    @Test
    public void aminoAcidCompare2() {
        AminoAcidLL list = new AminoAcidLL("CCGUUGGCACUGUUG");
        AminoAcidLL inList = new AminoAcidLL("CGGUUGGGGCUGUUU");
        int expected = 0;
        assertEquals(expected, list.aminoAcidCompare(inList));
    }

    //Tests if the correct difference in the number of codons for each codon in the codon list is returned
    //Test gave a nullPointerException
    @Test
    public void codonCompare() {
        AminoAcidLL list = new AminoAcidLL("CCGUUGGCACUGUUG");
        AminoAcidLL inList = new AminoAcidLL("CCGUUGGCACUGUUG");
        int expected = 0;
        assertEquals(expected, list.aminoAcidCompare(inList));
    }

    //Tests if the linked list is sorted by returning true or false
    //Test passed implying that both the code and test case was correct
    @Test
    public void isSorted() {
        AminoAcidLL list = new AminoAcidLL("CCGUUGGCACUGUUG");
        assertTrue("false", list.isSorted());
    }

    //Tests if the 1st node of the list is indeed the node with aminoacid A.
    //Test failed because since I created 2 different nodes, their addresses will never be the same
    @Test
    public void sort() {
        AminoAcidLL list = AminoAcidLL.createFromRNASequence("CCGUUGGCACUGUUG");
        AminoAcidLL expected = new AminoAcidLL("GCG");//it should produce a node with A as the aminoacid
        assertEquals(expected, AminoAcidLL.sort(list));
    }
}